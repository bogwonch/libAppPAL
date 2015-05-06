package apppal.logic.language.constraint;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Booleans are the only value we need...
 */
public class Bool extends CE implements Unifiable<CE>
{
  public final boolean value;
  public Bool(boolean value)
  {
    this.value = value;
  }

  public String toString()
  {
    if (this.value)
      return "true";
    else
      return "false";
  }

  public Set<Variable> vars() { return new HashSet<>(); }

  @Override
  public CE eval()
  {
    return this;
  }

  public Set<Constant> consts() { return new HashSet<>(); }

  @Override
  public Unification unify(final CE with)
  {
    return new Unification((with instanceof Bool) &&
                           (this.value == ((Bool) with).value));
  }

  @Override
  public Bool substitute(Map<Variable, Substitution> delta)
  {
    return this;
  }

  @Override
  public int hashCode()
  { return (this.value)? 1 : 0; }

  @Override
  public boolean equals(Object other)
  {
    if (!(other instanceof Bool))
    {
      return false;
    }
    Bool b = (Bool) other;
    return this.value == b.value;
  }
}
