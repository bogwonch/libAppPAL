package apppal.logic.language;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import apppal.logic.evaluation.Substitution;
import apppal.logic.evaluation.Unification;
import apppal.logic.grammar.AppPALEmitter;
import apppal.logic.grammar.AppPALLexer;
import apppal.logic.grammar.AppPALParser;
import apppal.logic.interfaces.EntityHolding;
import apppal.logic.interfaces.Unifiable;

/**
 * SecPAL Assertion
 */
public class Assertion implements EntityHolding, Unifiable<Assertion>
{
  public final E speaker;
  public final Claim says;
  private final int scope;

  private static int number = 0;

  public Assertion(E speaker, Claim says)
  { this(speaker, says, ++Assertion.number); }

  public Assertion(E speaker, Claim says, int scope)
  {
    this.speaker = speaker;
    this.says = says;
    this.scope = scope;
    this.scope(scope);
    for (Fact f : this.says.antecedents)
      f.implicitSpeaker = speaker;
  }

  public Set<Variable> vars()
  {
    Set<Variable> vars = this.speaker.vars();
    vars.addAll(this.says.vars());
    return vars;
  }

  public Set<Constant> consts()
  {
    Set<Constant> consts = this.speaker.consts();
    consts.addAll(this.says.consts());
    return consts;
  }

  /** Create an assertion by parsing a string
   * @param str the assertion to parse
   * @returns the parsed assertion
   */
  public static Assertion parse(String str) throws IOException
  {
    InputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
    ANTLRInputStream input = new ANTLRInputStream(in);
    AppPALLexer lexer = new AppPALLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    AppPALParser parser = new AppPALParser(tokens);
    ParseTree tree = parser.assertion();
    AppPALEmitter emitter = new AppPALEmitter();
    return (Assertion) emitter.visit(tree);
  }

  public String toString()
  {
    return this.speaker + " says " + this.says + ".";
  }

  /** @brief Check whether the assertion meets the SecPAL safety conditions.
   *
   * An assertion a (A says f if f1,...,fn where c) is safe if:
   *
   * 1. a) if f is flat => all v in vars(f) are safe in a.
   *    b) if f is (e can-say ...) => e is safe in a.
   * 2. vars c are a subset of the vars of the consequent and antecedent.
   * 3. all antecedent facts are flat.
   *
   * @returns boolean
   */
  public boolean isSafe()
  {
    // 1. a) if f is flat => all v in vars(f) are safe in a.
    if (this.says.consequent.isFlat())
    {
      for (E e : this.says.consequent.vars())
        if (! e.safeIn(this)) { return false; }
    }
    //   b) if f is (e can-say ...) => e is safe in a.
    else
    {
      assert(this.says.consequent.object instanceof CanSay);
      if (! this.says.consequent.subject.safeIn(this))
        return false;
    }

    // 2. vars c are a subset of the vars of the consequent and antecedent.
    Set<Variable> c_vars = this.says.constraint.vars();
    Set<Variable> o_vars = this.says.consequent.vars();
    o_vars.addAll(this.says.antecedentVars());
    if (! o_vars.containsAll(c_vars))
      return false;

    // 3. all antecedent facts are flat.
    if (this.says.hasAntecedents())
      for (Fact f : this.says.antecedents)
        if (! f.isFlat()) { return false; }

    return true;
  }

  @Override
  public Unification unify(Assertion that)
  {
    final Unification unification = this.speaker.unify(that.speaker);
    if (! unification.hasFailed())
    {
      Claim thetaX = this.says.substitute(unification.theta);
      Claim thetaY = that.says.substitute(unification.theta);
      unification.compose(thetaX.unify(thetaY));
    }
    return unification;
  }

  @Override
  public Assertion substitute(Map<Variable, Substitution> delta)
  {
    final Claim says = this.says.substitute(delta);
    return new Assertion(speaker, says);
  }

  public Set<Constant> getVoiced()
  {
    Set<Constant> voiced = new HashSet<>();
    if (this.speaker instanceof Constant)
      voiced.add((Constant) this.speaker);

    /* // If we never have any statements from this delegated speaker why bother to search?
    if ((this.says.consequent.object instanceof CanSay)
      && (this.says.consequent.subject instanceof Constant))
      voiced.add((Constant) this.says.consequent.subject);
    */
    return voiced;
  }

  public Set<Constant> getSubjects()
  {
    Set<Constant> subjects = new HashSet<>();
    if (this.says.consequent.subject instanceof Constant)
      subjects.add((Constant) this.says.consequent.subject);

    for (Fact f : this.says.antecedents)
      if (f.subject instanceof Constant)
        subjects.add((Constant) f.subject);

    return subjects;
  }


  /**
   * When writing tests it is helpful to be able to reset the global assertion counter so we know which assertions have which scopes.
   * THIS SHOULD NEVER BE CALLED IN THE REAL WORLD.
   */
  public static void resetScope()
  {
    Assertion.number = 0;
  }

  private void scope(int scope)
  {
    this.speaker.scope(scope);
    this.says.scope(scope);
  }

  public Assertion consequence()
  { return new Assertion(this.speaker, new Claim(this.says.consequent), this.scope); }

  // TODO: refactor into constructors
  public static Assertion makeCanActAs(E speaker, E subject, Constant c)
  {
    return Assertion.make(speaker, subject, new CanActAs(c));
  }

  public static Assertion make(E speaker, E c, VP object)
  {
    return Assertion.make(speaker, new Fact(c, object));
  }

  public static Assertion make(E speaker, Fact consequent)
  {
    return new Assertion(speaker, new Claim(consequent));
  }

  public static Assertion makeCanSay(E speaker, Constant c, D d, Fact fact)
  {
    return Assertion.make(speaker, c, new CanSay(d, fact));
  }
}
