package apppal.application;

import apppal.Util;
import apppal.logic.evaluation.AC;
import apppal.logic.language.Assertion;
import apppal.logic.language.CanSay;
import apppal.logic.language.Constant;
import apppal.logic.language.E;
import apppal.logic.language.EKind;
import apppal.logic.language.Predicate;
import apppal.logic.language.Variable;
import apppal.schema.Graph;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Schema 
{
    public static void main(final String args[])
    {
        for (final String arg : args)
            {
                try { 
                    final Graph s = new Graph(arg); 
                    s.printGraph(); 
                }
                catch (IOException e) {return;}
            }
    }
}
