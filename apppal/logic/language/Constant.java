package apppal.logic.language;

import java.io.IOException;

/**
 * Constant Entity
 */
public class Constant extends E
{
  public Constant(String name)
  {
    super(name, EKind.CONSTANT);
  }

  public String toString()
  {
    return "\"" + this.name + "\"";
  }

  public static Constant parse(String str) throws IOException
  {
    E e = E.parse(str);
    if (e instanceof Constant)
      return (Constant) e;
    else
      throw new IOException("parsed something else when parsing a constant");
  }
}
