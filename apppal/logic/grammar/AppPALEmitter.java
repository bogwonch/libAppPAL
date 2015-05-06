package apppal.logic.grammar;

import java.util.LinkedList;

import apppal.logic.language.Assertion;
import apppal.logic.language.CanActAs;
import apppal.logic.language.CanSay;
import apppal.logic.language.Claim;
import apppal.logic.language.Constant;
import apppal.logic.language.D;
import apppal.logic.language.E;
import apppal.logic.language.Fact;
import apppal.logic.language.Predicate;
import apppal.logic.language.VP;
import apppal.logic.language.Variable;
import apppal.logic.language.constraint.Bool;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Conj;
import apppal.logic.language.constraint.Constraint;
import apppal.logic.language.constraint.Equals;
import apppal.logic.language.constraint.Function;
import apppal.logic.language.constraint.Negation;
import apppal.logic.language.constraint.Sat;

import static apppal.logic.language.D.INF;
import static apppal.logic.language.D.ZERO;

public class AppPALEmitter extends AppPALBaseVisitor<Object>
{
  /**
   * Visit a parse tree produced by the {@code variable}
   * labeled alternative in {@link apppal.logic.grammar.AppPALParser#e}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitVariable(AppPALParser.VariableContext ctx)
  {
    return new Variable(ctx.VARIABLE().getText());
  }

  /**
   * Visit a parse tree produced by the {@code constant}
   * labeled alternative in {@link AppPALParser#e}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitConstant(AppPALParser.ConstantContext ctx)
  {
    String s = ctx.CONSTANT().getText();
    if (s.startsWith("\""))
      s = s.substring(1, s.length());
    if (s.endsWith("\""))
      s = s.substring(0, s.length() - 1);
    return new Constant(s);
  }

  /**
   * Visit a parse tree produced by the {@code zero}
   * labeled alternative in {@link AppPALParser#d}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitZero(AppPALParser.ZeroContext ctx)
  {
    return ZERO;
  }

  /**
   * Visit a parse tree produced by the {@code inf}
   * labeled alternative in {@link AppPALParser#d}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitInf(AppPALParser.InfContext ctx)
  {
    return INF;
  }

  /**
   * Visit a parse tree produced by the {@code predicate}
   * labeled alternative in {@link AppPALParser#vp}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitPredicate(AppPALParser.PredicateContext ctx)
  {
    String predicate = ctx.PREDICATE_NAME().getText();
    LinkedList<E> args = new LinkedList<>();
    for (AppPALParser.EContext ectx : ctx.e())
    {
      E e = (apppal.logic.language.E) this.visit(ectx);
      args.add(e);
    }
    return new Predicate(predicate, args);
  }

  /**
   * Visit a parse tree produced by the {@code canSay}
   * labeled alternative in {@link AppPALParser#vp}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitCanSay(AppPALParser.CanSayContext ctx)
  {
    D d = (D) this.visit(ctx.d());
    Fact f = (Fact) this.visit(ctx.fact());
    return new CanSay(d, f);
  }

  /**
   * Visit a parse tree produced by the {@code canActAs}
   * labeled alternative in {@link AppPALParser#vp}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitCanActAs(AppPALParser.CanActAsContext ctx)
  {
    E e = (E) this.visit(ctx.e());
    return new CanActAs(e);
  }

  /**
   * Visit a parse tree produced by {@link AppPALParser#fact}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitFact(AppPALParser.FactContext ctx)
  {
    E e = (E) this.visit(ctx.e());
    VP vp = (VP) this.visit(ctx.vp());
    return new Fact(e, vp);
  }

  /**
   * Visit a parse tree produced by {@link AppPALParser#claim}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitClaim(AppPALParser.ClaimContext ctx)
  {
    Fact subsequent = (Fact) this.visit(ctx.fact(0));
    LinkedList<Fact> antecedent = new LinkedList<>();
    for (int i = 1; i < ctx.fact().size(); i++)
      antecedent.add((Fact) this.visit(ctx.fact(i)));

    Constraint constraint = (ctx.c() == null)? null : (Constraint) this.visit(ctx.c());
    return new Claim(subsequent, antecedent, constraint);
  }

  /**
   * Visit a parse tree produced by {@link AppPALParser#assertion}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitAssertion(AppPALParser.AssertionContext ctx)
  {
    E e = (E) this.visit(ctx.e());
    if (! (e instanceof Constant))
      throw new RuntimeException("assertion made by variable speaker: "+e);
    Claim c = (Claim) this.visit(ctx.claim());
    return new Assertion((Constant) e, c);
  }

  /**
   * Visit a parse tree produced by {@link AppPALParser#ac}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitAc(AppPALParser.AcContext ctx)
  {
    LinkedList<Assertion> ac = new LinkedList<>();
    for (AppPALParser.AssertionContext actx : ctx.assertion())
    {
      ac.add((Assertion) this.visit(actx));
    }
    return ac;
  }

  /**
   * Visit a parse tree produced by the {@code entity}
   * labeled alternative in {@link AppPALParser#ce}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitEntity(AppPALParser.EntityContext ctx)
  {
    return this.visit(ctx.e());
  }

  /**
   * Visit a parse tree produced by the {@code function}
   * labeled alternative in {@link AppPALParser#ce}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitFunction(AppPALParser.FunctionContext ctx)
  {
    LinkedList<CE> args = new LinkedList<>();
    for (AppPALParser.CeContext e : ctx.ce())
      args.add((CE) this.visit(e));
    return new Function(ctx.PREDICATE_NAME().getText(), args);
  }

  /**
   * Visit a parse tree produced by the {@code true}
   * labeled alternative in {@link AppPALParser#ce}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitTrue(AppPALParser.TrueContext ctx)
  { return new Bool(true); }

  /**
   * Visit a parse tree produced by the {@code false}
   * labeled alternative in {@link AppPALParser#ce}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitFalse(AppPALParser.FalseContext ctx)
  { return new Bool(false); }

  /**
   * Visit a parse tree produced by the {@code negation}
   * labeled alternative in {@link AppPALParser#c}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitNegation(AppPALParser.NegationContext ctx)
  {
    return new Negation((Constraint) this.visit(ctx.c()));
  }

  /**
   * Visit a parse tree produced by the {@code satisfaction}
   * labeled alternative in {@link AppPALParser#c}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitSatisfaction(AppPALParser.SatisfactionContext ctx)
  { return new Sat(); }

  /**
   * Visit a parse tree produced by the {@code equality}
   * labeled alternative in {@link AppPALParser#c}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitEquality(AppPALParser.EqualityContext ctx)
  {
    return new Equals((CE) this.visit(ctx.ce(0)), (CE) this.visit(ctx.ce(1)));
  }

  /**
   * Visit a parse tree produced by the {@code conjugation}
   * labeled alternative in {@link AppPALParser#c}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  public Object visitConjugation(AppPALParser.ConjugationContext ctx)
  {
    return new Conj((Constraint) this.visit(ctx.c(0)), (Constraint) this.visit(ctx.c(1)));
  }
}
