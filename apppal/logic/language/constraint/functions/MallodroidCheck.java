package apppal.logic.language.constraint.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import apppal.logic.evaluation.Proof;
import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.Bool;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Fail;

/**
 */
public class MallodroidCheck implements ConstraintFunction
{
  @Override
  public int arity()
  {
    return 1;
  }

  @Override
  public CE eval(List<CE> args)
  {
    BufferedReader in = null;
    try
    {
      final String app = args.get(0).toString().replace("\"", "");

      if (!app.startsWith("apk://"))
        return new Fail();

      final String name = app.substring(6);

      final URL check = new URL("http://localhost:4567/issues?apk="+name);
      final URLConnection conn = check.openConnection();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      final String value = in.readLine();
      in.close();
      in = null;

      if (value.equals("YES")) return new Bool(true);
      else if (value.equals("NO")) return new Bool(false);
      else return new Fail();
    }
    catch (Exception e)
    {
      return new Fail();
    }
    finally
    {
      if (in != null)
        try { in.close(); } catch (java.io.IOException e) {}
    }
  }
}
