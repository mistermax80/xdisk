package xdisk.persistence.database;

public class PersistenceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7722423773888974482L;

	public PersistenceException() {
		super();
	}

	public PersistenceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PersistenceException(String arg0) {
		super(arg0);
	}

	public PersistenceException(Throwable arg0) {
		super(arg0);
	}

}
