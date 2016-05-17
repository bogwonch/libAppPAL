package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import apppal.logic.evaluation.Proof;
import java.util.List;

public class CondProof extends Proof
{
    public final Assertion consequent;
    public final List<Proof> antecedents;

    public CondProof(Assertion c, List<Proof> as)
    {
        super(true);
        this.consequent = c;
        this.antecedents = as;
    }
}
