package apppal.logic.language.constraint;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Constraint Entity that has failed.
 */
public class Fail extends CE
{
  @Override
  public String toString()
  {
    return "?";
  }

  @Override
  public Set<Variable> vars()
  {
    return new HashSet<>();
  }

  @Override
  public Set<Constant> consts()
  {
    return new HashSet<>();
  }

  @Override
  public CE eval()
  {
    return this;
  }

  @Override
  public Unification unify(CE with)
  {
    return new Unification(false);
  }

  @Override
  public CE substitute(Map<Variable, Substitution> delta)
  {
    return this;
  }
}
