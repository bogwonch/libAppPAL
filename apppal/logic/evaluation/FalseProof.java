package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.Set;

public class FalseProof extends Proof {
  public FalseProof() {
    this.proven = false;
  }

  protected String showProof(int indent) {
    final StringBuilder builder = new StringBuilder();
    builder.append(new String(new char[indent]).replace("\0", "  "));
    builder.append("<false>");
    builder.append("\n");
    return builder.toString();
  }

  protected String ruleName() {
    return "false";
  }

  protected Set<Proof> dependents() {
    return new HashSet<>();
  }

  protected Assertion goal() {
    return null;
  }
}
