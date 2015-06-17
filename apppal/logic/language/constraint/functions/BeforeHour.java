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
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Check if we're before an hour in the day
 */
public class BeforeHour implements ConstraintFunction
{
  @Override
  public int arity() { return 1; }

  @Override
  public CE eval(List<CE> args)
  {
    BufferedReader in = null;
    try
    {
      final Integer hour = new Integer(args.get(0).toString().replace("\"",""));

      // Is the hour sensible?
      if (hour < 0 || hour >= 24 ) { return new Fail(); }

      // Get current hour
      final Integer now = new Integer(new GregorianCalendar().get(Calendar.HOUR_OF_DAY));

      return new Bool(now < hour);
    }
    catch (Exception e) { return new Fail(); }
  }
}
