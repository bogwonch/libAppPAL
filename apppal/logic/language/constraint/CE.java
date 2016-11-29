package apppal.logic.language.constraint;

import apppal.logic.interfaces.EntityHolding;
import apppal.logic.interfaces.Unifiable;
import apppal.logic.language.Variable;
import java.util.Set;

/** Constraint Entity. */
public abstract class CE implements EntityHolding, Unifiable<CE> {
  public abstract String toString();

  public abstract Set<Variable> vars();

  public void scope(int scope) {}

  public abstract CE eval();
}
