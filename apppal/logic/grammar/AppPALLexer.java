// Generated from apppal/logic/grammar/AppPAL.g4 by ANTLR 4.5
package apppal.logic.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AppPALLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, VARIABLE=15, CONSTANT=16, 
		ZERO=17, INF=18, PREDICATE_NAME=19, WS=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "VARIABLE", "CONSTANT", "ZERO", 
		"INF", "PREDICATE_NAME", "WS", "LOWER", "UPPER", "TOKENCHAR"
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


	public AppPALLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "AppPAL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26\u0096\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\7\20p\n\20\f\20\16\20s"+
		"\13\20\3\21\3\21\6\21w\n\21\r\21\16\21x\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\7\24\u0085\n\24\f\24\16\24\u0088\13\24\3\25\6\25"+
		"\u008b\n\25\r\25\16\25\u008c\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\2"+
		"\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\2-\2/\2\3\2\7\3\2$$\5\2\13\f\17\17\"\""+
		"\3\2c|\3\2C\\\6\2//\62;C\\c|\u0096\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2\2\2\t\67\3\2\2\2\13?\3\2\2\2\rJ"+
		"\3\2\2\2\17M\3\2\2\2\21S\3\2\2\2\23X\3\2\2\2\25Z\3\2\2\2\27_\3\2\2\2\31"+
		"e\3\2\2\2\33i\3\2\2\2\35k\3\2\2\2\37m\3\2\2\2!t\3\2\2\2#|\3\2\2\2%~\3"+
		"\2\2\2\'\u0082\3\2\2\2)\u008a\3\2\2\2+\u0090\3\2\2\2-\u0092\3\2\2\2/\u0094"+
		"\3\2\2\2\61\62\7*\2\2\62\4\3\2\2\2\63\64\7.\2\2\64\6\3\2\2\2\65\66\7+"+
		"\2\2\66\b\3\2\2\2\678\7e\2\289\7c\2\29:\7p\2\2:;\7/\2\2;<\7u\2\2<=\7c"+
		"\2\2=>\7{\2\2>\n\3\2\2\2?@\7e\2\2@A\7c\2\2AB\7p\2\2BC\7/\2\2CD\7c\2\2"+
		"DE\7e\2\2EF\7v\2\2FG\7/\2\2GH\7c\2\2HI\7u\2\2I\f\3\2\2\2JK\7k\2\2KL\7"+
		"h\2\2L\16\3\2\2\2MN\7y\2\2NO\7j\2\2OP\7g\2\2PQ\7t\2\2QR\7g\2\2R\20\3\2"+
		"\2\2ST\7u\2\2TU\7c\2\2UV\7{\2\2VW\7u\2\2W\22\3\2\2\2XY\7\60\2\2Y\24\3"+
		"\2\2\2Z[\7v\2\2[\\\7t\2\2\\]\7w\2\2]^\7g\2\2^\26\3\2\2\2_`\7h\2\2`a\7"+
		"c\2\2ab\7n\2\2bc\7u\2\2cd\7g\2\2d\30\3\2\2\2ef\7u\2\2fg\7c\2\2gh\7v\2"+
		"\2h\32\3\2\2\2ij\7#\2\2j\34\3\2\2\2kl\7?\2\2l\36\3\2\2\2mq\5-\27\2np\5"+
		"/\30\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2r \3\2\2\2sq\3\2\2\2tv\7"+
		"$\2\2uw\n\2\2\2vu\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\7"+
		"$\2\2{\"\3\2\2\2|}\7\62\2\2}$\3\2\2\2~\177\7k\2\2\177\u0080\7p\2\2\u0080"+
		"\u0081\7h\2\2\u0081&\3\2\2\2\u0082\u0086\5+\26\2\u0083\u0085\5/\30\2\u0084"+
		"\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2"+
		"\2\2\u0087(\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\t\3\2\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\u008f\b\25\2\2\u008f*\3\2\2\2\u0090\u0091\t\4\2\2"+
		"\u0091,\3\2\2\2\u0092\u0093\t\5\2\2\u0093.\3\2\2\2\u0094\u0095\t\6\2\2"+
		"\u0095\60\3\2\2\2\7\2qx\u0086\u008c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}