package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Client;

public class ClientController {

	public static void load(Client client) throws PersistenceException{ClientDAO.load(client);}

	public static void insert(Client client) throws PersistenceException{ClientDAO.insert(client);}

	public static void delete(Client client) throws PersistenceException{ClientDAO.delete(client);}

	public static void update(Client client) throws PersistenceException{ClientDAO.update(client);}
	
	public static int removeAll() throws PersistenceException{
		return ClientDCS.removeAll();
	}

	public static Collection<Client> getAll() throws PersistenceException{
		return ClientDCS.getAll();
	}

	public static boolean isPresent(Client client) throws PersistenceException{
		return ClientDCS.isPresent(client);
	}

	public static boolean checkSession(String id, String userid) throws PersistenceException {
		return ClientDCS.checkSession(id, userid);
	}
}
