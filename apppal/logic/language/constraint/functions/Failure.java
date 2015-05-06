package apppal.logic.language.constraint.functions;

import java.util.List;

import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Fail;

/**
 * Constraint function that always fails.
 */
public class Failure implements ConstraintFunction
{
  @Override
  public int arity()
  { return 0; }

  @Override
  public CE eval(List<CE> args)
  { return new Fail(); }

}
