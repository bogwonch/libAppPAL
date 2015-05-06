package apppal.logic.evaluation;

import java.util.HashMap;
import java.util.Map;

import apppal.logic.language.E;
import apppal.logic.language.Variable;

/**
 * Holds the unification of two assertions
 */
public class Unification
{
  public Map<Variable, Substitution> theta;

  private boolean success;

  public Unification()
  {
    this.theta = new HashMap<>();
    this.success = true;
  }

  public Unification(boolean success)
  {
    this();
    if (!success) this.fails();
  }

  public void add(Variable from, E to) throws Exception
  {
    if (this.hasFailed()) throw new Exception("cannot add to failed unification");
    if (this.theta.containsKey(from))
      throw new IllegalArgumentException("attempt to unify " + from + " to multiple values");
    Substitution delta = new Substitution(from, to);
    this.theta.put(from, delta);
  }

  public void fails()
  {
    this.theta = null;
    this.success = false;
  }

  public boolean hasFailed()
  {
    return this.theta == null || this.success == false;
  }

  public String toString()
  {
    if (!this.success)
      return "_|_";

    Object[] deltas = this.theta.values().toArray();
    if (deltas.length == 0) return "{}";

    String str = "{";
    for (int i = 0; i < deltas.length; i++)
      str += ((i == 0)? " ":", ") + deltas[i];
    str += " }";
    return str;
  }

  public void compose(Unification other)
  {
    if (other.hasFailed() || this.hasFailed()) this.fails();
    else
    {
      for (Substitution s : this.theta.values())
      {
        // Apply the substitutions from this to the other
        for (Substitution t : other.theta.values())
          t.substitute(s);

        // Add any missing keys from this
        if (! other.theta.containsValue(s))
          other.theta.put(s.from, s);
      }
      this.theta = other.theta;
    }
  }
}
