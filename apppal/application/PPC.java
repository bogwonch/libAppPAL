package apppal.application;

import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.evaluation.Result;
import apppal.logic.language.Assertion;
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

  public PPC(String[] args)
  {
    this.policy_files = new LinkedList<String>();
    this.query_files = new LinkedList<String>();
    this.queries = new HashSet<Assertion>();
    this.ac = new AC();
    this.interactive = false;
    this.out = System.out;

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

          default:
            System.err.println("unrecognised flag '"+args[i]+"'");
            usage();
            System.exit(1);
        }
      }
    }
    catch (Exception e)
    { System.err.println("\nMissing argument for '"+args[i-1]+"'");
      e.printStackTrace();
      System.exit(2);
    }

    // Build the assertion context
    for (String file : this.policy_files)
    {
      System.err.println("[+] Loading policy file '"+file+"'");
      try
      { this.ac.merge(new AC(new FileInputStream(file))); }
      catch (IOException e)
      { System.err.println("AppPAL: error: "+e);
        System.exit(3);
      }
    }

    // Build the list of queries
    for (String file : this.query_files)
    {
      System.err.println("[+] Loading query file '"+file+"'");
      final List<Assertion> assertions;
      try
      { assertions = AC.parse(new FileInputStream(file));
        for (Assertion a : assertions)
          this.queries.add(a);
      }
      catch (IOException e)
      { System.err.println("AppPAL: error: "+e);
        System.exit(4);
      }
    }
    
    // Run stuff
    this.evaluation = new Evaluation(this.ac);
  }

  public void run()
  {
    if (this.queries.size() > 0)
    {
      System.err.println("[+] Running queries");
      for (Assertion query : this.queries)
      {
        final Evaluation e = new Evaluation(this.ac);
        final Result result = e.run(query);
        this.out.println((result.isProven() ? "YES: " : "NO:  ")+query);
      }
    }

    if (this.interactive) repl();
  }

  private void runQuery(Assertion query)
  {
    final Evaluation e = new Evaluation(this.ac);
    final Result result = e.run(query);

    final String str = (result.isProven() ? "YES: " : "NO:  ")+query; 
    synchronized (this.out) { this.out.println(str); }
  }


  private void repl() 
  {
    System.err.println("[+] Starting REPL");
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

      }
      catch (Exception e)
      { // Handle someone hitting C-D to quit.  God knows why we need this.
        // I'm blaming Java.  It's a stupid language.
        if (e.toString() == "java.lang.NullPointerException") return;
        else System.err.println("AppPAL: error: "+e);
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
    //TODO replace with some figlet ASCII art
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
  }
}
