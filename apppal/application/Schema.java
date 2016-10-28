package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.schema.Graph;
import apppal.schema.Obligations;
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
        System.out.println("    -h --help   Show this message");
        System.out.println("    -D --debug  Enable debug messages");
        System.out.println("    -g --graph  Show a graph of the policy");
        System.out.println("    -m --must   Extract a policy of obligations");

    }

    public static void main(final String args[])
    {
        boolean flag_graph = false;
        boolean flag_must = false;
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

                default:
                    files.add(arg);
                    break;
            }
        }

        if (files.isEmpty() || !(flag_graph||flag_must))
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
            }
        }
        catch (IOException e) {
            Util.error("an error occurred: "+e); 
        }
    }
}
