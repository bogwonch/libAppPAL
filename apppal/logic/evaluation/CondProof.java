package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import apppal.logic.evaluation.Proof;
import java.util.List;
import java.lang.StringBuilder;

public class CondProof extends Proof
{
    public final Assertion consequent;
    public final List<Proof> antecedents;

    public CondProof(Assertion c, List<Proof> as)
    {
        this.proven = true;
        this.consequent = c;
        this.antecedents = as;
    }

    protected String showProof(int indent)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getIndent(indent));
        builder.append(consequent.toString());
        builder.append("\n");
        for (final Proof a : this.antecedents)
        {
            builder.append(a.showProof(indent+1));
        }
        return builder.toString();
    }
}
