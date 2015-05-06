package apppal.logic.interfaces;

import java.util.Set;

import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Interface for classes holding Es so we can pull them out at will.
 */
public interface EntityHolding
{
  Set<Variable> vars();
  Set<Constant> consts();
}
