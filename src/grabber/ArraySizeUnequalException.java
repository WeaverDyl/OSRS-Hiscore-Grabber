package grabber;

/**
 * An exception that is thrown when we are trying to compare two arrays whose lengths are not eqwual
 * 
 * @author Dylan Weaver
 *
 */
public class ArraySizeUnequalException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor used to create instances of the exception
	 * 
	 * @param message The message that will be shown upon throwing the exception
	 */
	public ArraySizeUnequalException(String message) {
		super(message);
	}
}
