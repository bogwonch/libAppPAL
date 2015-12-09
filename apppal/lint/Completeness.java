package apppal.lint;

import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.Constant;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.Variable;
import apppal.Util;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Completeness
{
    private static final Variable VAR = new Variable("*");
    private final AC ac;
    private final Map<E, Set<String>> predicates;
    private final Map<E, Set<String>> decidable;

    public Completeness(final AC ac)
    {
        this.ac = ac;
        this.predicates = new HashMap<>();
        this.decidable = new HashMap<>();

        this.fix();
    }

    public boolean decidable(final E speaker, final String predicate)
    {
        return lookup(decidable, speaker, predicate);
    }

    public boolean decidable(final E speaker, final Fact fact)
    {
        if (fact.object instanceof Predicate)
            return decidable(speaker, ((Predicate) fact.object).name);
        else
            return true; // TODO: don't assume all can-say, can-act-as statements are derivable.
    }

    public boolean decidable(final Assertion a)
    {
        return decidable(a.speaker, a.says.consequent);
    }

    /**
     * Find all predicates which may be undecidable for a speaker
     */
    public Map<E, Set<String>> problems()
    {
        final Map<E, Set<String>> problems = new HashMap<>();
        for (final E speaker : this.predicates.keySet())
            for(final String predicate : this.predicates.get(speaker))
                if (! decidable(speaker, predicate))
                    add(problems, speaker, predicate);
        return problems;
    }

    /**
     * Fixed point algorithm for finding all decidable predicates.
     *
     * A predicate is decidable for a speaker if there exists enough
     * information in the assertion context to decide whether that predicate
     * could be said by the speaker without remote attestation.
     *
     * All can-say and can-act-as statements are decidable (over approximation)
     *
     * An assertion speaker says _ predicate(_) is decidable if:
     *   - there exists an assertion speaker says _ predicate(_) if p1, .. pn.
     *   - for all i in 1..n: A says pi is decidable.
a     */
    private void fix()
    {
        int iteration = 0;
        boolean iterate = true;
        Util.debug("checking completeness");

        while (iterate == true)
        {
            Util.debug("completeness fixed point iteration "+(++iteration));

            iterate = false;
            for (final Assertion a : ac.assertions)
            {
                Util.debug("examining '"+a+"'");
                final E speaker;
                final String predicate;
                final List<Fact> conditions;

                // Get the speaker
                if (a.speaker instanceof Variable)
                    speaker = VAR;
                else
                    speaker = a.speaker;

                // Get the predicate
                if (a.says.consequent.object instanceof Predicate)
                {
                    predicate = ((Predicate) a.says.consequent.object).name;
                }
                else
                {
                    Util.debug("skipping");
                    continue;
                }

                // Get the conditions
                conditions = a.says.antecedents;

                Util.debug("<"+speaker+", "+predicate+">");

                add(predicates, speaker, predicate);
                if (decidable(speaker, predicate))
                {
                    Util.debug("known decidable");
                    continue;
                }
                else
                {
                    boolean all_conds_decidable = true;
                    for (final Fact f : conditions)
                    {
                        if (! decidable(speaker, f))
                        {
                            all_conds_decidable = false;
                            break;
                        }
                    }

                    if (all_conds_decidable)
                    {
                        Util.debug("found decidable");
                        add(decidable, speaker, predicate);
                        iterate = true;
                    }
                }
            }
        }

        Util.debug("completeness fixed point found in "+iteration+" iterations");
    }


    /**
     * Looks up a entity and predicate in a map
     */
    private static boolean lookup(final Map<E, Set<String>> table, final E e, final String s)
    {
        final Set<String> results = table.get(e);
        if (results == null)
            return false;
        else
            return results.contains(s);
    }

    /**
     * Add an entity and key to a map
     */
    private static void add(final Map<E, Set<String>> table, final E e, final String s)
    {
        final Set<String> set = table.get(e);
        if (set == null)
        {
            final Set<String> results = new HashSet<>();
            results.add(s);
            table.put(e, results);
        }
        else
            set.add(s);
    }
}
