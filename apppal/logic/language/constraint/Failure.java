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
 * Represents a constraint that has failed (i.e. an error occurred during checking.
 *
 * Never evaluates to a proof.
 */
public class Failure extends Constraint implements Unifiable<Constraint>
{
  @Override
  public Unification unify(Constraint with)
  {
    return new Unification(false);
  }

  @Override
  public Constraint substitute(Map<Variable, Substitution> delta)
  {
    return this;
  }

  @Override
  public boolean isTrue()
  {
    return false;
  }

  @Override
  public String toString()
  {
    return "_|_";
  }

  @Override
  public boolean hasFailed()
  {
    return true;
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
}
