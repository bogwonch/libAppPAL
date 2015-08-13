package apppal.logic.evaluation;

import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.E;
import apppal.logic.language.EKind;
import apppal.logic.language.CanActAs;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Likely
{
  private final Set<E> canActAs_variables;
  private final Set<E> canSay_variables;
  private final Map<E, Set<E>> canActAs_mapables;
  private final Map<E, Set<E>> canSay_mapables;

  public Likely(AC ac)
  {
    this.canActAs_variables = new HashSet<>();
    this.canSay_variables = new HashSet<>();
    this.canActAs_mapables = new HashMap<>();
    this.canSay_mapables = new HashMap<>();

    for (Assertion a : ac.assertions)
    {
      if (a.isCanActAs())
      {
        E subject = a.says.consequent.subject;
        E renaming = ((CanActAs) a.says.consequent.object).renaming;
        if (renaming.kind == EKind.VARIABLE)
          canActAs_variables.add(subject);
        else
        {
          if (canActAs_mapables.containsKey(subject))
          {
            final Set<E> set = canActAs_mapables.get(subject);
            set.add(renaming);
            canActAs_mapables.put(subject, set);
          }
          else
          {
            final Set<E> set = new HashSet<>();
            set.add(renaming);
            canActAs_mapables.put(subject, set);
          }
        }
      }
      else if (a.isCanSay())
      {
        E delegated = a.says.consequent.subject;
        E speaker = a.speaker;
        if (delegated.kind == EKind.VARIABLE)
          canSay_variables.add(speaker);
        else
        {
          if (canSay_mapables.containsKey(speaker))
          {
            final Set<E> set = canSay_mapables.get(speaker);
            set.add(delegated);
            canSay_mapables.put(speaker, set);
          }
          else
          {
            final Set<E> set = new HashSet<>();
            set.add(delegated);
            canSay_mapables.put(speaker, set);
          }
        }
      }
    }

    /* System.err.println("[!] Likely"); */
    /* System.err.println("    CA Vars:"); */
    /* for (E e : canActAs_variables) System.err.println("      "+e); */
    /* System.err.println("    CA Mapped:"); */
    /* for (E e : canActAs_mapables.keySet()) */
      /* for (E ee : canActAs_mapables.get(e)) */
        /* System.err.println("      "+e+" -> "+ee); */

    /* for (E e : canSay_mapables.keySet()) */
    /*   for (E ee : canSay_mapables.get(e)) */
    /*     System.err.println("[!] "+e+" |--> "+ee); */
  }

  public boolean canSay(final E speaker, final E delegated)
  {
    if (canSay_variables.contains(speaker))
    { return true; }
    else if (canSay_mapables.containsKey(speaker) && canSay_mapables.get(speaker).contains(delegated))
    { return true; }
    else return false;
  }

  public boolean canActAs(final E subject, final E renaming)
  {
    /* System.err.print("[L] "+subject+" -> "+renaming); */
    if (canActAs_variables.contains(subject))
    {
      /* System.err.println(":\tyes by variable"); */
      return true;
    }
    else if (canActAs_mapables.containsKey(subject) && canActAs_mapables.get(subject).contains(renaming))
    {
      /* System.err.println(":\tyes by mapping"); */
      return true;
    }
    /* System.err.println(":\tno"); */
    return false;
  }
}
