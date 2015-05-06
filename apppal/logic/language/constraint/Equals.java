package apppal.logic.language.constraint;

import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Equality in constraints.
 */
public class Equals extends Constraint implements Unifiable<Constraint>
{
  public final CE lhs;
  public final CE rhs;

  public Equals(CE lhs, CE rhs)
  {
    super();
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public String toString() { return this.lhs+" = "+this.rhs; }

  @Override
  public boolean hasFailed()
  {
    return (this.lhs instanceof Fail) || (this.rhs instanceof Fail);
  }

  public Set<Variable> vars()
  {
    Set<Variable> vars = this.lhs.vars();
    vars.addAll(this.rhs.vars());
    return vars;
  }
  public Set<Constant> consts()
  {
    Set<Constant> consts = this.lhs.consts();
    consts.addAll(this.rhs.consts());
    return consts;
  }

  @Override
  public Unification unify(Constraint with)
  {
    if (! (with instanceof Equals)) return new Unification(false);
    final Equals that = (Equals) with;
    final Unification unification = this.lhs.unify(that.lhs);
    if (unification.hasFailed()) return unification;
    final CE thetaX = this.rhs.substitute(unification.theta);
    final CE thetaY = that.rhs.substitute(unification.theta);
    unification.compose(thetaX.unify(thetaY));
    return unification;
  }

  @Override
  public Constraint substitute(Map<Variable, Substitution> delta)
  { return new Equals(this.lhs.substitute(delta), this.rhs.substitute(delta)); }

  @Override
  public boolean isTrue()
  {
    if (this.hasFailed()) return false;
    return lhs.eval().equals(rhs.eval());
  }

  @Override
  public void scope(int scope)
  {
    this.lhs.scope(scope);
    this.rhs.scope(scope);
  }
}
