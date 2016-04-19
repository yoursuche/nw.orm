package nw.orm.core.exception;

/**
 * 
 * @author Ogwara O. Rowland
 *
 */
public class NwormException extends Exception {

	private static final long serialVersionUID = 4162504831974227015L;

	public NwormException() {
		super("nw.orm Exception");
	}

	public NwormException(String msg) {
		this(msg, null);
	}
	
	public NwormException(String msg, Throwable cause){
		super(msg, cause);
	}

}
