package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import apppal.logic.evaluation.Proof;
import java.util.List;

public class CanActAsProof extends Proof
{
    public final Assertion consequent;
    public final Proof renaming;
    public final Proof renamed;

    public CanActAsProof(Assertion consequent, Proof renaming, Proof renamed)
    {
        this.proven = true;
        this.consequent = consequent;
        this.renaming = renaming;
        this.renamed = renamed;
    }
    
    protected String showProof(int indent)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(new String(new char[indent]).replace("\0", "  "));
        builder.append(consequent.toString());
        builder.append("\n");
        builder.append(this.renaming.showProof(indent+1));
        builder.append(this.renamed.showProof(indent+1));
       
        return builder.toString();
    }
}
