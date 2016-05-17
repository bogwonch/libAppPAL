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
        super(true);
        this.consequent = consequent;
        this.delegator = delegator;
        this.delegation = delegation;
    }
}
