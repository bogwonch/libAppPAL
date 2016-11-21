package apppal.logic.evaluation;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.LinkedList;
import apppal.logic.language.Assertion;

/**
 * Abstract representation of tree proof
 */
public abstract class Proof
{
  public boolean proven;

  public boolean isKnown()
  {
    return this.proven;
  }
  public boolean isNotKnown()
  {
    return ! this.isKnown();
  }

  public String toString()
  {
    return this.showProof(0);
  }

  public static String toGV(final Proof p)
  { return (new GraphViz(p)).toString(); }

  public static class GraphViz
  {
    private Integer proof_counter;
    private Integer node_counter;

    final Set<String> graph;
    final Map<String, String> assertions;
    final Set<String> proofs;

    public GraphViz(final Proof p)
    {
      this.node_counter = 0;
      this.proof_counter = 0;
      this.graph = new TreeSet<>();
      this.assertions = new TreeMap<>();
      this.proofs = new TreeSet<>();

      this.add(p);
    }

    public String toString()
    {
      final StringBuilder out = new StringBuilder();
      out.append("digraph proof {\n");
      for (final String label : assertions.keySet())
      {
        final String node = assertions.get(label);
        out.append("  "+node+" [label=\""+label+"\"];\n");
      }
      for (final String node : proofs)
        out.append("  "+node+" [shape=point]\n");
      for (final String g : graph)
        out.append("  "+g+"\n");

      out.append("}");
      return out.toString();
    }

    public void add(final Proof p)
    {
      final Assertion goal = p.goal();
      final String key = getAssertion(goal);
      final Set<Proof> children = p.dependents();

      if (! children.isEmpty())
      {
        final String node = getProof();
        this.graph.add(key+" -> "+node+" [label=\""+p.ruleName()+"\"];");

        for (final Proof child : children)
        {
          final String c = getAssertion(child.goal());
          this.graph.add(node+" -> "+c+";");
          this.add(child);
        }
      }
    }

    public String getAssertion(final Assertion a)
    {
      final String key = a.toString();
      if (assertions.containsKey(key))
        return assertions.get(key);
      final String value = "node_"+node_counter;
      node_counter += 1;
      assertions.put(key, value);
      return value;
    }

    public String getProof()
    {
      final String result = "proof_"+proof_counter;
      proof_counter += 1;
      return result;
    }
  }

  abstract protected String showProof(int indent);
  abstract protected String ruleName();
  abstract protected Set<Proof> dependents();
  abstract protected Assertion goal();
}
