package apppal.logic.interfaces;

import java.util.Map;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.language.Variable;

/**
 * Describes when there is something unifiable.
 */
public interface Unifiable<T>
{
  abstract public Unification unify(final T with);
  abstract public T substitute(final Map<Variable, Substitution> delta);
}
