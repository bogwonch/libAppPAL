package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.schema.Graph;
import apppal.schema.Obligations;
import apppal.schema.Describe;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.io.FileInputStream;

public class Schema 
{
    public static void help()
    {
        System.out.println("Schema.jar:  AppPAL schema tool for BYOD");
        System.out.println("");
        System.out.println("Usage: java -jar Schema.jar [flags] [files]");
        System.out.println("");
        System.out.println("Flags:");
        System.out.println("    -h --help      Show this message");
        System.out.println("    -D --debug     Enable debug messages");
        System.out.println("    -d --describe  Show a count of what predicates are used");
        System.out.println("    -g --graph     Show a graph of the policy");
        System.out.println("    -m --must      Extract a policy of obligations");

    }

    public static void main(final String args[])
    {
        boolean flag_graph = false;
        boolean flag_must = false;
        boolean flag_describe = false;
        final List<String> files = new LinkedList<>();
        for (final String arg : args)
        {
            switch (arg) {
                case "-h": case "--help":
                    help();
                    return;

                case "-D": case "--debug":
                    Util.enable_debug = true;
                    Util.debug("debugging enabled");
                    break;

                case "-m": case "--must":
                    flag_must = true;
                    break;

                case "-g": case "--graph":
                    flag_graph = true;
                    break;

                case "-d": case "--describe":
                    flag_describe = true;
                    break;

                default:
                    files.add(arg);
                    break;
            }
        }

        if (files.isEmpty() || !(flag_graph||flag_must||flag_describe))
        {
            help();
            return;
        }

        try { 
            for (final String file : files)
            {
                AC ac;
                try { ac = new AC(new FileInputStream(file)); }
                catch (IOException err)
                {
                    Util.error("couldn't load "+file+": "+err);
                    throw(err);
                }

                Util.debug("loaded "+file);
                if (flag_graph)
                {
                    Util.debug("plotting graph");
                    final Graph s = new Graph(ac); 
                    s.printGraph(); 
                }
                if (flag_must)
                {
                    Util.debug("extracting musts");
                    final Obligations s = new Obligations(ac);
                }
                if (flag_describe)
                {
                    Util.debug("describing predicates");
                    final Describe d = new Describe(ac);
                    System.out.println(d.toCSVString());
                }
            }
        }
        catch (IOException e) {
            Util.error("an error occurred: "+e); 
        }
    }
}
