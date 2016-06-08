package apppal.lint;

import apppal.logic.evaluation.AC;
import apppal.logic.language.E;
import apppal.logic.language.Predicate;
import apppal.logic.language.Assertion;
import apppal.logic.language.Variable;
import apppal.logic.language.Fact;
import apppal.logic.language.Constant;
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
    }
    private final AC ac;
    private final Graph graph;

    public Redundancy(final AC ac)
    {
      this.ac = ac;
      this.graph = new Graph();
      this.addFromAC();
      graph.dump();
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

