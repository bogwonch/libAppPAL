package apppal.lint;

import apppal.logic.evaluation.AC;
import apppal.logic.language.E;
import apppal.logic.language.Predicate;
import apppal.logic.language.Assertion;
import apppal.logic.language.Variable;
import apppal.logic.language.Fact;
import apppal.logic.language.Constant;
import apppal.Util;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;

public class Redundancy
{
    /* A pair is a speaker, and facts.
     * It indicates that we want that speaker to say a predicate
     */
    private class Pair
    {
        private final E speaker;   
        private final Fact predicate;

        public Pair(final E fst, final Fact snd)
        {
          this.speaker = fst;
          this.predicate = snd;
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
    }
    
    private class Graph
    {
        private final Map<Pair, List<Set<Pair>>> graph;

        public Graph()
        { this.graph = new HashMap<>(); }

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
            final List<Set<Pair>> results = graph.get(key);
            if (results != null) return results.isEmpty();
            else
            { 
                Util.warn("policy is incomplete: "+key.toString());
                return true;
            }
        }

        /* Dump the graph out in GraphViz dot format */
        public void dump()
        {
            System.out.println("digraph redundancy {");
            int acc = 0;
            for (final Pair key : this.graph.keySet())
            {
                List<Set<Pair>> value = this.graph.get(key);
                for (final Set<Pair> group : value)
                {
                    String k = "\"" +key.toString()+" ["+acc+"]\"";
                    System.out.println("  \""+key.toString()+"\" -> "+k+";");
                    for (final Pair p : group)
                    { System.out.println("  "+k+" -> \""+p.toString()+"\";"); }
                    acc += 1;
                }
            }
            System.out.println("}");
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
                Util.debug("iteration "+iteration+" of redundancy check");
                for (final Pair key : this.graph.keySet())
                {
                    Util.debug("looking at "+key.toString());
                    int proof_iter = 0;
                    final List<Set<Pair>> fixed = new LinkedList<>();
                    for (final Set<Pair> proof : graph.get(key))
                    {
                        proof_iter += 1;
                        Util.debug("proof "+proof_iter+" of "+key.toString());
                        List<Set<Pair>> proofs = new LinkedList<>();
                        final Set<Pair> fixed_set = new HashSet<>();
                        proofs.add(fixed_set);
                        for (final Pair obligation : proof)
                        { 
                            Util.debug("  obligation: "+obligation.toString());
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
                                for (final Set<Pair> rule_obligations_set : graph.get(obligation))
                                    for (final Set<Pair> existing_proofs : proofs)
                                    {
                                        final Set<Pair> p = new HashSet<>();
                                        p.addAll(existing_proofs);
                                        p.addAll(rule_obligations_set);
                                        new_proofs.add(p);
                                    }
                                proofs = new_proofs;
                                iterate = true;
                            }
                        } 
                        fixed.addAll(proofs);
                    }

                    this.graph.put(key, fixed);
                }
            }
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

