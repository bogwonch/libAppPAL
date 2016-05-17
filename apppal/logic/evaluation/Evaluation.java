package apppal.logic.evaluation;

import java.util.EnumSet;
import java.util.Map;

import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Constant;
import apppal.logic.language.D;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.VP;
import apppal.logic.language.Variable;
import apppal.logic.evaluation.Likely;
import apppal.logic.evaluation.Proof;
import apppal.logic.evaluation.CondProof;
import apppal.logic.evaluation.CanActAsProof;
import apppal.logic.evaluation.CanSayProof;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

/**
 * Class for doing the actual evaluation
 */
public class Evaluation
{
  public final AC ac;
  private Assertion q;
  private ResultsTable rt;
  private Set<String> derivable;
  private Likely likely;

  public Evaluation(AC ac)
  {
    this.ac = ac;
    this.rt = new ResultsTable();
    this.derivable = new HashSet<>();
    this.derivable.add("isAnApp");

    for (final Assertion a : this.ac.assertions)
    {
      if (a.isGround() && a.says.constraint.isTrue())
      {
        rt.add(a);
        if (a.says.consequent.object instanceof Predicate)
          this.derivable.add(((Predicate) a.says.consequent.object).name);
      }
      else if (! a.isGround() &&
               a.says.consequent.object instanceof Predicate)
      {
        this.derivable.add(((Predicate) a.says.consequent.object).name);
      }
    }

    this.likely = new Likely(this.ac);

    errDump();
  }

  public Set<String> getDerivable()
  {
    return this.derivable;
  }

  public void errDump()
  {
    /* System.err.println("[#] Evaluation dump"); */
    /* System.err.println("  AC"); */
    /* for (Assertion a : ac.assertions) System.err.println("  A| "+a); */
    /* System.err.println("  Constants"); */
    /* for (E a : ac.constants) System.err.println("  C| "+a); */
    /* System.err.println("  Subjects"); */
    /* for (E a : ac.subjects) System.err.println("  S| "+a); */
    /* System.err.println("  Voiced"); */
   /* for (E a : ac.voiced) System.err.println("  V| "+a); */
    /* System.err.println("  Interesting"); */
    /* for (E a : ac.interesting) System.err.println("  I| "+a); */
    /* System.err.println("  Derivable"); */
    /* for (String s : derivable) System.err.println("  D| "+s); */
  }

  public static Result run(AC ac, Assertion query)
  {
    return new Evaluation(ac).run(query);
  }

  public Result run(final Assertion query)
  {
    return this.evaluate(query, D.INF);
  }

  public static boolean shows(AC ac, Assertion query)
  {
    return new Evaluation(ac).shows(query);
  }

  public boolean shows(final Assertion query)
  {
    return this.run(query).isProven();
  }

  private Result evaluate(Assertion q, D d)
  {

    if (this.rt.has(q, d))
    {
      return this.rt.get(q, d);
    }

    // If we're never going to be able to derive this don't even bother.
    if (q.says.consequent.object instanceof Predicate &&
        ! this.derivable.contains(((Predicate) q.says.consequent.object).name))
    {
      return new Result(q, d, new Proof(false));
    }

    /* System.out.println("[@] evaluating: "+q); */

    this.rt.markUnfinished(q, d);

    // TODO: refactor this bollox
    final Proof condProof = this.cond(q, d);
    if (condProof.isKnown())
    {
      final Result result = new Result(q, d, condProof);
      this.rt.update(result);
      return result;
    }


    /* System.out.println("[@] tried cond: "+q); */

    final Proof canSayOrCanActAsProof = this.canSayOrCanActAs(q, d);
    if (canSayOrCanActAsProof.isKnown())
    {
      final Result result = new Result(q, d, canSayOrCanActAsProof);
      this.rt.update(result);
      return result;
    }

    /* System.out.println("[@] all failed: "+q); */
    Result result = new Result(q, d, new Proof(false));
    this.rt.update(result);
    return result;
  }

  private Proof cond(Assertion q, D d)
  {
    // Check implicit facts
    // isAnApp
    final E subject = q.says.consequent.subject;
    final VP vp = q.says.consequent.object;
    if (vp instanceof Predicate)
    {
      if (((Predicate) vp).name.equals("isAnApp"))
      {
        if (subject instanceof Constant)
          return new Proof(subject.name.startsWith("apk://"));
      }
    }

    for (final Assertion a : this.ac.assertions)
    {
      /* System.err.println("[?] Cond: "+a+ "{"); */
      final Unification headU = q.unify(a.consequence());
      if (headU.hasFailed()) continue;

      final Assertion thetaA = a.substitute(headU.theta);

      // Condition three: no vars of the consequent fact must be gone after unification
      if (thetaA.says.consequent.vars().size() != 0)
        continue;

      // Condition one: all antecedents must be satisfied
      // Condition three: constraint must be sat
      final List<Proof> antecedentProofs = this.checkAntecedents(thetaA, d);
      if (antecedentProofs != null)
        return new CondProof(thetaA, antecedentProofs);
    }

    /* System.err.println("} NO\n"); */
    return new Proof(false);
  }

