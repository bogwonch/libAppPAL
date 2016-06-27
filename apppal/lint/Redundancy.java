package apppal.lint;

import apppal.logic.evaluation.AC;
import apppal.logic.language.E;
import apppal.logic.language.Predicate;
import apppal.logic.language.Assertion;
import apppal.logic.language.Variable;
import apppal.logic.language.Claim;
import apppal.logic.language.Fact;
import apppal.logic.language.Constant;
import apppal.logic.evaluation.Unification;
import apppal.Util;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.lang.Comparable;

public class Redundancy
{
    /* A pair is a speaker, and facts.
     * It indicates that we want that speaker to say a predicate
     */
    private class Pair implements Comparable<Pair>
    {
        private final E speaker;   
        private final Fact predicate;

        public Pair(final E fst, final Fact snd)
        {
          this.speaker = fst;
          this.predicate = snd;
        }

        public Assertion toAssertion()
        {
            return new Assertion(speaker, new Claim(predicate));
        }

        public String toString()
        {
            return speaker.toString() + " says " + predicate + ".";
        }

        public boolean equals(Object other)
        {
            if (other == this) return true;
            if (other == null) return false;
            if (this.getClass() != other.getClass()) return false;
            return this.toString().equals(other.toString());
        }

        public int hashCode()
        {
            return this.toString().hashCode();
        }

        public int compareTo(Pair o)
        {
            return this.toString().compareTo(o.toString());
        }
    }
    
    private class Graph
    {
        private final Map<Pair, List<Set<Pair>>> graph;

        public Graph()
        { this.graph = new TreeMap<>(); }

        public void add(final Pair head, final Set<Pair> tail)
        {
            if (! this.graph.containsKey(head))
            { this.graph.put(head, new LinkedList<Set<Pair>>()); }

            final List<Set<Pair>> assoc = this.graph.get(head);
            assoc.add(tail);
            this.graph.put(head, assoc);
        }

        public boolean isFact(final Pair key)
        {
            Util.debug("Checking if fact: "+key.toString());
            final List<Set<Pair>> results = graph.get(key);
            if (results != null) 
            {
                if (results.isEmpty())
                {
                    Util.debug("  No rules");
                    Util.debug("  Fact");
                    return true;
                }
                else
                {
                    if (results.size() == 1 && results.get(0).isEmpty())
                    {
                        Util.debug("    Looks like a fact");
                        return true;
                    }
                    Util.debug("  Has "+results.size()+" rules");
                    for (Set<Pair> ps : results)
                        Util.debug("    "+ps.toString());
                    Util.debug("  Not a fact");
                    return false;
                }
            }
            else
            { 
                if (! key.toAssertion().vars().isEmpty())
                {
                    Util.debug("  Contains variables");
                    Util.debug("  Not a fact");
                    return false;
                }
                Util.warn("policy is incomplete: "+key.toString());
                return true;
            }
        }

        /* Dump the graph out in GraphViz dot format */
        public void dump()
        {
            System.out.println("digraph redundancy {");
            int acc = 0;
            int key_counter = 0;
            for (final Pair key : this.graph.keySet())
            {
                //System.out.println("/* entry: "+ (key_counter++) +" */");
                final List<Set<Pair>> value = this.graph.get(key);
                for (final Set<Pair> group : value)
                {
                    if (group.isEmpty()) 
                    {
                        System.out.println("  \""+key.toString()+"\";");
                    }
                    else
                    {
                        String k = "\"" +key.toString()+" ["+acc+"]\"";
                        System.out.println("  \""+key.toString()+"\" -> "+k+";");
                        for (final Pair p : group)
                        { System.out.println("  "+k+" -> \""+p.toString()+"\";"); }
                        acc += 1;
                    }
                }
            }
            System.out.println("}");
        }

        /* Get the set of keys from the graph that could be found by unifying with the search */
        public final Set<Pair> get_unifying(final Pair search)
        {
            Util.debug("searching for unifications with "+search.toString());
            final Assertion search_ass = search.toAssertion();
            final Set<Pair> results = new HashSet<>();
            for (final Pair candidate : this.graph.keySet())
            {
                if (candidate == search) continue;
                Util.debug("  checking: "+candidate.toString());
                final Unification u = search_ass.unify(candidate.toAssertion());
                if (! u.hasFailed())
                {
                    Util.debug("    it unifies");
                    results.add(candidate);
                } else {
                    Util.debug("    it does not unify");
                }
            }
            return results;
        }

