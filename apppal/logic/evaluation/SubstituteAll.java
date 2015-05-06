package apppal.logic.evaluation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import apppal.logic.language.Constant;
import apppal.logic.language.Variable;

/**
 * Rudely inefficient c
 */
public class SubstituteAll implements Iterable<Map<Variable, Substitution>>
{
  private final Set<Variable> vars;
  private final Set<Constant> consts;

  private final Set<Map<Variable, Substitution>> substitutions;

  public SubstituteAll(Set<Variable> vars, Set<Constant> consts)
  {
    this.vars = vars;
    this.consts = consts;
    this.substitutions = this.findAll();
  }

  @Override
  public Iterator<Map<Variable, Substitution>> iterator()
  { return this.substitutions.iterator(); }

  /**
   * Finds all the possible substitution for a stack of vars
   * @param vars a stack of variables to assign values to
   * @return the set of maps of variables to substitutions
   *
   * Gods of Complexity pity me!
   * This worked in Haskell because lazy evaluation...
   * I suspect this is going to hurt now.
   */
  private Set<Map<Variable, Substitution>>findAll(final Stack<Variable> vars)
  {
    // If there are no variables to substitute return the empty set
    if (vars.size() == 0) return new HashSet<>();
    else
    {
      final Variable v = vars.pop();
      final Set<Map<Variable, Substitution>> subs = findAll(vars);

      // If there are no substitutions yet add all substitutions for a single variable
      if (subs.size() == 0)
      {
        for (Constant c : this.consts)
        {
          final Map<Variable, Substitution> sub = new HashMap<>();
          sub.put(v, new Substitution(v, c));
          subs.add(sub);
        }
        return subs;
      }
      // Otherwise for all existing substitutions add the new assignment to each of them
      else
      {
        final Set<Map<Variable, Substitution>> answer = new HashSet<>();
        for (final Constant c : this.consts)
          for (final Map<Variable, Substitution> subset : subs)
          {
            final Map<Variable, Substitution> add = new HashMap<>();
            add.put(v, new Substitution(v, c));
            add.putAll(subset);
            answer.add(add);
          }
        return answer;
      }
    }
  }

  private Set<Map<Variable, Substitution>> findAll(final Set<Variable> vars)
  {
    final Stack<Variable> stack = new Stack<>();
    for (Variable v : vars)
      stack.push(v);
    return this.findAll(stack);
  }

  private Set<Map<Variable, Substitution>> findAll()
  { return findAll(this.vars);  }
}
