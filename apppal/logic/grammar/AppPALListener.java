// Generated from /Users/bogwonch/Repos/AppPAL/app/src/main/java/appguarden/apppal/grammar/AppPAL.g4 by ANTLR 4.5
package apppal.logic.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AppPALParser}.
 */
public interface AppPALListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code variable}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 */
	void enterVariable(AppPALParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 */
	void exitVariable(AppPALParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constant}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 */
	void enterConstant(AppPALParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constant}
	 * labeled alternative in {@link AppPALParser#e}.
	 * @param ctx the parse tree
	 */
	void exitConstant(AppPALParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code zero}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 */
	void enterZero(AppPALParser.ZeroContext ctx);
	/**
	 * Exit a parse tree produced by the {@code zero}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 */
	void exitZero(AppPALParser.ZeroContext ctx);
	/**
	 * Enter a parse tree produced by the {@code inf}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 */
	void enterInf(AppPALParser.InfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code inf}
	 * labeled alternative in {@link AppPALParser#d}.
	 * @param ctx the parse tree
	 */
	void exitInf(AppPALParser.InfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code predicate}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(AppPALParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code predicate}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(AppPALParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code canSay}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void enterCanSay(AppPALParser.CanSayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code canSay}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void exitCanSay(AppPALParser.CanSayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code canActAs}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void enterCanActAs(AppPALParser.CanActAsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code canActAs}
	 * labeled alternative in {@link AppPALParser#vp}.
	 * @param ctx the parse tree
	 */
	void exitCanActAs(AppPALParser.CanActAsContext ctx);
	/**
	 * Enter a parse tree produced by {@link AppPALParser#fact}.
	 * @param ctx the parse tree
	 */
	void enterFact(AppPALParser.FactContext ctx);
	/**
	 * Exit a parse tree produced by {@link AppPALParser#fact}.
	 * @param ctx the parse tree
	 */
	void exitFact(AppPALParser.FactContext ctx);
	/**
	 * Enter a parse tree produced by {@link AppPALParser#claim}.
	 * @param ctx the parse tree
	 */
	void enterClaim(AppPALParser.ClaimContext ctx);
	/**
	 * Exit a parse tree produced by {@link AppPALParser#claim}.
	 * @param ctx the parse tree
	 */
	void exitClaim(AppPALParser.ClaimContext ctx);
	/**
	 * Enter a parse tree produced by {@link AppPALParser#assertion}.
	 * @param ctx the parse tree
	 */
	void enterAssertion(AppPALParser.AssertionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AppPALParser#assertion}.
	 * @param ctx the parse tree
	 */
	void exitAssertion(AppPALParser.AssertionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AppPALParser#ac}.
	 * @param ctx the parse tree
	 */
	void enterAc(AppPALParser.AcContext ctx);
	/**
	 * Exit a parse tree produced by {@link AppPALParser#ac}.
	 * @param ctx the parse tree
	 */
	void exitAc(AppPALParser.AcContext ctx);
	/**
	 * Enter a parse tree produced by the {@code entity}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void enterEntity(AppPALParser.EntityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code entity}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void exitEntity(AppPALParser.EntityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code function}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void enterFunction(AppPALParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code function}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void exitFunction(AppPALParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void enterTrue(AppPALParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void exitTrue(AppPALParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void enterFalse(AppPALParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link AppPALParser#ce}.
	 * @param ctx the parse tree
	 */
	void exitFalse(AppPALParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void enterNegation(AppPALParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void exitNegation(AppPALParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code satisfaction}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void enterSatisfaction(AppPALParser.SatisfactionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code satisfaction}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void exitSatisfaction(AppPALParser.SatisfactionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equality}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void enterEquality(AppPALParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equality}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void exitEquality(AppPALParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conjugation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void enterConjugation(AppPALParser.ConjugationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conjugation}
	 * labeled alternative in {@link AppPALParser#c}.
	 * @param ctx the parse tree
	 */
	void exitConjugation(AppPALParser.ConjugationContext ctx);
}
