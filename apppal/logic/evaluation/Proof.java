package apppal.logic.evaluation;

/**
 * Created by bogwonch on 02/03/2015.
 */
public class Proof
{
  public final boolean proven;
  public Proof(boolean proven) { this.proven = proven; }

  public boolean isKnown() { return this.proven; }
  public boolean isNotKnown() { return ! this.isKnown(); }
}
