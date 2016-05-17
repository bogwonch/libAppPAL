package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import apppal.logic.evaluation.Proof;
import java.util.List;

public class CanSayProof extends Proof
{
    public final Assertion consequent;
    public final Proof delegator;
    public final Proof delegation;

    public CanSayProof(Assertion consequent, Proof delegator, Proof delegation)
    {
        this.proven = true;
        this.consequent = consequent;
        this.delegator = delegator;
        this.delegation = delegation;
    }
    
    protected String showProof(int indent)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(new String(new char[indent]).replace("\0", "  "));
        builder.append(consequent.toString());
        builder.append("\n");
        builder.append(this.delegator.showProof(indent+1));
        builder.append("\n");
        builder.append(this.delegation.showProof(indent+1));
        builder.append("\n");
       
        return builder.toString();
    }
}
