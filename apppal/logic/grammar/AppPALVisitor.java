// Generated from /Users/bogwonch/Repos/AppPAL/app/src/main/java/appguarden/apppal/grammar/AppPAL.g4 by ANTLR 4.5
package apppal.logic.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AppPALParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AppPALVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(AppPALParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constant}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(AppPALParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code zero}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitZero(AppPALParser.ZeroContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inf}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInf(AppPALParser.InfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code predicate}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(AppPALParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code canSay}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCanSay(AppPALParser.CanSayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code canActAs}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCanActAs(AppPALParser.CanActAsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AppPALParser#fact}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFact(AppPALParser.FactContext ctx);
	/**
	 * Visit a parse tree produced by {@link AppPALParser#claim}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClaim(AppPALParser.ClaimContext ctx);
	/**
	 * Visit a parse tree produced by {@link AppPALParser#assertion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertion(AppPALParser.AssertionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AppPALParser#ac}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAc(AppPALParser.AcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code entity}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntity(AppPALParser.EntityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code function}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(AppPALParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(AppPALParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(AppPALParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegation(AppPALParser.NegationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code satisfaction}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSatisfaction(AppPALParser.SatisfactionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equality}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality(AppPALParser.EqualityContext ctx);
	/**
	 * Visit a parse tree produced by the {@code conjugation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjugation(AppPALParser.ConjugationContext ctx);
}
