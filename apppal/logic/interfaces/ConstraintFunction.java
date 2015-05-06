package apppal.logic.interfaces;

import java.util.List;

import apppal.logic.language.constraint.CE;

/**
 * Created by bogwonch on 05/03/2015.
 */
public interface ConstraintFunction
{
  public abstract int arity();
  public abstract CE eval(List<CE> args);
}
