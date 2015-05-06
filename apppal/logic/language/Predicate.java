package apppal.logic.language;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.EntityHolding;

/**
 * AppPAL predicate
 * predicate (E+)
 */
public class Predicate extends VP implements EntityHolding
{
  public final String name;
  public final List<E> args;

  public Predicate(String name)
  {
    this(name, null);
  }

  public Predicate(String name, List<E> args)
  {
    this.name = name;
    if (args == null)
      this.args = new LinkedList<E>();
    else
      this.args = args;
  }

  /** Check whether this predicate has arguments
   * @return boolean
   */
  private boolean hasArgs()
  {
    return !(this.args == null || this.args.isEmpty());
  }

  public String toString()
  {
    // If the predicate list is empty don't display it
    if (! this.hasArgs())
      return this.name;

    // Else this.args.size() >= 1
    String answer = this.name + "(";
    for (int i = 0; i < this.args.size() - 1; i++)
      answer += this.args.get(i) + ", ";
    answer += this.args.get(this.args.size() - 1) + ")";

    return answer;
  }

  public Set<Variable> vars()
  {
    final Set<Variable> vars = new HashSet<>();
    if (this.hasArgs())
      for(E arg : this.args)
        vars.addAll(arg.vars());
    return vars;
  }

  @Override
  public void scope(int scope)
  { for (E e : this.args) e.scope(scope); }

  public Set<Constant> consts()
  {
    final Set<Constant> consts = new HashSet<>();
    if (this.hasArgs())
      for(E arg : this.args)
        consts.addAll(arg.consts());
    return consts;
  }

  public Unification unify(VP vp)
  {
    final Unification unification = new Unification();
    if (!(vp instanceof Predicate))
      return new Unification(false);

    final Predicate other = (Predicate) vp;

    if (!this.name.equals(other.name))
      return new Unification(false);

    final int n = this.args.size();
    if (n != other.args.size())
      return new Unification(false);

    for (int k = 0; k < n; k++)
    {
      final E thetaX = this.args.get(k).substitute(unification.theta);
      final E thetaY = other.args.get(k).substitute(unification.theta);
      Unification tau = thetaX.unify(thetaY);
      unification.compose(tau);
      if (unification.hasFailed())
        return unification;
    }


    return unification;
  }

  public Predicate substitute(Map<Variable, Substitution> delta)
  {
    final LinkedList<E> args = new LinkedList<>();
    for (E e : this.args)
      args.add(e.substitute(delta));
    return new Predicate(this.name, args);
  }
}
