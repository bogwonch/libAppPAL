package apppal.lint;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.Variable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Completeness {
  private static final Variable VAR = new Variable("*");
  private final AC ac;
  private final Map<E, Set<String>> predicates;
  private final Map<E, Set<String>> decidable;

  public Completeness(final AC ac) {
    this.ac = ac;
    this.predicates = new HashMap<>();
    this.decidable = new HashMap<>();

    this.fix();
  }

  public boolean decidable(final E speaker, final String predicate) {
    return lookup(decidable, speaker, predicate);
  }

  public boolean decidable(final E speaker, final Fact fact) {
    if (fact.object instanceof Predicate) return decidable(speaker, ((Predicate) fact.object).name);
    else return true; // TODO: don't assume all can-say, can-act-as statements are derivable.
  }

  public boolean decidable(final Assertion a) {
    return decidable(getSpeaker(a), a.says.consequent);
  }

  /** Find all predicates which may be undecidable for a speaker */
  public Map<E, Set<String>> problems() {
    final Map<E, Set<String>> problems = new HashMap<>();
    for (final E speaker : this.predicates.keySet())
      for (final String predicate : this.predicates.get(speaker))
        if (!decidable(speaker, predicate)) add(problems, speaker, predicate);
    return problems;
  }

  /**
   * Find all undecidable assertions
   *
   * <p>Find all the assertions which depend on an undecidable predicate in their conditions
   */
  public Set<Assertion> undecidable() {
    final Set<Assertion> undecidable = new HashSet<>();
    for (final Assertion a : ac.assertions) if (specificallyUndecidable(a)) undecidable.add(a);

    return undecidable;
  }

  /**
   * Find all can-say assertions where a delegation could be permitted, but which currently we don't
   * have any information from the delegated party whether this would or would not be permitted
   */
  public class RemotelyUnknown {
    public final Assertion assertion;
    public final E target;
    public final E via;
    public final String decision;

    public RemotelyUnknown(Assertion a) {
      this.assertion = a;
      this.target = getSpeaker(a.speaker);
      this.via = getSpeaker(a.says.consequent.subject);
      this.decision = getPredicateName(((CanSay) a.says.consequent.object).fact);
    }

    public String toString() {
      return "(via " + this.via + ") " + target + " says * " + decision;
    }
  }

  public Set<RemotelyUnknown> remotelyUnknown() {
    final Set<RemotelyUnknown> results = new HashSet<>();
    for (final Assertion a : ac.assertions)
      if (a.isCanSay() && decidable(a)) {
        final CanSay cs = (CanSay) a.says.consequent.object;
        final E delegated = a.says.consequent.subject;

        if (!lookup(predicates, delegated, getPredicateName(cs.fact)))
          results.add(new RemotelyUnknown(a));
      }
    return results;
  }

  /**
   * Is this assertion undecidable
   *
   * <p>Does this assertion depend on an undecidable predicate?
   */
  private boolean specificallyUndecidable(final Assertion a) {
    final E speaker = getSpeaker(a);
    for (final Fact f : a.says.antecedents)
      if (!decidable(speaker, f)) {
        return true;
      }
    return false;
  }

  /**
   * Fixed point algorithm for finding all decidable predicates.
   *
   * <p>A predicate is decidable for a speaker if there exists enough information in the assertion
   * context to decide whether that predicate could be said by the speaker without remote
   * attestation.
   *
   * <p>All can-say and can-act-as statements are decidable (over approximation)
   *
   * <p>An assertion speaker says _ predicate(_) is decidable if: - there exists an assertion
   * speaker says _ predicate(_) if p1, .. pn. - for all i in 1..n: A says pi is decidable. a
   */
  private void fix() {
    int iteration = 0;
    boolean iterate = true;
    Util.debug("checking completeness");

    // Populate predicates
    for (final Assertion a : ac.assertions) {
      final E speaker = getSpeaker(a);
      // Get the predicate
      if (a.isCanSay()) {
        final Fact delegated = ((CanSay) a.says.consequent.object).fact;
        final String pred = getPredicateName(delegated);
        if (pred != null) add(predicates, speaker, pred);
      }

      final String pred = getPredicateName(a);
      if (pred != null) add(predicates, speaker, pred);

      for (final Fact f : a.says.antecedents) {
        final String conditional_pred = getPredicateName(f);
        if (conditional_pred != null) add(predicates, speaker, conditional_pred);
      }
    }

    // Find fixed point
    while (iterate == true) {
      Util.debug("completeness fixed point iteration " + (++iteration));

      iterate = false;
      for (final Assertion a : ac.assertions) {
        final E speaker;
        final String predicate;
        final List<Fact> conditions;

        // Get the speaker
        speaker = getSpeaker(a);

        // Get the predicate
        predicate = getPredicateName(a);
        if (predicate == null) {
          Util.debug("skipping");
          continue;
        }

        // Get the conditions
        conditions = a.says.antecedents;

        Util.debug(speaker + " says * " + predicate);

        // Handle can-say
        if (a.isCanSay() && decidable(a)) {
          final E delegated = a.says.consequent.subject;
          final CanSay cs = ((CanSay) a.says.consequent.object);
          if (decidable(delegated, cs.fact)) {
            Util.debug("consequent is decidable");
            add(decidable, speaker, getPredicateName(cs.fact));
          }
        }

        // Handle the assertion itself
        if (decidable(speaker, predicate)) {
          Util.debug("known decidable");
          continue;
        } else {
          boolean all_conds_decidable = true;
          for (final Fact f : conditions) {
            if (f.object instanceof Predicate) {
              if (!decidable(speaker, f)) {
                all_conds_decidable = false;
              }
            }
          }

          if (all_conds_decidable) {
            Util.debug("found decidable");
            add(decidable, speaker, predicate);
            iterate = true;
          }
        }
      }
    }

    Util.debug("completeness fixed point found in " + iteration + " iterations");
  }

  /** Looks up a entity and predicate in a map */
  private static boolean lookup(final Map<E, Set<String>> table, final E e, final String s) {
    final Set<String> results = table.get(e);
    if (results == null) return false;
    else return results.contains(s);
  }

  /** Add an entity and key to a map */
  private static void add(final Map<E, Set<String>> table, final E e, final String s) {
    final Set<String> set = table.get(e);
    if (set == null) {
      if (s != null) {
        final Set<String> results = new HashSet<>();
        results.add(s);
        table.put(e, results);
      } else Util.warn("trying to add null predicate to map");
    } else set.add(s);
  }

  /** Get the speaker of an assertion */
  private static E getSpeaker(final Assertion a) {
    if (a.speaker instanceof Variable) return VAR;
    else return a.speaker;
  }

  private static E getSpeaker(final E e) {
    if (e instanceof Variable) return VAR;
    else return e;
  }

  /**
   * Get the predicate of an assertion
   *
   * <p>Returns null if we cannot name the predicate
   */
  private static String getPredicateName(final Assertion a) {
    return getPredicateName(a.says.consequent);
  }

  private static String getPredicateName(final Fact f) {
    if (f.object instanceof Predicate) return ((Predicate) f.object).name;
    else if (f.object instanceof CanSay)
      return "can-say * " + getPredicateName(((CanSay) f.object).fact);
    else return null;
  }
}
