package apppal.logic.evaluation;

import java.util.EnumSet;
import java.util.Map;

import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Constant;
import apppal.logic.language.D;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.VP;
import apppal.logic.language.Variable;

/**
 * Class for doing the actual evaluation
 */
public class Evaluation
{
  public final AC ac;
  private Assertion q;
  private ResultsTable rt;

  public Evaluation(AC ac)
  {
    this.ac = ac;
    this.rt = new ResultsTable();
  }

  public static Result run(AC ac, Assertion query)
  {
    return new Evaluation(ac).run(query);
  }

  public Result run(final Assertion query)
  { return this.evaluate(query, D.INF); }

  public static boolean shows(AC ac, Assertion query)
  {
    return new Evaluation(ac).shows(query);
  }

  public boolean shows(final Assertion query)
  { return this.run(query).isProven(); }

  private Result evaluate(Assertion q, D d)
  {
    if (this.rt.has(q,d))
    {
      return this.rt.get(q,d);
    }

    this.rt.markUnfinished(q, d);

    // TODO: refactor this bollox
    final Proof condProof = this.cond(q, d);
    if (condProof.isKnown())
    {
      final Result result = new Result(q, d, condProof);
      this.rt.update(result);
      return result;
    }

    final Proof canSayOrCanActAsProof = this.canSayOrCanActAs(q, d);
    if (canSayOrCanActAsProof.isKnown())
    {
      final Result result = new Result(q, d, canSayOrCanActAsProof);
      this.rt.update(result);
      return result;
    }


    Result result = new Result(q, d, new Proof(false));
    this.rt.update(result);
    return result;
  }

  private Proof cond(Assertion q, D d)
  {
    // Check implicit facts
    // isAnApp
    final E subject = q.says.consequent.subject;
    final VP vp = q.says.consequent.object;
    if (vp instanceof Predicate)
    {
      if (((Predicate) vp).name.equals("isAnApp"))
      {
        if (subject instanceof Constant)
         return new Proof(subject.name.startsWith("apk://"));
      }
    }

    for (final Assertion a : this.ac.assertions)
    {
      /* System.err.println("[?] Cond: "+a+ "{"); */
      final Unification headU = q.unify(a.consequence());
      if (headU.hasFailed()) continue;

      final Assertion thetaA = a.substitute(headU.theta);

      // Condition three: no vars of the consequent fact must be gone after unification
      if (thetaA.says.consequent.vars().size() != 0)
        continue;

      // Condition one: all antecedents must be satisfied
      // Condition three: constraint must be sat
      if (! this.checkAntecedents(thetaA, d).isKnown())
        continue;

      /* System.err.println("} YES"); */
      return new Proof(true);
    }

    /* System.err.println("} NO\n"); */
    return new Proof(false);
  }

  private Proof checkAntecedents(Assertion a, D d)
  {
    if (a.says.antecedents.size() == 0 &&
        a.says.constraint.isTriviallyTrue())
      return new Proof(true);
    if (a.vars().size() == 0) return this.checkAntecedentsNoVars(a, d);
    else return this.checkAntecedentWithVars(a, d);
  }

  private Proof checkAntecedentsNoVars(Assertion a, D d)
  {
    if (a.vars().size() != 0) throw new RuntimeException("vars in antecedent when none claimed");
    for (final Fact f : a.says.antecedents)
    {
      final Result r = evaluate(f.toAssertion(), d);
      if (! r.isProven()) return new Proof(false);
    }

    // Check the constraint
    return new Proof(a.says.constraint.isTrue());
  }

  private Proof checkAntecedentWithVars(Assertion a, D d)
  {
    SubstituteAll subs = new SubstituteAll(a.vars(), this.ac.constants);
    for (Map<Variable, Substitution> theta : subs)
    {
      Assertion thetaA = a.substitute(theta);
      if (this.checkAntecedentsNoVars(thetaA, d).isKnown())
        return new Proof(true);
    }
    return new Proof(false);
  }

  /*
  private Proof canSay(Assertion q, D d)
  {
    // Disallow this rule when delegation banned
    if (d == D.ZERO) return new Proof(false);

    // Disallow nested can-say statements
    if (q.says.consequent.object instanceof CanSay) return new Proof(false);

    for (final Constant c : this.ac.voiced)
    {
      for(final D depth : EnumSet.of(D.ZERO, D.INF))
      {
        final Assertion delegator =
          Assertion.makeCanSay(q.speaker, c, depth, q.says.consequent);
        final Result rDelegator = evaluate(delegator, d);
        if (rDelegator.isProven())
        {
          final Assertion delegation = Assertion.make(c, q.says.consequent);
          final Result rDelegation = evaluate(delegation, depth);
          if (rDelegation.isProven())
            return new Proof(true);
        }
      }
    }

    return new Proof(false);
  }
  */

  /*
  private Proof canActAs(Assertion q, D d)
  {
    for (final Constant c : this.ac.constants)
    {
      final Assertion renaming =
        Assertion.makeCanActAs(q.speaker, q.says.consequent.subject, c);
      final Result rRenaming = evaluate(renaming, d);
      if (! rRenaming.isProven()) continue;

      final Assertion renamed =
        Assertion.make(q.speaker, c, q.says.consequent.object);
      final Result rRenamed = evaluate(renamed, d);
      if (! rRenamed.isProven()) continue;

      return new Proof(true);
    }

    return new Proof(false);
  }
  */

  private Proof canSayOrCanActAs(Assertion q, D d)
  {
    for (final Constant c : this.ac.subjects)
    {
      // Can Act As
      /* if (this.ac.interesting.contains(c)) // True by definition of interesting surely? TODO Figure out wtf? */
      /* { */
        if (! q.says.consequent.subject.equals(c)) // Don't care about A can-act-as A: Tautological
        {
          /* System.err.println("[?] CanActAs: "+q+" {"); */
          final Assertion renaming =
            Assertion.makeCanActAs(q.speaker, q.says.consequent.subject, c);
          final Result rRenaming = evaluate(renaming, d);
          if (rRenaming.isProven())
          {
            final Assertion renamed =
              Assertion.make(q.speaker, c, q.says.consequent.object);
            final Result rRenamed = evaluate(renamed, d);
            if (!rRenamed.isProven()) { 
              /* System.err.println("} NO"); */
              continue;
            }

            /* System.err.println("} YES"); */
            return new Proof(true);
          }
          /* System.err.println("} NO"); */
        }
      /* } */

      // Can-say
      if (this.ac.voiced.contains(c))
      {
        if (! q.speaker.equals(c)) // Don't care about A says A can-say...: Tautological
        {
          /* System.err.println("[?] CanSay: "+q); */
          // Disallow this rule when delegation banned
          if (d == D.ZERO) return new Proof(false);

          //// Disallow can-say rule on nested can-say statements
          if (q.says.consequent.object instanceof CanSay) return new Proof(false);

          for (final D depth : EnumSet.of(D.ZERO, D.INF))
          {
            final Assertion delegator =
              Assertion.makeCanSay(q.speaker, c, depth, q.says.consequent);
            final Result rDelegator = evaluate(delegator, d);
            if (rDelegator.isProven())
            {
              final Assertion delegation = Assertion.make(c, q.says.consequent);
              final Result rDelegation = evaluate(delegation, depth);
              if (rDelegation.isProven())
              {
                /* System.err.println("} YES"); */
                return new Proof(true);
              }
            }
          }
        }
        /* System.err.println("} NO"); */
      }
    }

    return new Proof(false);
  }
}
