package xdisk.persistence.database;

public class PersistenceException extends Exception{

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
