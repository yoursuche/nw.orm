package nw.orm.core.exception;

import nw.orm.annotations.NwormTransaction;

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

	@Override
	@NwormTransaction
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
