package apppal.application;

import apppal.Util;

import java.util.List;
import java.util.LinkedList;

public class Lint
{
    private boolean check_completeness = false;
    private boolean check_consistency = false;
    private boolean check_redundancy = false;

    public Lint()
    {
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

            default:
                if (arg.startsWith("-"))
                    Util.warn("unrecognised flag: '"+arg+"': ignoring");
                else
                    policies.add(arg);
                break;
            }
        }
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
    }
}
