package xdisk.persistence.database;

import xdisk.persistence.Disk;

public class DiskController {

	public static void load(Disk disk) throws PersistenceException{DiskDAO.load(disk);}

	public static void insert(Disk disk) throws PersistenceException{DiskDAO.insert(disk);}

	public static void delete(Disk disk) throws PersistenceException{DiskDAO.delete(disk);}

	public static void update(Disk disk) throws PersistenceException{DiskDAO.update(disk);}
}
