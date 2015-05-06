package apppal.logic.evaluation;

import apppal.logic.language.E;
import apppal.logic.language.Variable;

/**
 * A substitution
 */
public class Substitution
{
  public final Variable from;
  public E to;

  public Substitution(Variable from, E to)
  {
    this.from = from;
    this.to = to;
  }

  public String toString() { return this.from+"=>"+this.to; }

  public void substitute(Variable from, E to)
  { if (this.to.equals(from)) this.to = to; }

  public void substitute(Substitution s)
  { this.substitute(s.from, s.to); }
}
