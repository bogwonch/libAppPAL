// Generated from apppal/logic/grammar/AppPAL.g4 by ANTLR 4.5
package apppal.logic.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AppPALParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, VARIABLE=15, CONSTANT=16, 
		ZERO=17, INF=18, PREDICATE_NAME=19, WS=20;
	public static final int
		RULE_e = 0, RULE_d = 1, RULE_vp = 2, RULE_fact = 3, RULE_claim = 4, RULE_assertion = 5, 
		RULE_ac = 6, RULE_ce = 7, RULE_c = 8;
	public static final String[] ruleNames = {
		"e", "d", "vp", "fact", "claim", "assertion", "ac", "ce", "c"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "','", "')'", "'can-say'", "'can-act-as'", "'if'", "'where'", 
		"'says'", "'.'", "'true'", "'false'", "'sat'", "'!'", "'='", null, null, 
		"'0'", "'inf'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "VARIABLE", "CONSTANT", "ZERO", "INF", "PREDICATE_NAME", 
		"WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AppPAL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AppPALParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EContext extends ParserRuleContext {
		public EContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e; }
	 
		public EContext() { }
		public void copyFrom(EContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConstantContext extends EContext {
		public TerminalNode CONSTANT() { return getToken(AppPALParser.CONSTANT, 0); }
		public ConstantContext(EContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableContext extends EContext {
		public TerminalNode VARIABLE() { return getToken(AppPALParser.VARIABLE, 0); }
		public VariableContext(EContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EContext e() throws RecognitionException {
		EContext _localctx = new EContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_e);
		try {
			setState(20);
			switch (_input.LA(1)) {
			case VARIABLE:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				match(VARIABLE);
				}
				break;
			case CONSTANT:
				_localctx = new ConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				match(CONSTANT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DContext extends ParserRuleContext {
		public DContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_d; }
	 
		public DContext() { }
		public void copyFrom(DContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ZeroContext extends DContext {
		public TerminalNode ZERO() { return getToken(AppPALParser.ZERO, 0); }
		public ZeroContext(DContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterZero(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitZero(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitZero(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InfContext extends DContext {
		public TerminalNode INF() { return getToken(AppPALParser.INF, 0); }
		public InfContext(DContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterInf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitInf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitInf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DContext d() throws RecognitionException {
		DContext _localctx = new DContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_d);
		try {
			setState(24);
			switch (_input.LA(1)) {
			case ZERO:
				_localctx = new ZeroContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				match(ZERO);
				}
				break;
			case INF:
				_localctx = new InfContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				match(INF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VpContext extends ParserRuleContext {
		public VpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vp; }
	 
		public VpContext() { }
		public void copyFrom(VpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PredicateContext extends VpContext {
		public TerminalNode PREDICATE_NAME() { return getToken(AppPALParser.PREDICATE_NAME, 0); }
		public List<EContext> e() {
			return getRuleContexts(EContext.class);
		}
		public EContext e(int i) {
			return getRuleContext(EContext.class,i);
		}
		public PredicateContext(VpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CanSayContext extends VpContext {
		public DContext d() {
			return getRuleContext(DContext.class,0);
		}
		public FactContext fact() {
			return getRuleContext(FactContext.class,0);
		}
		public CanSayContext(VpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterCanSay(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitCanSay(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitCanSay(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CanActAsContext extends VpContext {
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public CanActAsContext(VpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterCanActAs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitCanActAs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitCanActAs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VpContext vp() throws RecognitionException {
		VpContext _localctx = new VpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_vp);
		int _la;
		try {
			setState(46);
			switch (_input.LA(1)) {
			case PREDICATE_NAME:
				_localctx = new PredicateContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				match(PREDICATE_NAME);
				setState(38);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(27);
					match(T__0);
					setState(28);
					e();
					setState(33);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(29);
						match(T__1);
						setState(30);
						e();
						}
						}
						setState(35);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(36);
					match(T__2);
					}
				}

				}
				break;
			case T__3:
				_localctx = new CanSayContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(40);
				match(T__3);
				setState(41);
				d();
				setState(42);
				fact();
				}
				break;
			case T__4:
				_localctx = new CanActAsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(44);
				match(T__4);
				setState(45);
				e();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactContext extends ParserRuleContext {
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public VpContext vp() {
			return getRuleContext(VpContext.class,0);
		}
		public FactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fact; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterFact(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitFact(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitFact(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactContext fact() throws RecognitionException {
		FactContext _localctx = new FactContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_fact);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			e();
			setState(49);
			vp();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClaimContext extends ParserRuleContext {
		public List<FactContext> fact() {
			return getRuleContexts(FactContext.class);
		}
		public FactContext fact(int i) {
			return getRuleContext(FactContext.class,i);
		}
		public CContext c() {
			return getRuleContext(CContext.class,0);
		}
		public ClaimContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_claim; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterClaim(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitClaim(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitClaim(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClaimContext claim() throws RecognitionException {
		ClaimContext _localctx = new ClaimContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_claim);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			fact();
			setState(61);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(52);
				match(T__5);
				{
				setState(53);
				fact();
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(54);
					match(T__1);
					setState(55);
					fact();
					}
					}
					setState(60);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
			}

			setState(65);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(63);
				match(T__6);
				setState(64);
				c(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertionContext extends ParserRuleContext {
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public ClaimContext claim() {
			return getRuleContext(ClaimContext.class,0);
		}
		public AssertionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterAssertion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitAssertion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitAssertion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertionContext assertion() throws RecognitionException {
		AssertionContext _localctx = new AssertionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assertion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			e();
			setState(68);
			match(T__7);
			setState(69);
			claim();
			setState(70);
			match(T__8);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AcContext extends ParserRuleContext {
		public List<AssertionContext> assertion() {
			return getRuleContexts(AssertionContext.class);
		}
		public AssertionContext assertion(int i) {
			return getRuleContext(AssertionContext.class,i);
		}
		public AcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ac; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterAc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitAc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitAc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AcContext ac() throws RecognitionException {
		AcContext _localctx = new AcContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ac);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(72);
				assertion();
				}
				}
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==VARIABLE || _la==CONSTANT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CeContext extends ParserRuleContext {
		public CeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ce; }
	 
		public CeContext() { }
		public void copyFrom(CeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FunctionContext extends CeContext {
		public TerminalNode PREDICATE_NAME() { return getToken(AppPALParser.PREDICATE_NAME, 0); }
		public List<CeContext> ce() {
			return getRuleContexts(CeContext.class);
		}
		public CeContext ce(int i) {
			return getRuleContext(CeContext.class,i);
		}
		public FunctionContext(CeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends CeContext {
		public TrueContext(CeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitTrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends CeContext {
		public FalseContext(CeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitFalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EntityContext extends CeContext {
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public EntityContext(CeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterEntity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitEntity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitEntity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CeContext ce() throws RecognitionException {
		CeContext _localctx = new CeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ce);
		int _la;
		try {
			setState(93);
			switch (_input.LA(1)) {
			case VARIABLE:
			case CONSTANT:
				_localctx = new EntityContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				e();
				}
				break;
			case PREDICATE_NAME:
				_localctx = new FunctionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				match(PREDICATE_NAME);
				setState(79);
				match(T__0);
				setState(88);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << VARIABLE) | (1L << CONSTANT) | (1L << PREDICATE_NAME))) != 0)) {
					{
					setState(80);
					ce();
					setState(85);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(81);
						match(T__1);
						setState(82);
						ce();
						}
						}
						setState(87);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(90);
				match(T__2);
				}
				break;
			case T__9:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				match(T__9);
				}
				break;
			case T__10:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(92);
				match(T__10);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CContext extends ParserRuleContext {
		public CContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_c; }
	 
		public CContext() { }
		public void copyFrom(CContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NegationContext extends CContext {
		public CContext c() {
			return getRuleContext(CContext.class,0);
		}
		public NegationContext(CContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterNegation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitNegation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitNegation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SatisfactionContext extends CContext {
		public SatisfactionContext(CContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterSatisfaction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitSatisfaction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitSatisfaction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualityContext extends CContext {
		public List<CeContext> ce() {
			return getRuleContexts(CeContext.class);
		}
		public CeContext ce(int i) {
			return getRuleContext(CeContext.class,i);
		}
		public EqualityContext(CContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterEquality(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitEquality(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitEquality(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConjugationContext extends CContext {
		public List<CContext> c() {
			return getRuleContexts(CContext.class);
		}
		public CContext c(int i) {
			return getRuleContext(CContext.class,i);
		}
		public ConjugationContext(CContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).enterConjugation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AppPALListener ) ((AppPALListener)listener).exitConjugation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AppPALVisitor ) return ((AppPALVisitor<? extends T>)visitor).visitConjugation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CContext c() throws RecognitionException {
		return c(0);
	}

	private CContext c(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CContext _localctx = new CContext(_ctx, _parentState);
		CContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_c, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			switch (_input.LA(1)) {
			case T__12:
				{
				_localctx = new NegationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(96);
				match(T__12);
				setState(97);
				c(3);
				}
				break;
			case T__11:
				{
				_localctx = new SatisfactionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98);
				match(T__11);
				}
				break;
			case T__9:
			case T__10:
			case VARIABLE:
			case CONSTANT:
			case PREDICATE_NAME:
				{
				_localctx = new EqualityContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99);
				ce();
				setState(100);
				match(T__13);
				setState(101);
				ce();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(110);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConjugationContext(new CContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_c);
					setState(105);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(106);
					match(T__1);
					setState(107);
					c(2);
					}
					} 
				}
				setState(112);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8:
			return c_sempred((CContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean c_sempred(CContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26t\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\5\2"+
		"\27\n\2\3\3\3\3\5\3\33\n\3\3\4\3\4\3\4\3\4\3\4\7\4\"\n\4\f\4\16\4%\13"+
		"\4\3\4\3\4\5\4)\n\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\61\n\4\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\7\6;\n\6\f\6\16\6>\13\6\5\6@\n\6\3\6\3\6\5\6D\n\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\6\bL\n\b\r\b\16\bM\3\t\3\t\3\t\3\t\3\t\3\t\7\tV\n"+
		"\t\f\t\16\tY\13\t\5\t[\n\t\3\t\3\t\3\t\5\t`\n\t\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\5\nj\n\n\3\n\3\n\3\n\7\no\n\n\f\n\16\nr\13\n\3\n\2\3\22\13"+
		"\2\4\6\b\n\f\16\20\22\2\2|\2\26\3\2\2\2\4\32\3\2\2\2\6\60\3\2\2\2\b\62"+
		"\3\2\2\2\n\65\3\2\2\2\fE\3\2\2\2\16K\3\2\2\2\20_\3\2\2\2\22i\3\2\2\2\24"+
		"\27\7\21\2\2\25\27\7\22\2\2\26\24\3\2\2\2\26\25\3\2\2\2\27\3\3\2\2\2\30"+
		"\33\7\23\2\2\31\33\7\24\2\2\32\30\3\2\2\2\32\31\3\2\2\2\33\5\3\2\2\2\34"+
		"(\7\25\2\2\35\36\7\3\2\2\36#\5\2\2\2\37 \7\4\2\2 \"\5\2\2\2!\37\3\2\2"+
		"\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$&\3\2\2\2%#\3\2\2\2&\'\7\5\2\2\')\3"+
		"\2\2\2(\35\3\2\2\2()\3\2\2\2)\61\3\2\2\2*+\7\6\2\2+,\5\4\3\2,-\5\b\5\2"+
		"-\61\3\2\2\2./\7\7\2\2/\61\5\2\2\2\60\34\3\2\2\2\60*\3\2\2\2\60.\3\2\2"+
		"\2\61\7\3\2\2\2\62\63\5\2\2\2\63\64\5\6\4\2\64\t\3\2\2\2\65?\5\b\5\2\66"+
		"\67\7\b\2\2\67<\5\b\5\289\7\4\2\29;\5\b\5\2:8\3\2\2\2;>\3\2\2\2<:\3\2"+
		"\2\2<=\3\2\2\2=@\3\2\2\2><\3\2\2\2?\66\3\2\2\2?@\3\2\2\2@C\3\2\2\2AB\7"+
		"\t\2\2BD\5\22\n\2CA\3\2\2\2CD\3\2\2\2D\13\3\2\2\2EF\5\2\2\2FG\7\n\2\2"+
		"GH\5\n\6\2HI\7\13\2\2I\r\3\2\2\2JL\5\f\7\2KJ\3\2\2\2LM\3\2\2\2MK\3\2\2"+
		"\2MN\3\2\2\2N\17\3\2\2\2O`\5\2\2\2PQ\7\25\2\2QZ\7\3\2\2RW\5\20\t\2ST\7"+
		"\4\2\2TV\5\20\t\2US\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X[\3\2\2\2YW"+
		"\3\2\2\2ZR\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\`\7\5\2\2]`\7\f\2\2^`\7\r\2\2"+
		"_O\3\2\2\2_P\3\2\2\2_]\3\2\2\2_^\3\2\2\2`\21\3\2\2\2ab\b\n\1\2bc\7\17"+
		"\2\2cj\5\22\n\5dj\7\16\2\2ef\5\20\t\2fg\7\20\2\2gh\5\20\t\2hj\3\2\2\2"+
		"ia\3\2\2\2id\3\2\2\2ie\3\2\2\2jp\3\2\2\2kl\f\3\2\2lm\7\4\2\2mo\5\22\n"+
		"\4nk\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\23\3\2\2\2rp\3\2\2\2\20\26"+
		"\32#(\60<?CMWZ_ip";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}