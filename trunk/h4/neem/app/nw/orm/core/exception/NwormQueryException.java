package nw.orm.core.exception;

public class NwormQueryException extends IllegalArgumentException{

	/**
	 *
	 */
	private static final long serialVersionUID = 5906233570303969615L;

	public NwormQueryException() {
		super("nw.orm Exception executing query");
	}

	public NwormQueryException(String msg, Throwable cause) {
		super("nw.orm Exception executing query " + msg, cause);
	}

}
