package apppal.lint;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Unification;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Claim;
import apppal.logic.language.Constant;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.Variable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Comparable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Redundancy
{
    /* A goal is something you might want to prove.
     * A fact by a speaker essentially.
     */
    private class Goal implements Comparable<Goal>
    {
        private final E speaker;
        private final Fact fact;
        private final Assertion assertion;
        private final String string;

        public Goal(final E speaker, final Fact fact)
        {
            this.speaker = speaker;
            this.fact = fact;
            this.assertion = new Assertion(speaker, new Claim(fact));
            this.string = this.speaker + " says " + this.fact + ".";
        }

        public Assertion toAssertion() { return this.assertion; }
        public String toString() { return this.string; }
        public int compareTo(final Goal o) { return this.toString().compareTo(o.toString()); }
        public int hashCode() { return this.toString().hashCode(); }
        public boolean equals(final Object o)
        {
            if (o == this) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            return this.toString().equals(o.toString());
        }
    }

    /* A proof is a set of goals that need to be satisfied.
     */
    private class Proof implements Comparable<Proof>
    {
        public final Set<Goal> goals;
    
        public Proof() { this.goals = new TreeSet<>(); } 
        public Proof(Set<Goal> goals)
        {
            this();
            this.goals.addAll(goals);
        }

        public void add(final Goal g) { this.goals.add(g); }

        public Proof search_replace(final Goal target, Set<Goal> replacements)
        {
            final Proof result = new Proof();
            for (final Goal g : this.goals)
            {
                if (g.equals(target))
                    result.goals.addAll(replacements);
                else 
                    result.goals.add(g);
            }
            return result;
        }

        public int compareTo(final Proof o)
        { 
            if (this.goals.size() < o.goals.size()) return -1;
            if (this.goals.size() > o.goals.size()) return +1;
            if (this.goals.containsAll(o.goals)) return 0;
            // TODO: investigate if this is correct
            return this.goals.hashCode() - o.goals.hashCode();
        }

        public String graph_dump(final String key)
        {
            StringBuilder out = new StringBuilder();
            for (final Goal g : this.goals)
            {
                out.append("  ");
                out.append(key);
                out.append(" -> ");
                out.append("\"");
                out.append(g);
                out.append("\";\n");
            }
            return out.toString();
        }

        /* A proof uses a node if the nodes owner is in the goals */
        public boolean uses(final Graph.Node node)
        { return this.goals.contains(node.owner); }

        /* A proof is stated if there is nothing to prove */
        public boolean is_stated() { return this.goals.isEmpty(); }
    }

    private class Graph
    {
        public Map<Goal, Node> graph;
        public Set<Goal> goals;

        public Graph()
        {
            this.graph = new TreeMap<>();
            this.goals = new TreeSet<>();
        }

        public void dump(OutputStream o)
        {
            final PrintStream out = new PrintStream(o);
            final StringBuilder goals = new StringBuilder();
            final StringBuilder rules = new StringBuilder();

            for (final Goal key : this.goals)
            {
                final Node links = this.graph.get(key);
                goals.append("  \"");
                goals.append(key);
                goals.append("\" [shape=box");
                if (links != null && links.is_flat())
                    goals.append(" color=green");
                else if (links != null && links.is_reduced(this))
                    goals.append(" color=blue");
                else if (links != null && links.is_simple(this))
                    goals.append(" color=red");
                goals.append("];\n");
                if (links != null)
                    rules.append(links.graph_dump(key));
            }
            
            out.println("digraph graphname {");
            out.println(goals);
            out.println(rules);
            out.println("}");
            out.close();
        }

        public void add(final Assertion a)
        {
            this.add_assertion(a);
            if (a.isCanSay())
            {
                final E speaker = a.speaker;
                final CanSay vp = (CanSay) a.says.consequent.object;
                final Fact outcome = vp.fact;
                final E delegatee = a.says.consequent.subject;
                final Goal result = new Goal(speaker, outcome);
                final Goal delegation = new Goal(delegatee, outcome);
                final Goal can_say = new Goal(speaker, a.says.consequent);
                final Set<Goal> tail = new TreeSet<>();
                tail.add(delegation);
                tail.add(can_say);
                this.add(result, new Node(result, new Proof(tail)));
            }
        }

        private void add_assertion(final Assertion a)
        {
            final E speaker = a.speaker;
            final Fact outcome = a.says.consequent;
            final Goal head = new Goal(speaker, outcome);
            final Set<Goal> tail = new TreeSet<>();
            for (final Fact f : a.says.antecedents)
            { tail.add(new Goal(speaker, f)); }
            final Node node = new Node(head, new Proof(tail));
            this.add(head, node);
        }

        public void add(final Goal head, final Node tail)
        {
            final Node node = this.graph.get(head);
            if (node == null)
                this.graph.put(head, tail);
            else
                node.merge(tail);

            this.goals.add(head);
        }

        public void update_goals()
        {
            for (final Node n : this.graph.values())
            {
                this.goals.addAll(n.goals());
            }
            for (final Goal goal : this.goals)
            {
                if (this.graph.get(goal) == null)
                {
                    this.add(goal, new Node(goal));
                }
            }
        }

        public void link_with_unification()
        {
            for (final Goal g1 : this.goals)
            {
                for (final Goal g2 : this.goals)
                {
                    if (g1.equals(g2)) continue;
                    final Assertion a1 = g1.toAssertion();
                    final Assertion a2 = g2.toAssertion();
                    if (! a1.isGround())
                    {
                        final Unification u = a1.unify(a2);
                        if (! u.hasFailed())
                        {
                            Util.debug(a1+" unifies with "+a2);
                            final Proof unification_proof = new Proof();
                            unification_proof.add(g2);
                            final Node n = new Node(g1, unification_proof);
                            this.add(g1, n);
                        }
                    }
                }
            }
        }

        public void fix_backrefs()
        {
            for (final Node n : this.graph.values())
                n.fix_backrefs(this);
        }

        public int flatten()
        {
            List<Node> to_flatten = new LinkedList<>();
            for (final Node n : this.graph.values())
                if (n.needs_flattening(this)) to_flatten.add(n);
            // If you don't do it by marking those you want to flatten before flattening
            // it can get a little eager and flatten everything.
            // I want to do it a little slower.
            for (final Node n : to_flatten)
                n.flatten(this);
            return to_flatten.size();
        }

        public class Node
        {
            public final Goal owner;
            public final Set<Proof> proofs;

            /* A back reference to a proof in another node */
            public class BackRef
            {
                public final Node node;
                public BackRef(final Node node) { this.node = node; }

            }

            public final Set<BackRef> used_by;

            public Node(final Goal owner)
            {
                this.owner = owner;
                this.proofs = new TreeSet<>();
                this.used_by = new HashSet<>();
            }

            public Node(final Goal owner, final Proof proof)
            {
                this(owner);
                this.add(proof);
            }

            public Node(final Goal owner, final Set<Proof> proofs)
            {
                this(owner);
                for (final Proof proof : proofs)
                    this.add(proof);
            }

            /* A node is simple if it does not depend on any additional rules to decide it
             * i.e. all the facts associated with it are ground.
             */
            public boolean is_simple(final Graph graph)
            {
                for (final Proof proof : proofs)
                    for (final Goal goal : proof.goals)
                    {
                        final Node node = graph.graph.get(goal);
                        if (! node.is_flat()) return false;
                    }
                return true;
            }

            /* A node is flat if all its proofs are stated */
            public boolean is_flat()
            {
                for (final Proof p : this.proofs)
                { if (! p.is_stated()) return false; }
                return true;
            }

            /* A node needs hoisting if it isn't flat, isn't reduced, but it is simple */
            public boolean needs_flattening(final Graph graph)
            { return ! (this.is_flat() || this.is_reduced(graph)) && this.is_simple(graph); }

            /* A node is reduced if it is simple and it is not used by anything */
            public boolean is_reduced(final Graph graph)
            { return this.is_simple(graph) && this.used_by.isEmpty(); }

            public void fix_backrefs(final Graph graph)
            {
                for (final Proof proof : proofs)
                {
                    for (final Goal goal : this.goals())
                    {
                        final Node update = graph.graph.get(goal);
                        if (update != null)
                        {
                            update.used_by.add(new BackRef(this));
                        }
                        else
                            Util.warn("policy may be incomplete: "+goal);
                    }
                }
            }

            public boolean flatten(final Graph graph)
            {
                if (! this.needs_flattening(graph)) return false;
                final Set<BackRef> to_remove = new HashSet<>();
                for (final BackRef ref : this.used_by)
                {
                    ref.node.flatten(this, this.proofs);
                    to_remove.add(ref);
                }
                this.used_by.removeAll(to_remove);
                return true;
            }

            public void flatten(final Node target, final Set<Proof> replacements)
            {
                final List<Proof> to_delete = new LinkedList<>();
                final List<Proof> to_replace = new LinkedList<>();
                for (final Proof proof : this.proofs)
                {
                    if (proof.uses(target))
                    {
                        to_delete.add(proof);
                        for (final Proof r : replacements)
                            to_replace.add(proof.search_replace(target.owner, r.goals));
                    }
                }
                Util.debug("Flattening: "+this.owner);
                Util.debug("  before: "+this.proofs.size()+" proofs");
                this.proofs.removeAll(to_delete);
                Util.debug("  after removal: "+this.proofs.size()+" proofs");
                this.proofs.addAll(to_replace);
                Util.debug("  after replacements: "+this.proofs.size()+" proofs");
            }

            public Set<Goal> goals()
            {
                final Set<Goal> result = new TreeSet<>();
                for (final Proof p : this.proofs)
                    result.addAll(p.goals);
                return result;
            }

            public void add(final Proof p) 
            {
                this.proofs.add(p);
            }

            public void merge(final Node o)
            {
                this.proofs.addAll(o.proofs);
                this.used_by.addAll(o.used_by);
            }

            public String graph_dump(final Goal key)
            {
                int acc = 0;
                final String k = "\""+key+"\"";
                final StringBuilder out = new StringBuilder();
                for (final Proof proof : proofs)
                {
                    final String pk = "\""+key+" ["+acc+"]\"";
                    if (proof.is_stated())
                    {
                        out.append("  ");
                        out.append(pk);
                        out.append(" [shape=point label=\"\"];\n");
                        out.append("  ");
                        out.append(k);
                        out.append(" -> ");
                        out.append(pk);
                        out.append(";\n");
                    }
                    else
                    {
                        out.append("  ");
                        out.append(k);
                        out.append(" -> ");
                        out.append(pk);
                        out.append(";\n");
                        out.append(proof.graph_dump(pk));
                        acc += 1;
                    }
                }

                for (final BackRef ref : this.used_by)
                {
                    out.append("  ");
                    out.append("\"");
                    out.append(ref.node.owner);
                    out.append("\"");
                    out.append(" -> ");
                    out.append(k);
                    out.append("[style=dotted dir=back];\n");
                }

                return out.toString();
            }
        }
    }

    final AC ac;
    final Graph graph;

    public Redundancy(final AC ac)
    {
        this.ac = ac;
        this.graph = new Graph();
        for (final Assertion a : this.ac.assertions)
            graph.add(a);
        this.graph.update_goals();
        this.graph.link_with_unification();
        this.graph.fix_backrefs();
        try 
        {
            graph.dump(new FileOutputStream("graph-0.dot"));
            int iterations = 1;
            int flattened = 0;
            do 
            {
                flattened = this.graph.flatten();
                Util.info("flattened "+flattened+" node(s)");
                if (flattened > 0)
                    graph.dump(new FileOutputStream("graph-"+(iterations++)+".dot"));
            }
            while (flattened > 0);
        }
        catch (FileNotFoundException err)
        {
            Util.error("couldn't dump graph to file: "+err);
        }
    }
}

