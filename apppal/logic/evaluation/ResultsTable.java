package apppal.logic.evaluation;

import java.util.LinkedList;
import java.util.List;

import apppal.logic.language.Assertion;
import apppal.logic.language.D;

/**
 * Holds previously discovered results.
 */
public class ResultsTable
{
  private List<Result> table;

  public ResultsTable()
  {
    this.table = new LinkedList<>();
  }

  /**
   * Check if we have a result for a query at a depth
   *
   * @param q query
   * @param d depth
   * @return boolean
   */
  public boolean has(Assertion q, D d)
  {
    for (Result r : this.table)
      if (r.answers(q, d)) return true;
    return false;
  }

  public boolean has(Result r) { return this.has(r.query, r.d); }

  /**
   * Get a result from the table
   *
   * @param q query
   * @param d depth
   * @return the matched result
   * @throws IndexOutOfBoundsException
   */
  public Result get(Assertion q, D d) throws IndexOutOfBoundsException
  {
    for (Result r : this.table)
      if (r.answers(q, d)) return r;

    throw new IndexOutOfBoundsException("no result found for: " + q + " at " + d);
  }

  /**
   * Add a result if not already in the table
   *
   * @param r result to add
   */
  public void add(Result r)
  {
    if (!this.has(r))
      this.table.add(r);
  }

  /**
   * Update a result in the table
   * @param r result to update
   */
  public void update(Result r)
  {
    if (!this.has(r)) this.add(r);
    else
    {
      this.table.remove(this.get(r.query, r.d));
      this.table.add(r);
    }
  }

  public boolean isKnownProven(Assertion q, D d)
  {
    if (this.has(q,d)) return this.get(q,d).getProof().isKnown();
    else return false;
  }

  public boolean isKnownNotProven(Assertion q, D d)
  {
    if (this.has(q,d)) return this.get(q,d).getProof().isNotKnown();
    else return false;
  }

  public void markUnfinished(Assertion q, D d)
  {
    this.add(new Result(q, d));
  }
}
