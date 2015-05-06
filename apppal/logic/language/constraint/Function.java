package apppal.logic.language.constraint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Constant;
import apppal.logic.language.Variable;
import apppal.logic.language.constraint.functions.*;
import apppal.logic.language.constraint.functions.Failure;

/**
 * Constraint Function
 */
public class Function extends CE implements Unifiable<CE>
{
  public final String name;
  public final List<CE> args;

  public Function(String name, List<CE>args)
  {
    this.name = name;
    if (args == null)
      this.args = new LinkedList<>();
    else
      this.args = args;
  }

  public String toString()
  {
    String str = this.name+'(';
    if (! this.args.isEmpty())
    {
      str = str + this.args.get(0);
      for (int i = 1; i < this.args.size(); i++)
        str += ", "+this.args.get(i);
    }
    return str+")";
  }

  public Set<Variable> vars()
  {
    final Set<Variable> vars = new HashSet<>();
    for (final CE e : this.args)
      vars.addAll(e.vars());
    return vars;
  }
  public Set<Constant> consts()
  {
    final Set<Constant> consts = new HashSet<>();
    for (final CE e : this.args)
      consts.addAll(e.consts());
    return consts;
  }

  @Override
  public Unification unify(final CE with)
  {
    if (!(with instanceof Function)) return new Unification(false);
    Function that = (Function) with;
    if (! this.name.equals(that.name)) return new Unification(false);
    int n = this.args.size();
    if (that.args.size() != n) return new Unification(false);
    final Unification unification = new Unification();
    for (int k = 0; k < n; k++)
    {
      CE thetaX = this.args.get(k).substitute(unification.theta);
      CE thetaY = that.args.get(k).substitute(unification.theta);
      unification.compose(thetaX.unify(thetaY));
      if (unification.hasFailed()) return unification;
    }
    return unification;
  }

  @Override
  public Function substitute(Map<Variable, Substitution> delta)
  {
    final List<CE> args = new LinkedList<>();
    for (final CE arg : this.args)
      args.add(arg.substitute(delta));
    return new Function(this.name, args);
  }

  public void scope(int scope)
  { for (CE e : this.args) e.scope(scope); }

  /**
   * Evaluates a function by calling the correct evaluation function
   * @return
   */
  @Override
  public CE eval() throws IllegalArgumentException
  {
    final ConstraintFunction f;
    switch (this.name)
    {
      case "hasPermission":
        f = new HasPermission();
        break;

      case "failure":
        f = new Failure();
        break;

      default:
        throw new IllegalArgumentException("cannot evaluate unknown function: "+this.name);
    }

    if (this.args.size() != f.arity())
      throw new IllegalArgumentException(
        "function "+this.name+
        " should have arity "+f.arity()+
        " but found "+this.args.size()+" arguments.");

    return f.eval(this.args);
  }
}
