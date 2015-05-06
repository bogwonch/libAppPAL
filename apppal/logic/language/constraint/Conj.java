package apppal.logic.language.constraint;

import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Conjunction of two constraints
 */
public class Conj extends Constraint implements Unifiable<Constraint>
{
  public final Constraint lhs;
  public final Constraint rhs;

  public Conj(Constraint lhs, Constraint rhs)
  {
    super();
    this.lhs = lhs;
    this.rhs = rhs;
  }

  public String toString() { return this.lhs +", "+this.rhs; }

  @Override
  public boolean hasFailed()
  {
    return this.lhs.hasFailed() || this.rhs.hasFailed();
  }

  public Set<Variable> vars()
  {
    final Set<Variable> vars = this.lhs.vars();
    vars.addAll(this.rhs.vars());
    return vars;
  }
  public Set<Constant> consts()
  {
    final Set<Constant> consts = this.lhs.consts();
    consts.addAll(this.rhs.consts());
    return consts;
  }

  @Override
  public Unification unify(Constraint with)
  {
    if (! (with instanceof Conj)) return new Unification(false);
    final Conj that = (Conj) with;
    final Unification unification = this.lhs.unify(that.lhs);
    if (unification.hasFailed()) return unification;
    final Constraint thetaX = this.rhs.substitute(unification.theta);
    final Constraint thetaY = that.rhs.substitute(unification.theta);
    unification.compose(thetaX.unify(thetaY));
    return unification;
  }

  @Override
  public Conj substitute(Map<Variable, Substitution> delta)
  {
    return new Conj(this.lhs.substitute(delta), this.rhs.substitute(delta));
  }

  @Override
  public boolean isTrue()
  {
    if (this.hasFailed()) return false;
    return this.lhs.isTrue() && this.rhs.isTrue();
  }

  @Override
  public void scope(int scope)
  {
    this.lhs.scope(scope);
    this.rhs.scope(scope);
  }

}
