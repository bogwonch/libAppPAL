package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;
import apppal.logic.language.E;
import apppal.logic.language.EKind;
import apppal.logic.language.Predicate;
import java.io.FileInputStream;
import java.io.IOException;
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
                try { final Schema s = new Schema(arg); }
                catch (IOException e) {return;}
            }
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

        public String toGraphVizString()
        {
            
        }
    }
}
