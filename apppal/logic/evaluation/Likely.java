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
        final E subject = a.says.consequent.subject;
        final E renaming = ((CanActAs) a.says.consequent.object).renaming;
        if (renaming.kind == EKind.VARIABLE)
          canActAs_variables.add(subject);
        else
          add_mapable(canActAs_mapables, subject, renaming);
      }
      else if (a.isCanSay())
      {
        final E delegated = a.says.consequent.subject;
        final E speaker = a.speaker;
        if (delegated.kind == EKind.VARIABLE)
          canSay_variables.add(speaker);
        else
          add_mapable(canSay_mapables, speaker, delegated);
      }
    }

    // Fixup can-say and can-act-as together mapables
    for (E e : canActAs_variables)
      for (E k : canSay_mapables.keySet())
      {
        Set<E> ms = canSay_mapables.get(k);
        if (ms.contains(e))
          canSay_variables.add(k);
      }
    for (E caK : canActAs_mapables.keySet())
    {
      Set<E> caMs = canActAs_mapables.get(caK);
      for(E caM : caMs)
      {
        for(E csK : canSay_mapables.keySet())
        {
          Set<E> csMs = canSay_mapables.get(csK);
          if (csMs.contains(caM))
            add_mapable(canSay_mapables, csK, caK);
        }
      }
    }

    /* // Copious amounts of debugging info */
    /* System.err.println("[!] Likely"); */
    /* System.err.println("    CA Vars:"); */
    /* for (E e : canActAs_variables) System.err.println("      "+e); */
    /* System.err.println("    CA Mapped:"); */
    /* for (E e : canActAs_mapables.keySet()) */
    /*   for (E ee : canActAs_mapables.get(e)) */
    /*     System.err.println("      "+e+" -> "+ee); */
    /* for (E e : canSay_mapables.keySet()) */
    /*   for (E ee : canSay_mapables.get(e)) */
    /*     System.err.println("[!] "+e+" |--> "+ee); */

  }

  public void add_mapable(Map<E, Set<E>> map, E key, E value)
  {
    if (map.containsKey(key))
    {
      final Set<E> set = map.get(key);
      set.add(value);
      map.put(key, set);
    }
    else
    {
      final Set<E> set = new HashSet<>();
      set.add(value);
      map.put(key, set);
    }
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
