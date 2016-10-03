package apppal.application;

import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.evaluation.Result;
import apppal.logic.language.Assertion;
import apppal.logic.language.Constant;
import apppal.Util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class PPC
{
  private List<String> query_files;
  private List<String> policy_files;
  private Set<Assertion> queries;
  private final Evaluation evaluation;
  private final AC ac;
  private PrintStream out;
  private Boolean interactive;
  private Boolean dump_info;

  public PPC(String[] args)
  {
    this.policy_files = new LinkedList<String>();
    this.query_files = new LinkedList<String>();
    this.queries = new HashSet<Assertion>();
    this.ac = new AC();
    this.interactive = false;
    this.out = System.out;
    this.dump_info = false;

    // Command line parsing
    int i = 0;
    try
    { for (i = 0; i < args.length; i = i+1)
      {
        switch (args[i])
        { case "-p":
            this.policy_files.add(args[++i]);
            break;

          case "-q":
            this.query_files.add(args[++i]);
            break;

          case "-o":
            this.out = new PrintStream(args[++i]);
            break;

          case "-h":
            usage();
            System.exit(0);
            break;

          case "-i":
            this.interactive = true;
            break;

          case "-D":
            this.dump_info = true;
            break;

          case "--debug":
            Util.enable_debug = true;
            Util.debug("debug enabled");
            break;

          default:
            Util.error("unrecognised flag '"+args[i]+"'");
            usage();
            System.exit(1);
        }
      }
    }
    catch (Exception e)
    { Util.error("\nMissing argument for '"+args[i-1]+"'");
      Util.debug(e.toString());
      System.exit(2);
    }

    // Build the assertion context
    for (String file : this.policy_files)
    {
      Util.info("loading policy file '"+file+"'");
      try
      { this.ac.merge(new AC(new FileInputStream(file))); }
      catch (IOException e)
      { Util.error("failed to load policy file: "+e);
        System.exit(3);
      }
    }

    // Build the list of queries
    for (String file : this.query_files)
    {
      Util.info("loading query file '"+file+"'");
      final List<Assertion> assertions;
      try
      { assertions = AC.parse(new FileInputStream(file));
        for (Assertion a : assertions)
          this.queries.add(a);
      }
      catch (IOException e)
      { Util.error("failed to load query file: "+e);
        System.exit(4);
      }
    }

    // Run stuff
    this.evaluation = new Evaluation(this.ac);
  }

  public void run()
  {
    final Evaluation e = new Evaluation(this.ac);

    if (this.dump_info)
    {
      System.out.println("Constants:");
      for (Constant c : this.ac.constants)
      {
        final boolean voiced = this.ac.voiced.contains(c);
        final boolean subjects = this.ac.subjects.contains(c);
        final boolean interesting = this.ac.interesting.contains(c);

        System.out.print(voiced      ? "v" : " ");
        System.out.print(subjects    ? "s" : " ");
        System.out.print(interesting ? "i" : " ");
        System.out.print(" ");
        System.out.println(c);
      }

      System.out.println("\nPredicates:");
      final Set<String> predicates = new HashSet<String>();
      for (final Assertion a : this.ac.assertions)
      {
        predicates.addAll(a.getPredicates());
      }
      final Set<String> derivable = e.getDerivable();
      for (String p : predicates)
      {
          System.out.print(derivable.contains(p) ? "d" : " ");
          System.out.print(" ");
          System.out.println(p);
      }

      System.out.println("\nRules:\n");
      int n = 1;
      for (final Assertion a : this.ac.assertions)
      {
        if (! a.isGround()) 
        {
          System.out.println(n + ".\t" + a);
          n +=1;
        }
      }
    }

    if (this.queries.size() > 0)
    {
      Util.info("running queries");
      for (Assertion query : this.queries)
      {
        final Result result = e.run(query);
        this.out.println((result.isProven() ? "YES: " : "NO:  ")+query);
        this.out.println(result.proof.toString());
      }
    }

    if (this.interactive) repl();
  }

  private void runQuery(Assertion query)
  {
    final Evaluation e = new Evaluation(this.ac);
    final Result result = e.run(query);

    final String str = (result.isProven() ? "YES: " : "NO:  ")+query;
    synchronized (this.out)
    {
      this.out.println(str);
      this.out.println(result.proof.toString());
    }
  }


  private void repl()
  {
    Util.info("starting REPL");
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    for (;;)
    { try
      {
        System.out.print("?- ");

        final String line;
        try { line = br.readLine(); }
        catch (NullPointerException e) { return; }

        if (line.isEmpty()) continue;
        if (line == null) return;

        final Assertion query = Assertion.parse(line);
        final Result result = this.evaluation.run(query);

        this.out.println((result.isProven() ? "YES: " : "NO:  ")+line);
        this.out.println(result.proof.toString());

      }
      catch (Exception e)
      { // Handle someone hitting C-D to quit.  God knows why we need this.
        // I'm blaming Java.  It's a stupid language.
        if (e.toString() == "java.lang.NullPointerException") return;
        else Util.warn("something went wrong: "+e);
      }
    }
  }

  public static void main(String[] args) throws Exception
  {
    banner();
    PPC ppc = new PPC(args);
    ppc.run();
  }

  public static void banner()
  {
    System.out.println("AppPAL PPC");
    System.out.println(" - The App Policy Authorization Logic");
    System.out.println("   for PC-based Policy Checking");
    System.out.println("");
  }

  public static void usage()
  {
    System.out.println("Flags:");
    System.out.println("  -h         show this message");
    System.out.println("  -i         read queries from the commandline");
    System.out.println("  -o FILE    write results to this file");
    System.out.println("  -p FILE    add a policy file to the AC");
    System.out.println("  -q FILE    add a file of queries to be run");
    System.out.println("  -D         show whats in the policy");
  }
}
