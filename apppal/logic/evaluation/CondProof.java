package apppal.logic.evaluation;

import apppal.logic.language.Assertion;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CondProof extends Proof {
  public final Assertion consequent;
  public final List<Proof> antecedents;

  public CondProof(Assertion c, List<Proof> as) {
    this.proven = true;
    this.consequent = c;
    this.antecedents = as;
  }

  public CondProof(Assertion c) {
    this.proven = true;
    this.consequent = c;
    this.antecedents = new LinkedList<>();
  }

  protected String showProof(int indent) {
    final StringBuilder builder = new StringBuilder();
    builder.append(new String(new char[indent]).replace("\0", "  "));
    builder.append(consequent.toString());
    builder.append("\n");
    for (final Proof a : this.antecedents) {
      builder.append(a.showProof(indent + 1));
    }
    return builder.toString();
  }

  protected String ruleName() {
    return "cond";
  }

  protected Set<Proof> dependents() {
    return new HashSet<>(this.antecedents);
  }

  protected Assertion goal() {
    return this.consequent;
  }
}
