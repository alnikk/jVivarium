/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import fr.utbm.lo43.jvivarium.core.Coordinates;


/**
 * Thrown when only positive coordinates are expected.
 * 
 * @author Guyon
 */

@SuppressWarnings("serial")
public class NegativeSizeException extends Exception
{
	/**
	 * This variable is the bad coordinates. 
	 * Exception due to this coordinates.
	 */
	private Coordinates bad;

	/**
	 * Create an exception related to specified
	 * negative coordinates
	 * 
	 * @param bad negative coordinates related 
	 * 			to the exception
	 */
	public NegativeSizeException(Coordinates bad)
	{
		super();
		this.bad = bad;
	}

	/**
	 * Create NegativeSizeException
	 */
	public NegativeSizeException()
	{
		super();
		this.bad = null;
	}

	/**
	 * Getter for the negative coordinates 
	 * related to the exception
	 * 
	 * @return negative coordinates related 
	 * 			to the exception
	 */
	public Coordinates getNegativeCoordinatesException()
	{
		return this.bad;
	}
}

