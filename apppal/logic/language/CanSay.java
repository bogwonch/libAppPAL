package apppal.logic.language;


import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.interfaces.EntityHolding;

/**
 * AppPAL can-say statement
 * can-say D Fact
 */
public class CanSay extends VP implements EntityHolding
{
  public final D d;
  public final Fact fact;

  public CanSay(D d, Fact fact)
  {
    this.d = d;
    this.fact = fact;
  }

  public String toString()
  {
    return "can-say " + this.d + " " + this.fact;
  }

  public Set<Variable> vars() { return this.fact.vars(); }
  public Set<Constant> consts() { return this.fact.consts(); }

  public Unification unify(VP vp)
  {
    Unification unification = new Unification();
    if (! (vp instanceof CanSay)) { unification.fails(); }
    else
    {
      CanSay other = (CanSay) vp;

      if (this.d != other.d) unification.fails();
      else
        unification.compose(this.fact.unify(other.fact));
    }
    return unification;
  }

  public CanSay substitute(Map<Variable, Substitution> delta)
  { return new CanSay(this.d, this.fact.substitute(delta)); }

  public void scope(int scope)
  { this.fact.scope(scope); }
}

