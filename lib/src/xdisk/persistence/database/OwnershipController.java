package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Ownership;

public class OwnershipController {

	public static void load(Ownership ownership) throws PersistenceException{OwnershipDAO.load(ownership);}

	public static void insert(Ownership ownership) throws PersistenceException{OwnershipDAO.insert(ownership);}

	public static void delete(Ownership ownership) throws PersistenceException{OwnershipDAO.delete(ownership);}

	//public static void update(Ownership ownership) throws PersistenceException{OwnershipDAO.update(ownership);}
	
	public static int removeAll() throws PersistenceException{
		return OwnershipDCS.removeAll();
	}

	public static Collection<Ownership> getAll() throws PersistenceException{
		return OwnershipDCS.getAll();
	}
	
	public static Collection<Ownership> getUserByCode(String code) throws PersistenceException{
		return OwnershipDCS.getUserByCode(code);
	}

	public static Collection<Ownership> getUserOnlineByCode(String code) throws PersistenceException{
		return OwnershipDCS.getUserOnlineByCode(code);
	}
}
