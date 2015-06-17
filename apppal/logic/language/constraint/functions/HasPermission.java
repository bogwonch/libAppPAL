package apppal.logic.language.constraint.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.Bool;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Fail;

/**
 * Check if a permission is present using a local server.
 */
public class HasPermission implements ConstraintFunction
{
  @Override
  public int arity() { return 2; }

  @Override
  public CE eval(List<CE> args)
  {
    BufferedReader in = null;
    try
    {
      /* System.err.println("[?] Evaluating 'hasPermission'"); */
      final String app = args.get(0).toString().replace("\"","");
      final String perm = args.get(1).toString().replace("\"","");
      /* System.err.println("[?]   arg[0]: "+app); */
      /* System.err.println("[?]   arg[1]: "+perm); */

      // Check we are dealing with an app
      if (! app.startsWith("apk://"))
      {
        /* System.err.println("[?]   not an app! Failing."); */
        return new Fail();
      }

      // Path to app permissions
      final File permissions = new File("Apps/"+app.replace("apk://", "")+".permissions");
      /* System.err.println("[?] reading '"+permissions+"'"); */

      // Normally if the file doesn't exist we'll want to extract the
      // permissions... in this case we don't care as we'll have them all
      if (! permissions.exists())
      {
        /* System.err.println("[?]   permissions file doesn't exist!  Failing."); */
        return new Fail();
      }

      in = new BufferedReader(new FileReader(permissions));
      String line;
      while ((line = in.readLine()) != null)
      { if (line.contains(perm))
        { in.close();
          /* System.err.println("[?] ...it was true!"); */
          return new Bool(true);
        }
      }
      in.close();
      /* System.err.println("[?] ...it was false!"); */
      return new Bool(false);
    }
    catch (Exception e)
    {
      try { if (in != null) in.close(); } catch(java.io.IOException err) {} // Already in failure code
      
      return new Fail();
    }
  }
}
