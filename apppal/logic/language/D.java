package apppal.logic.language;


/**
 * Delegation Depth
 */
public enum D
{
  ZERO, INF;

  public boolean isAtLeast(D d)
  {
    switch (this)
    {
      case ZERO:
        return d.equals(ZERO);
      case INF:
        return true;

      default:
        return false; // Unreachable
    }
  }

  @Override
  public String toString()
  {
    switch (this)
    {
      case ZERO:
        return "0";
      case INF:
        return "inf";

      default:
        throw new IllegalArgumentException("illegal value of D");
    }
  }
}
