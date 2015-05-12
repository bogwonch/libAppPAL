package apppal.logic.evaluation;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import apppal.logic.grammar.AppPALEmitter;
import apppal.logic.grammar.AppPALLexer;
import apppal.logic.grammar.AppPALParser;
import apppal.logic.language.Assertion;
import apppal.logic.language.Constant;
import apppal.logic.language.E;

/**
 * An assertion context is the result of loading a policy file into AppPAL.
 * It contains all manner of information about how AppPAL statements should be evaluated.
 */
public class AC
{
  public final List<Assertion> assertions;
  public final Set<Constant> constants;

  // Types of constants
  public final Set<Constant> voiced;
  public final Set<Constant> subjects;
  public final Set<Constant> interesting; // voiced U subjects

  /**
   * Check all assertions in the context meet the safety property.
   * @throws IllegalArgumentException
   */
  private void checkAssertionSafety() throws IllegalArgumentException
  {
    for (Assertion a : this.assertions)
      if (! a.isSafe())
        throw new IllegalArgumentException("unsafe assertion: "+a);
  }

  /**
   * Populate the list of constants from the assertions.
   */
  private void populateConstants()
  {
    for (Assertion a : this.assertions)
    {
      this.voiced.addAll(a.getVoiced());
      this.subjects.addAll(a.getSubjects());
      this.constants.addAll(a.consts());
    }
    this.interesting.addAll(this.voiced);
    this.interesting.addAll(this.subjects);

    /* System.err.println("VOICED:"); for (E e : this.voiced) { System.err.println("  "+e); } */
    /* System.err.println("SUBJECTS:"); for (E e : this.subjects) { System.err.println("  "+e); } */
    /* System.err.println("INTERESTING:"); for (E e : this.interesting) { System.err.println("  "+e); } */
  }

  /**
   * Parse an Assertion context out of an input stream
   * @param in input stream to parse
   * @return the list of assertions
   * @throws IOException
   */
  public static List<Assertion> parse(InputStream in) throws IOException
  {
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.ac();
    AppPALEmitter emitter = new AppPALEmitter();

    return (List<Assertion>) emitter.visit(tree);
  }

  public AC(String str) throws IOException
  { this(new ByteArrayInputStream(str.getBytes("UTF-8"))); }

  public AC(InputStream in) throws IOException { this(AC.parse(in)); }

  public AC(List<Assertion> as)
  {
    this.assertions = as;
    this.constants = new HashSet<>();
    this.voiced = new HashSet<>();
    this.subjects = new HashSet<>();
    this.interesting = new HashSet<>();

    this.checkAssertionSafety();
    this.populateConstants();
  }

  public AC()
  {
    this.assertions = new LinkedList<>();
    this.constants = new HashSet<>();
    this.voiced = new HashSet<>();
    this.subjects = new HashSet<>();
    this.interesting = new HashSet<>();
  }

  /**
   * Merge two assertion contexts into one mega-assertion context.
   * @param other the context to merge
   */
  public void merge(AC other)
  {
    this.assertions.addAll(other.assertions);
    this.populateConstants();
  }
}
