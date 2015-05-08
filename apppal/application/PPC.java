package apppal.application;

import apppal.logic.evaluation.AC;
import apppal.logic.evaluation.Evaluation;
import apppal.logic.language.Assertion;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PPC
{
  private List<String> query_files;
  private List<String> policy_files;
  private List<Assertion> queries;
  private final Evaluation evaluation;
  private final AC ac;
  private PrintStream out;
  private Boolean interactive;

  public PPC(String[] args)
  {
    this.policy_files = new LinkedList<String>();
    this.query_files = new LinkedList<String>();
    this.queries = new LinkedList<Assertion>();
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
    { System.err.println("Missing argument for '"+args[i-1]+"'");
      System.exit(2);
    }

    // Build the assertion context
    for (String file : this.policy_files)
    {
      try
      { this.ac.merge(new AC(new FileInputStream(file))); }
      catch (IOException e)
      { System.err.println("AppPAL: error: "+e);
        System.exit(3);
      }
    }

    // Build the list of queries
    for (String file : this.policy_files)
    { final List<Assertion> assertions;
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
    if (this.interactive) repl();
  }

  private void repl() 
  {
    try {
    String query = "\"AppPAL\" says \"AppPAL\" isCool."; 
    Assertion a = Assertion.parse(query);
    this.out.println("?- "+a.toString());
    }
    catch (Exception e)
    { System.err.println("AppPAL: error: "+e); }
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
