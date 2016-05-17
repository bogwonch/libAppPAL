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
        super(true);
        this.consequent = consequent;
        this.renaming = renaming;
        this.renamed = renamed;
    }
}
