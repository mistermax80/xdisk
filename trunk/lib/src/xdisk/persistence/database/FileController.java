package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;

public class FileController {

	public static void load(File file) throws PersistenceException{FileDAO.load(file);}

	public static void insert(File file) throws PersistenceException{FileDAO.insert(file);}

	public static void delete(File file) throws PersistenceException{FileDAO.delete(file);}

	public static void update(File file) throws PersistenceException{FileDAO.update(file);}
	
	public static int removeAll() throws PersistenceException{
		return FileDCS.removeAll();
	}

	public static Collection<File> getAll() throws PersistenceException{
		return FileDCS.getAll();
	}
	
	public static Collection<File> getFile(Folder folder) throws PersistenceException{
		return FileDCS.getFile(folder);
	}
}
