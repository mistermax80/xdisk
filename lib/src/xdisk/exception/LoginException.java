package xdisk.exception;

/**
 * @author Massimo Gigli
 * Questa classe definisce le eccezioni che sono gestite
 * per le operazioni di login
 *
 */
public class LoginException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException(String message) {
		super(message);
	}

	public LoginException() {
		super();
	}

	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginException(Throwable cause) {
		super(cause);
	}	
	
	
}
