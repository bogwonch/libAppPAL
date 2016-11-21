package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import apppal.logic.evaluation.Proof;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuilder;

public class FalseProof extends Proof
{
    public FalseProof()
    {
        this.proven = false;
    }

    protected String showProof(int indent)
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(new String(new char[indent]).replace("\0", "  "));
        builder.append("<false>");
        builder.append("\n");
        return builder.toString();
    }

    protected String ruleName()
    { return "false"; }
    
    protected Set<Proof> dependents()
    { return new HashSet<>(); }

    protected Assertion goal()
    { return null; }
    
    
}
