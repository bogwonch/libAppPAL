package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Constant;
import apppal.logic.language.E;
import apppal.logic.language.EKind;
import apppal.logic.language.Predicate;
import apppal.logic.language.Variable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Schema 
{
    final AC ac;
    final Set<Entity> speakers;
    final Set<String> decisions;
    final Set<Relation> relations;

    public static void main(final String args[])
    {
        for (final String arg : args)
            {
                try { 
                    final Schema s = new Schema(arg); 
                    s.printGraph(); 
                }
                catch (IOException e) {return;}
            }
    }

    public void printGraph()
    {
        final SchemaGraph g = new SchemaGraph();
        for (final Relation r : relations) g.add(r);

        System.out.println(g);
    }

    public Schema(final String path) throws IOException
    {
        try { this.ac = new AC(new FileInputStream(path)); }
        catch (IOException err)
        {
            Util.error("couldn't load "+path+": "+err);
            throw(err);
        }

        this.speakers = new TreeSet<>();
        this.decisions = new TreeSet<>();
        this.relations = new TreeSet<>();

        for (final Assertion a : this.ac.assertions)
        { this.add_assertion(a); }
    }

    private void add_assertion(final Assertion a)
    {
        final Entity context = new Entity(a.speaker);
        final Entity subject = new Entity(a.says.consequent.subject);
        if (a.isCanActAs())
        {;}
        else if (a.isCanSay())
        {
            final CanSay cs = (CanSay)a.says.consequent.object;
            if (cs.fact.object instanceof Predicate)
            {
                final String predicate = ((Predicate)cs.fact.object).name;
                speakers.add(context);
                speakers.add(subject);
                relations.add(new Relation(context, subject, predicate));
                relations.add(new Relation(subject, subject, predicate));
            }
        }
        else
        {
            final String predicate = ((Predicate)a.says.consequent.object).name;
            final Relation r = new Relation(context, context, predicate);
            relations.add(r);
        }
    }

    public class Entity
        implements Comparable<Entity>
    {
        private final boolean is_specific;
        private final String representation;
        public Entity(Constant e)
        {
            this.is_specific = true;
            this.representation = e.toString();
        }

        public Entity(E e)
        {
            this.is_specific = e.kind == EKind.CONSTANT;
            this.representation = e.toString();
        }

        public Entity(Variable e)
        {
            this.is_specific = false;
            this.representation = e.toString();
        }

        public String toString() { return representation; }

        public int compareTo(final Entity other)
        {
            if (this.is_specific && ! other.is_specific)
                return -1;
            if (!this.is_specific && other.is_specific)
                return +1;
            else
                return this.representation.compareTo(other.representation);
        }
    }

    public class SchemaGraph
    {
        // Each map is from the AppPAL name to the graphviz label
        private final Map<String, String> contexts;
        private final Map<String, String> speakers;
        private final Map<String, String> predicates;
        private final List<Path> paths;

        private int nonce = 0;

        private class Path
        {
            public final String from;
            public final String to;
            public final String attributes;

            public Path(final String from, final String to) { this(from, to, ""); }
            public Path(final String from, final String to, final String attributes)
            {
                this.from = from; 
                this.to = to; 
                this.attributes = attributes;   
            }
            
            public String toString()
            {
                final StringBuilder out = new StringBuilder();
                out.append(from);
                out.append(" -> ");
                out.append(to);
                if (! attributes.equals(""))
                {
                    out.append(" ");
                    out.append(attributes);
                    out.append("\n");
                }

                return out.toString();
            }

        }

        public SchemaGraph()
        {
            this.contexts = new HashMap<>();
            this.speakers = new HashMap<>();
            this.predicates = new HashMap<>();
            this.paths = new LinkedList<>();
        }

        private String add(final String key, final Map<String, String> to)
        {
            if (! to.containsKey(key))
            {
                final String target = key.replaceAll("[^a-zA-Z0-9]","_") + "__" + nonce++;
                to.put(key, target);
                return target;
            }
            else return to.get(key);
        }

        private String add_context(final Entity e) { return add(e.toString(), contexts); }
        private String add_speaker(final Entity e) { return add(e.toString(), speakers); }
        private String add_predicate(final String e) { return add(e, predicates); }

        public void add(final Relation r)
        {
            final String c = add_context(r.context);
            final String s = add_speaker(r.target);
            final String p = add_predicate(r.decision);

            if (r.context == r.target)
            { 
                this.paths.add(new Path(c,s));
                this.paths.add(new Path(s,p));
            }
            else
            {
                this.paths.add(new Path(c,s,"[label=\""+r.decision+"\"]"));
                this.paths.add(new Path(s,p));
            }
        }

        public String toString()
        {
            StringBuilder out = new StringBuilder();

            out.append("digraph schema {\n");
            for (final Map.Entry<String, String>e : contexts.entrySet())
                out.append("  "+e.getValue()+" [shape=ellipse color=red label=\""+e.getKey()+"\"]\n");
            out.append("\n");
            for (final Map.Entry<String, String>e : speakers.entrySet())
                out.append("  "+e.getValue()+" [shape=parallelogram color=blue label=\""+e.getKey()+"\"]\n");
            out.append("\n");
            for (final Map.Entry<String, String>e : predicates.entrySet())
                out.append("  "+e.getValue()+" [shape=box color=green label=\""+e.getKey()+"\"]\n");
            out.append("\n");

            for (final Path p : paths)
                out.append("  "+p+"\n");
            out.append("}");
                
            return out.toString();
        }
    }

    public class Relation
        implements Comparable<Relation>
    {
        final Entity context, target;
        final String decision;

        public Relation(Entity context, Entity target, String decision)
        {
            this.context = context;
            this.target = target;
            this.decision = decision;
        }

        public int compareTo(final Relation other)
        {
            int comparison;
            comparison = this.context.compareTo(other.context);
            if (comparison != 0) return comparison;
            comparison = this.target.compareTo(other.target);
            if (comparison != 0) return comparison;
            return this.decision.compareTo(other.decision);
        }
    }
}
