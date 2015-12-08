package apppal.application;

import apppal.Util;
import apppal.lint.Completeness;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Lint
{
    private final boolean check_completeness;
    private final boolean check_consistency;
    private final boolean check_redundancy;

    private final List<String> policies;
    private final AC ac;

    public Lint(final boolean completeness,
                final boolean consistency,
                final boolean redundancy,
                final List<String> policies)
    {
        this.check_completeness = completeness;
        this.check_consistency  = consistency;
        this.check_redundancy   = redundancy;
        this.policies = policies;
        this.ac = new AC();

        if (!(check_completeness ||check_consistency || check_redundancy))
            Util.warn("no checks requested");

        if (policies.size() == 0)
            Util.warn("no files to check");

        this.load_policies();

        if (this.check_completeness)
        {
            final Completeness check = new Completeness(ac);
        }
        if (this.check_consistency)
        {
            Util.warn("consistency checks are unimplemented");
        }
        if (this.check_redundancy)
        {
            Util.warn("redundancy checks are unimplemented");
        }
    }

    private void load_policies()
    {
        int count = 0;
        for (final String file : this.policies)
        {
            Util.debug("loading '"+file+"'");
            try
            {
                final int old_size = this.ac.assertions.size();
                this.ac.merge(new AC(new FileInputStream(file)));
                final int new_size = this.ac.assertions.size();

                Util.debug("loaded "+(new_size-old_size)+" assertions ("+new_size+" in total)");
                count += 1;
            }
            catch (IOException e)
            {
                Util.error("failed to load '"+file+"'");
            }
        }

        Util.info("loaded "+count+"/"+this.policies.size()+" files of "+this.ac.assertions.size()+" assertions");
    }

    public static void main(final String[] args)
    {
        final List<String> policies = new LinkedList<>();
        boolean completeness = false;
        boolean consistency = false;
        boolean redundancy = false;

        for (final String arg : args)
        {
            switch(arg)
            {
            case "-h":
            case "--help":
                main_help();
                break;

            case "--completeness":
                completeness = true;
                break;

            case "--consistency":
                consistency = true;
                break;

            case "--redundancy":
                redundancy = true;
                break;

            case "--all":
                completeness = true;
                consistency = true;
                redundancy = true;
                break;

            case "--debug":
                Util.enable_debug = true;
                break;

            default:
                if (arg.startsWith("-"))
                    Util.warn("unrecognised flag: '"+arg+"': ignoring");
                else
                    policies.add(arg);
                break;
            }
        }

        final Lint program = new Lint(completeness, consistency, redundancy, policies);
    }

    public static void main_help()
    {
        System.out.println("AppPAL Lint");
        System.out.println(" - checks AppPAL policies for various properties.");
        System.out.println("");
        System.out.println("Usage: java -jar Lint.jar flag [policies...]");
        System.out.println("");
        System.out.println("Flags:");
        System.out.println("  --completeness: check policies for completeness");
        System.out.println("  --consistency:  check policies for consistency");
        System.out.println("  --redundancy:   check policies for redundancy");
        System.out.println("  --all:          check policies for all of the above");
        System.out.println("  --debug:        enable debugging messages");
    }
}