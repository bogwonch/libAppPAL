package apppal.logic.language;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.constraint.Constraint;
import apppal.logic.language.constraint.Sat;
import apppal.logic.interfaces.EntityHolding;

/**
 * AppPAL claims
 * fact [ if fact,* ] [ where c ]
 */
public class Claim implements EntityHolding, Unifiable
{
  public final Fact consequent;
  public final List<Fact> antecedents;
  public final Constraint constraint;

  /**
   * Creates a new claim
   */
  public Claim(Fact f, List<Fact> a, Constraint c)
  {
    this.consequent = f;

    if (a == null)
      this.antecedents = new LinkedList<>();
    else
      this.antecedents = a;

    if (c == null)
      this.constraint = new Sat();
    else
      this.constraint = c;
  }

  /* Utility constructors */
  public Claim(Fact c)
  {
    this(c, null, new Sat());
  }

  public Claim(Fact f, List<Fact> a)
  {
    this(f, a, new Sat());
  }

  public Claim(Fact consequent, Constraint c)
  {
    this(consequent, null, c);
  }

  public String toString()
  {
    String answer = this.consequent.toString();

    if (this.hasAntecedents())
    {
      answer += " if ";
      for (int i = 0; i < this.antecedents.size() - 1; i++)
        answer += this.antecedents.get(i) + ", ";
      answer += this.antecedents.get(this.antecedents.size() - 1);
    }

    if (!this.constraint.isTriviallyTrue())
      answer += " where " + this.constraint;

    return answer;
  }

  public boolean hasAntecedents()
  {
    return ! (this.antecedents == null || this.antecedents.isEmpty());
  }

  public Set<Variable> vars()
  {
    final Set<Variable> vars = this.consequent.vars();
    vars.addAll(this.antecedentVars());
    vars.addAll(this.constraint.vars());
    return vars;
  }

  public Set<Constant> consts()
  {
    final Set<Constant> consts = this.consequent.consts();
    consts.addAll(this.antecedentConsts());
    consts.addAll(this.constraint.consts());
    return consts;
  }

  /**
   * Get the vars from the antecedent conditions
   * @return Set of variables in the antecedent conditions
   */
  public Set<Variable> antecedentVars()
  {
    final Set<Variable> vars = new HashSet<>();
    if (this.hasAntecedents())
      for (Fact f : this.antecedents)
        vars.addAll(f.vars());
    return vars;
  }

  /**
   * Get the constants from the antecedent conditions
   * @return Set of constants in the antecedent conditions
   */
  public Set<Constant> antecedentConsts()
  {
    final Set<Constant> consts = new HashSet<>();
    if (this.hasAntecedents())
      for (Fact f : this.antecedents)
        consts.addAll(f.consts());
    return consts;
  }

  public Unification unify(final Object other)
  {
    if (!(other instanceof Claim)) return new Unification(false);

    final Claim that = (Claim) other;

    final int n = this.antecedents.size();
    if (n != that.antecedents.size()) return new Unification(false);

    final Unification unification = this.consequent.unify(that.consequent);
    for (int k = 0; k < n; k++)
    {
      final Fact thetaX = this.antecedents.get(k).substitute(unification.theta);
      final Fact thetaY = that.antecedents.get(k).substitute(unification.theta);
      final Unification tau = thetaX.unify(thetaY);
      unification.compose(tau);
      if (unification.hasFailed()) return unification;
    }

    final Constraint thetaX = this.constraint.substitute(unification.theta);
    final Constraint thetaY = this.constraint.substitute(unification.theta);
    final Unification tau = thetaX.unify(thetaY);
    unification.compose(tau);

    return unification;
  }

  public Claim substitute(Map delta)
  {
    final Fact consequent = this.consequent.substitute(delta);
    final LinkedList<Fact> antecedent = new LinkedList<>();
    for (final Fact a : this.antecedents)
      antecedent.add(a.substitute(delta));
    final Constraint constraint = this.constraint.substitute(delta);
    return new Claim(consequent, antecedent, constraint);
  }

  public void scope(int scope)
  {
    this.consequent.scope(scope);
    for (Fact f : this.antecedents) f.scope(scope);
    this.constraint.scope(scope);
  }
}
