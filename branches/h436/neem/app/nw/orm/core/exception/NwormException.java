package nw.orm.core.exception;

/**
 * Basic exception for nworm
 * @author Ogwara O. Rowland
 *
 */
public class NwormException extends Exception {

	private static final long serialVersionUID = 4162504831974227015L;

	public NwormException() {
		super("nw.orm Exception");
	}

	/**
	 * Constructor
	 * @param msg message to display
	 */
	public NwormException(String msg) {
		this(msg, null);
	}

	/**
	 *
	 * @param msg message to display
	 * @param cause exception
	 */
	public NwormException(String msg, Throwable cause){
		super(msg, cause);
	}

}
