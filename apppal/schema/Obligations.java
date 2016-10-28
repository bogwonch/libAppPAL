package apppal.schema;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.CanSay;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;

/* Writes a new policy containing all the obligations that must be satisfied */
public class Obligations 
{
    private final AC ac;
    private final List<Obligation> obligations;

    public Obligations(final AC ac)
    {
        this.ac = ac;
        this.obligations = new LinkedList<>();

        for (final Assertion a : this.ac.assertions)
        {
            this.obligations.addAll(extractMusts(a));
        }

        for (final Obligation o : this.obligations)
        {
            final Assertion a = o.toCheck();
            final boolean old_debug = Util.enable_debug;
            Util.enable_debug = false;
            System.out.println(a);
            Util.enable_debug = old_debug;
        }
    }

    public static List<Obligation> extractMusts(final Assertion a)
    {
        List<Obligation> musts = new LinkedList<>();
        final E e = a.speaker;
        final Fact head = extractFact(a.says.consequent);
        if (head != null)
        {
            musts.add(new Obligation(e, head));
        }

        for (Fact f : a.says.antecedents)
        {
            final Fact body = extractFact(f);
            if (body != null)
                musts.add(new Obligation(e, body));
        }
        
        return musts;
    }

    public static Fact extractFact(final Fact f)
    {
        final String predicate = f.getPredicate();
        if (predicate != null && predicate.startsWith("must"))
            return f;
        else if (f.object instanceof CanSay)
            return extractFact(((CanSay) f.object).fact);
        else
            return null;
    }

    public static class Obligation
    {
        final E e;
        final Fact fact;

        public Obligation(final E e, final Fact fact)
        {
            this.e = e; 
            this.fact = fact;
        }

        public Assertion toCheck()
        {
            // We need to disable the scopes to parse the assertion
            final boolean old_debug = Util.enable_debug;
            Util.enable_debug = false;
            final String obligation = this.fact.toString().replaceFirst(" must", " hasSatisfiedObligationTo");
            final String check = this.fact.toString().replaceFirst(" must", " has");
            final String assertion = this.e+" says "+obligation+" if "+this.fact+", "+check+".";
            Util.enable_debug = old_debug;
            Util.debug("obligation to: "+assertion);
            try {
                return Assertion.parse(assertion);
            } catch (IOException e) {
                Util.error("malformed obligation: "+e);
                return null;
            }
        }
    }
}
