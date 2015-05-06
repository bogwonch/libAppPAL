package apppal.logic.evaluation;

import java.util.NoSuchElementException;

import apppal.logic.language.Assertion;
import apppal.logic.language.D;

/**
 * Links a query and a delegation depth to a proof.
 */
public class Result
{
  private boolean finished;
  private Proof proof;
  public final Assertion query;
  public final D d;

  public Result(Assertion query, D d)
  {
    this.query = query;
    this.d = d;
    this.finished = false;
    this.proof = null;
  }

  public String toString()
  {
    if (! this.finished)
      return "[?|"+this.query+"]";
    else
    {
      if (this.proof.isKnown())
        return "[T|"+this.query+"]";
      else
        return "[F|"+this.query+"]";
    }
  }

  public Result(Assertion query, D d, Proof proof)
  {
    this.query = query;
    this.d = d;
    this.finished = true;
    this.proof = proof;
  }

  public boolean isFinished() { return this.finished; }
  public boolean needsEval() { return ! this.isFinished(); }

  public boolean answers(Assertion query, D d)
  {
    if (! d.isAtLeast(this.d)) return false;
    if (query.unify(this.query).hasFailed()) return false;
    return true;
  }

  public Proof getProof() throws NoSuchElementException
  {
    if (this.isFinished() && this.proof != null)
      return this.proof;

    throw new NoSuchElementException("no proof available");
  }

  public void provenBy(Proof proof) throws IllegalArgumentException
  {
    if (this.needsEval())
    {
      this.proof = proof;
      this.finished = true;
    }
    else
      throw new IllegalArgumentException("assertion is already proven");
  }

  public boolean isProven()
  {
    return (this.proof != null && this.proof.isKnown());
  }
}
