package apppal.logic.language.constraint.functions;

import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Fail;
import java.util.List;

/** Constraint function that always fails. */
public class Failure implements ConstraintFunction {
  @Override
  public int arity() {
    return 0;
  }

  @Override
  public CE eval(List<CE> args) {
    return new Fail();
  }
}
