package nw.orm.base;

public class REntityManagerException extends Exception {

	private static final long serialVersionUID = 4162504831974227015L;

	public REntityManagerException() {
		super("Entity Manager exception");
	}

	public REntityManagerException(String msg) {
		super(msg);
	}

}
