package apppal.logic.language.constraint;

import apppal.logic.interfaces.EntityHolding;
import apppal.logic.interfaces.Unifiable;

/**
 * SecPAL Constraint
 */
abstract public class Constraint implements EntityHolding, Unifiable<Constraint>
{
  public abstract boolean isTrue();
  public boolean isTriviallyTrue()
  {
    return false;
  }
  public void scope(int scope){}
  public abstract String toString();
  public abstract boolean hasFailed();
}
