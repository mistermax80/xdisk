package xdisk.persistence.database;


import java.util.Collection;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Request;

public class RequestController {

	private RequestController() {
		super();
	}

	public static void load(Request object) throws PersistenceException{RequestDAO.load(object);}

	public static void insert(Request object) throws PersistenceException{RequestDAO.insert(object);}

	public static void delete(Request object) throws PersistenceException{RequestDAO.delete(object);}

	public static void update(Request object) throws PersistenceException{RequestDAO.update(object);}

	public static int removeAll() throws PersistenceException{
		return RequestDCS.removeAll();
	}

	public static Collection<Request> getAll() throws PersistenceException{
		return RequestDCS.getAll();
	}

	public static String check(String ticket) throws PersistenceException {
		return RequestDCS.check(ticket);
	}

}
