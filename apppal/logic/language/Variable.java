package apppal.logic.language;

import java.io.IOException;
import apppal.Util;

/**
 * A variable instance of an entity
 */
public class Variable extends E
{
  public final String type;
  public boolean typeObliged;

  public Variable(String name)
  {
    super(name, EKind.VARIABLE);
    type = null;
    typeObliged = true;
  }

  public Variable(String name, String type)
  {
    super(name, EKind.VARIABLE);
    this.type = type;
    typeObliged = false;
  }

  public String toString()
  {
    // For test purposes if something isn't part of an assertion don't show it.
    //final String name = this.name+"."+this.scope;
    StringBuilder out = new StringBuilder();
    final String name = this.name;
    if (this.type == null || this.typeObliged)
      out.append(name);
    else
    {
      out.append(this.type);
      out.append(":");
      out.append(name);
    }

    if (Util.enable_debug)
    {
      out.append(".");
      out.append(this.scope);
    }

    return out.toString();
  }

  public static Variable parse(String str) throws IOException
  {
    E e = E.parse(str);
    if (e instanceof Variable)
      return (Variable) e;
    else
      throw new IOException("parsed something else when parsing a variable");
  }

  public String obligeTyping()
  {
    if (! this.typeObliged && this.type != null)
    {
      final String type = this.type;
      this.typeObliged = true;
      return this.name + " is"+type;
    }
    else return null;
  }

  // Fresh variables! Get yer secret fresh variables here!
  private static int fresh = 0;
  public static Variable getFresh() { return new Variable("_"+fresh++); }
}
