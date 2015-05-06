package apppal.logic.language;


import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.EntityHolding;

/**
 * SecPAL can-act-as statement
 * can-act-as E
 */
public class CanActAs extends VP implements EntityHolding
{
  public final E renaming;

  public CanActAs(E renaming)
  {
    this.renaming = renaming;
  }

  public String toString()
  {
    return "can-act-as " + this.renaming;
  }

  public Set<Variable> vars()
  {
    return renaming.vars();
  }
  public Set<Constant> consts() { return renaming.consts(); }

  public CanActAs substitute(Map<Variable, Substitution> delta)
  {
    if (this.renaming instanceof Variable && delta.containsKey(this.renaming))
    {
      return new CanActAs(this.renaming.substitute(delta));
    }
    return this;
  }

  public Unification unify(VP vp)
  {
    Unification unification = new Unification();
    if (! (vp instanceof CanActAs)) unification.fails();
    else
    {
      CanActAs other = (CanActAs) vp;
      unification.compose(this.renaming.unify(other.renaming));
    }
    return unification;
  }

  public void scope(int scope)
  {
    this.renaming.scope(scope);
  }
}
