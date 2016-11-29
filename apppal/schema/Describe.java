package apppal.schema;

import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Fact;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Describe {
  private final AC ac;
  private final Set<String> predicates;
  private final Map<String, Integer> head_count;
  private final Map<String, Integer> body_count;

  public Describe(final AC ac) {
    this.ac = ac;
    this.head_count = new HashMap<>();
    this.body_count = new HashMap<>();
    this.predicates = new HashSet<>();

    for (final Assertion a : ac.assertions) {
      final String head = extractFact(a.says.consequent);
      this.predicates.add(head);
      add_or_increment(head, head_count);

      for (final Fact f : a.says.antecedents) {
        final String body = extractFact(f);
        this.predicates.add(body);
        add_or_increment(body, body_count);
      }
    }
  }

  public String toCSVString() {
    final StringBuilder result = new StringBuilder();
    result.append("predicate, head_total, body_total, total\n");
    for (final String str : this.predicates) {
      final Integer head = (head_count.get(str) == null ? 0 : head_count.get(str));
      final Integer body = (body_count.get(str) == null ? 0 : body_count.get(str));
      result.append(str);
      result.append(", ");
      result.append(head.toString());
      result.append(", ");
      result.append(body.toString());
      result.append(", ");
      result.append(head + body);
      result.append("\n");
    }
    return result.toString();
  }

  public static String extractFact(final Fact f) {
    final String predicate = f.getPredicate();
    if (predicate != null) return predicate;
    else if (f.object instanceof CanSay) return extractFact(((CanSay) f.object).fact);
    else return null;
  }

  public static void add_or_increment(final String str, final Map<String, Integer> data) {
    final Integer v = data.get(str);
    if (v == null) data.put(str, new Integer(1));
    else data.put(str, v + 1);
  }
}
