package xdisk.exception;



/**
 * @author Massimo Gigli
 * Questa classe definisce le eccezioni che sono gestite
 * per le operazioni di login
 *
 */
public class PasswordLoginException extends LoginException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordLoginException() {
		super("La password non è corretta!");
	}	
}
