package apppal.logic.language.constraint;

import java.util.Set;

import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Variable;
import apppal.logic.interfaces.EntityHolding;

/**
 * Constraint Entity.
 */
abstract public class CE implements EntityHolding, Unifiable<CE>
{
  public abstract String toString();
  public abstract Set<Variable> vars();
  public void scope(int scope) {}
  public abstract CE eval();
}
