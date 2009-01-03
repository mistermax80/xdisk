package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Disk;

public class OwnershipController {

	public static void load(Disk disk) throws PersistenceException{DiskDAO.load(disk);}

	public static void insert(Disk disk) throws PersistenceException{DiskDAO.insert(disk);}

	public static void delete(Disk disk) throws PersistenceException{DiskDAO.delete(disk);}

	public static void update(Disk disk) throws PersistenceException{DiskDAO.update(disk);}
	
	public static int removeAll() throws PersistenceException{
		return DiskDCS.removeAll();
	}

	public static Collection<Disk> getAll() throws PersistenceException{
		return DiskDCS.getAll();
	}
}
