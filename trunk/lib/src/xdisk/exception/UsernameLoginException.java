package xdisk.exception;



/**
 * @author Massimo Gigli
 * Questa classe definisce le eccezioni che sono gestite
 * per le operazioni di login
 *
 */
public class UsernameLoginException extends LoginException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameLoginException() {
		super("Utente non presente nel sistema!");
	}	
}