  private List<Proof> checkAntecedents(Assertion a, D d)
  {
    if (a.says.antecedents.size() == 0 &&
        a.says.constraint.isTriviallyTrue())
      return new LinkedList<>();
    if (a.vars().size() == 0) return this.checkAntecedentsNoVars(a, d);
    else return this.checkAntecedentWithVars(a, d);
  }

  private List<Proof> checkAntecedentsNoVars(Assertion a, D d)
  {
    if (a.vars().size() != 0) throw new RuntimeException("vars in antecedent when none claimed");
    final List<Proof> proofs = new LinkedList<>();
    for (final Fact f : a.says.antecedents)
    {
      final Result r = evaluate(f.toAssertion(), d);
      if (! r.isProven()) return null;
      proofs.add(r.proof);
    }

    // Check the constraint
    if (! a.says.constraint.isTrue()) return null;

    return proofs;
  }

  private List<Proof> checkAntecedentWithVars(Assertion a, D d)
  {
    SubstituteAll subs = new SubstituteAll(a.vars(), this.ac.constants);
    for (Map<Variable, Substitution> theta : subs)
    {
      Assertion thetaA = a.substitute(theta);
      final List<Proof> proofs = checkAntecedentsNoVars(thetaA, d);
      if (proofs != null)
        return proofs;
    }
    return null;
  }

  private Proof canSayOrCanActAs(Assertion q, D d)
  {
    for (final Constant c : this.ac.interesting)
    {
      // Can Act As
      if (! q.says.consequent.subject.equals(c)) // Don't care about A can-act-as A: Tautological
      {
        if (likely.canActAs(q.says.consequent.subject, c))
        {
          /* System.err.println("[?] CanActAs:   "+q); */
          final Assertion renaming =
            Assertion.makeCanActAs(q.speaker, q.says.consequent.subject, c);
          final Result rRenaming = evaluate(renaming, d);
          if (rRenaming.isProven())
          {
            final Assertion renamed =
              Assertion.make(q.speaker, c, q.says.consequent.object);
            final Result rRenamed = evaluate(renamed, d);
            if (!rRenamed.isProven())
            {
              /* System.err.println("} NO"); */
              continue;
            }

            /* System.err.println("} YES"); */
            /* System.err.println("[?] TRUE by CanActAs: "+q); */
            return new CanActAsProof(q, rRenaming.proof, rRenamed.proof);
          }
          /* System.err.println("} NO"); */
        }
      }
      /* } */

      // Can-say
      if (this.ac.voiced.contains(c))
      {
        if (! q.speaker.equals(c)) // Don't care about A says A can-say...: Tautological
        {
          /* System.err.println("[?] CanSay:     "+q); */
          // Disallow this rule when delegation banned
          if (d == D.ZERO) continue;


          final boolean itsLikely = likely.canSay(q.speaker, c);
          /* System.err.println("[>] "+q.speaker+" -> "+c+": "+itsLikely); */
          if (itsLikely)
          {
            // Disallow can-say rule on nested can-say statements
            if (q.says.consequent.object instanceof CanSay) continue;

            for (final D depth : EnumSet.of(D.ZERO, D.INF))
            {
              final Assertion delegator =
                Assertion.makeCanSay(q.speaker, c, depth, q.says.consequent);

              final Result rDelegator = evaluate(delegator, d);
              /* System.err.println("      Delegator   "+rDelegator.isProven()+": "+delegator); */
              if (rDelegator.isProven())
              {
                final Assertion delegation = Assertion.make(c, q.says.consequent);
                final Result rDelegation = evaluate(delegation, depth);
                /* System.err.println("      Delegation "+rDelegation.isProven()+": "+delegation); */
                if (rDelegation.isProven())
                {
                  /* System.err.println("[?] TRUE by CanSay: "+q); */
                  return new CanSayProof(q, rDelegation.proof, rDelegation.proof);
                }
              }
            }
          }
        }
      }
    }

    return new Proof(false);
  }
}
