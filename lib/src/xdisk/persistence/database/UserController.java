package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.exception.UsernameLoginException;
import xdisk.exception.PasswordLoginException;
import xdisk.persistence.User;

public class UserController {
	private UserController(){}

    public static void load(User user) throws PersistenceException{UserDAO.load(user);}
    
    public static void insert(User user) throws PersistenceException{UserDAO.insert(user);}
    
    public static void delete(User user) throws PersistenceException{UserDAO.delete(user);}
    
    public static void update(User user) throws PersistenceException{UserDAO.update(user);}
    
    /**
     * rimuove tutti gli elementidi tipo User dalla persistenza
     * @return il numero di elementi rimossi
     * @throws PersistenceException
     */
	public static int removeAll() throws PersistenceException
	{
		return UserDCS.removeAll();
	}

	/**
	 * 
	 * @return tutti gli elementi di tipo User presenti nella pesistenza
	 * @throws PersistenceException
	 */
	public static Collection<User> getAll() throws PersistenceException{
		return UserDCS.getAll();
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @return  l'oggetto user della persistenza che ha come username il campo
	 * username e la password corrispondente a password
	 * @throws PersistenceException
	 * @throws LoginException
	 */
	public static User login(String username, String password) throws PersistenceException, UsernameLoginException, PasswordLoginException{
		User user = UserDCS.getUserByUsername(username);
		if (user == null)
			throw new UsernameLoginException();
		if (!UserController.checkPassword(user,password))
			throw new PasswordLoginException();
		return user;
	}

	public static boolean checkPassword(User user, String password) {
		return user.getPassword().equals(password);
	}
}