        /* This may be the most ridiculous bit of code I have ever written */
        public void fix()
        {
            int iteration = 0;
            boolean iterate = true;
            while (iterate)
            {
                iteration += 1;
                iterate = false;
                Util.debug("");
                Util.debug("iteration "+iteration+" of redundancy check");
                // For each goal in the AC...
                final Set<Pair> keys = new HashSet<Pair>();
                keys.addAll(this.graph.keySet());
                for (final Pair key : keys)
                {
                    Util.debug("looking at "+key.toString());
                    int proof_iter = 0;
                    final List<Set<Pair>> fixed = new LinkedList<>();
                    // ...and for each set of proof obligations associated with it
                    for (final Set<Pair> proof : graph.get(key))
                    {
                        proof_iter += 1;
                        Util.debug("proof "+proof_iter+" of "+key.toString());
                        List<Set<Pair>> proofs = new LinkedList<>();
                        final Set<Pair> fixed_set = new HashSet<>();
                        proofs.add(fixed_set);
                        // Get the obligation
                        for (final Pair obligation : proof)
                        { 
                            Util.debug("  obligation: "+obligation.toString());
                            // If the obligation is a fact then we're done.
                            // Keep it in the proof an continue.
                            if (isFact(obligation))
                            {
                                Util.debug("    is a fact");
                                for (final Set<Pair> each : proofs)
                                    each.add(obligation);
                            }
                            else
                            {
                                Util.debug("    is not a fact");
                                final List<Set<Pair>> new_proofs = new LinkedList<>();
                                final Set<Pair>unified_obligations = this.get_unifying(obligation);
                                if (! unified_obligations.isEmpty())
                                {
                                    final Set<Pair> p = new HashSet<>();
                                    Util.debug(obligation.toString()+" unifies with:");
                                    for (final Pair o : unified_obligations)
                                    {
                                        if (obligation != o)
                                        {
                                            Util.debug("  * "+o.toString());
                                            p.add(o);
                                        }
                                    }
                                    new_proofs.add(p);
                                } else {
                                    // Otherwise get the set of proof oligations for that fact...
                                    final List<Set<Pair>> obligations = graph.get(obligation);
                                    if (obligations != null)
                                        for (final Set<Pair> rule_obligations_set : graph.get(obligation))
                                        {
                                            Util.debug("      found "+rule_obligations_set.size()+" obligations");
                                            // And add each of them to the obligations for this rule.
                                            for (final Set<Pair> existing_proofs : proofs)
                                            {
                                                final Set<Pair> p = new HashSet<>();
                                                p.addAll(existing_proofs);
                                                p.addAll(rule_obligations_set);
                                                new_proofs.add(p);
                                            }
                                        }
                                }
                                
                                proofs = new_proofs;
                                iterate = true;
                                
                            }
                        } 
                        fixed.addAll(proofs);
                    }

                    this.graph.put(key, clean_proofs(fixed));
                }
            }
        }

        private List<Set<Pair>> clean_proofs(final List<Set<Pair>> proofs)
        {
            Util.debug("Cleaning proofs");
            final List<Set<Pair>> results = new LinkedList<>();

            for (final Set<Pair> proof : proofs)
            {
                Util.debug("  looking at "+proof.toString());
                boolean should_add = true;
                for (final Set<Pair> result : results)
                {
                    if (proof.size() == result.size())
                    {
                        /* Check if a subset */
                        boolean subset = true;
                        for (final Pair p : proof)
                        {  
                            boolean found_it = false;
                            Util.debug("    "+p.toString());
                            for (final Pair r : result)
                            {
                                Util.debug("      "+r.toString());
                                if (p.toString().equals(r.toString()))
                                {
                                    Util.debug("        yes");
                                    found_it = true;
                                    break;
                                }
                            }
                            if (! found_it) 
                            {  
                                Util.debug("      failed to find match");
                                subset = false;
                                break;
                            }
                        }

                        if (subset)
                        {
                            Util.debug("    we already have a proof");
                            should_add = false;
                            break;
                        }
                    }
                }
                if (should_add)
                {
                    Util.debug("    adding proof");
                    results.add(proof);
                }
            }
            return results;
        }


        public void check()
        {
            for (final Pair key : this.graph.keySet())
            {
                final List<Set<Pair>> proofs = graph.get(key);
                for (final Set<Pair> a : proofs)
                    for (final Set<Pair> b : proofs)
                    {
                        if (a == b) continue;
                        if (a.containsAll(b))
                        {
                            if (b.containsAll(a))
                                Util.warn("equivalent proofs in the checking of "+key.toString());
                            else
                                Util.warn("redundant proofs in the checking of "+key.toString());
                        }
                    }
            }
        }
    }
    private final AC ac;
    private final Graph graph;

    public Redundancy(final AC ac)
    {
      this.ac = ac;
      this.graph = new Graph();
      this.addFromAC();
      graph.dump();
      graph.fix();
      graph.dump();
      graph.check();
    }

    private void addFromAC()
    {
         for (final Assertion a : this.ac.assertions)
         {
             final E speaker = a.speaker;
             final Fact outcome = a.says.consequent;

             final Pair head = new Pair(speaker, outcome);
             final Set<Pair> tail = new HashSet<>();

             for (final Fact f : a.says.antecedents)
             { tail.add(new Pair(speaker, f)); }

             this.graph.add(head, tail);
         }
    }
}

