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
 * The trivially true class
 */
public class Sat extends Constraint implements Unifiable<Constraint>
{
  public Sat() {
    super();
  }

  public String toString()
  {
    return "sat";
  }

  @Override
  public boolean hasFailed()
  { return false; }

  public Set<Variable> vars() { return new HashSet<>(); }
  public Set<Constant> consts() { return new HashSet<>(); }

  @Override
  public boolean isTrue()
  { return true; }

  @Override
  public boolean isTriviallyTrue() { return true; }

  @Override
  public Unification unify(final Constraint with)
  { return new Unification(with instanceof Sat); }

  @Override
  public Constraint substitute(final Map<Variable, Substitution> delta)
  { return this; }
}
