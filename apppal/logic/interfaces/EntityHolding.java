package apppal.logic.interfaces;

import apppal.logic.language.Constant;
import apppal.logic.language.Variable;
import java.util.Set;

/** Interface for classes holding Es so we can pull them out at will. */
public interface EntityHolding {
  Set<Variable> vars();

  Set<Constant> consts();
}
