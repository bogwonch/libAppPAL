package apppal.logic.evaluation;

/**
 * Created by bogwonch on 02/03/2015.
 */
public abstract class Proof
{
  public boolean proven;

  public boolean isKnown()
  {
    return this.proven;
  }
  public boolean isNotKnown()
  {
    return ! this.isKnown();
  }

  public String toString()
  {
    return this.showProof(0);
  }

  abstract protected String showProof(int indent);

  public static String getIndent(int indent)
  {
    return new String(new char[indent]).replace("\0", "  "); 
  }
}
