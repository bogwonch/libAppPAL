package apppal.logic.language.constraint;

import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Negation constraint
 */
public class Negation extends Constraint implements Unifiable<Constraint>
{
  final public Constraint value;

  public Negation(Constraint c) {
    super();
    this.value = c;
  }
  public String toString() { return "! "+this.value; }

  @Override
  public boolean hasFailed()
  {
    return this.value.hasFailed();
  }

  public Set<Variable> vars() { return this.value.vars(); }
  public Set<Constant> consts() { return this.value.consts(); }

  @Override
  public Unification unify(final Constraint with)
  { if (with instanceof Negation)
      return this.value.unify(((Negation) with).value);
    else return new Unification(false);
  }

  @Override
  public Constraint substitute(final Map<Variable, Substitution> delta)
  { return new Negation(this.value.substitute(delta)); }

  @Override
  public boolean isTrue()
  {
    if (this.value.hasFailed()) return false;
    else return ! this.value.isTrue();
  }

  @Override
  public void scope(int scope)
  { this.value.scope(scope); }
}
