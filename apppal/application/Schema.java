package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.schema.Graph;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
    }

    public static void main(final String args[])
    {
        boolean flag_graph = false;
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

                case "-g": case "--graph":
                    flag_graph = true;
                    break;

            default:
                    files.add(arg);
                    break;
            }
        }

        if (files.isEmpty() || !(flag_graph))
        {
            help();
            return;
        }
        
        for (final String file : files)
        {
            Util.debug("using "+file);
            if (flag_graph)
            {
                Util.debug("plotting graph");
                try { 
                    final Graph s = new Graph(file); 
                    s.printGraph(); 
                }
                catch (IOException e) {
                    Util.error("failed to print graph: "+e); 
                }
            }
        }
    }
}
