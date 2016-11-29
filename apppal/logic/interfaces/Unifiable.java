package apppal.logic.interfaces;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.language.Variable;
import java.util.Map;

/** Describes when there is something unifiable. */
public interface Unifiable<T> {
  public abstract Unification unify(final T with);

  public abstract T substitute(final Map<Variable, Substitution> delta);
}
