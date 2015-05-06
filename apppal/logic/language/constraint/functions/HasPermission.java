package apppal.logic.language.constraint.functions;

import java.io.BufferedReader;
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
    try
    {
      String app = args.get(0).toString().replace("\"","");
      String perm = args.get(1).toString().replace("\"","");

      URL url = new URL("http://localhost:8000/has" +
        "?app="+app.toString()+
        "&permission="+perm.toString());

      URLConnection conn = url.openConnection();

      BufferedReader in = new BufferedReader(
        new InputStreamReader(conn.getInputStream()));


      String response;
      while ((response = in.readLine()) != null)
      {
        System.err.println("[!] '" + response + "'");
        if (response.equals("YES")) return new Bool(true);
        if (response.equals("NO")) return new Bool(false);
      }
      return new Fail();
    }
    catch (Exception e)
    {
      return new Fail();
    }
  }
}
