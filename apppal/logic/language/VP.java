package apppal.logic.language;

import apppal.logic.interfaces.EntityHolding;
import apppal.logic.interfaces.Unifiable;
import java.util.Set;

/** SecPAL language verb phrases */
public abstract class VP implements EntityHolding, Unifiable<VP> {
  public abstract String toString();

  public abstract Set<Variable> vars();

  public abstract void scope(int scope);
}
