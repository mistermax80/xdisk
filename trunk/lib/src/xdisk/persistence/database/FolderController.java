package xdisk.persistence.database;


import java.util.Collection;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;

public class FolderController {

	private FolderController() {
		super();
	}

	public static void load(Folder object) throws PersistenceException{FolderDAO.load(object);}

	public static void insert(Folder object) throws PersistenceException{FolderDAO.insert(object);}

	public static void delete(Folder object) throws PersistenceException{FolderDAO.delete(object);}

	public static void update(Folder object) throws PersistenceException{FolderDAO.update(object);}

	public static int removeAll() throws PersistenceException{
		return FolderDCS.removeAll();
	}

	public static Collection<Folder> getAll() throws PersistenceException{
		return FolderDCS.getAll();
	}
	
	public static Folder getRoot() throws PersistenceException{
		return FolderDCS.getRoot();
	}
	
	public static Collection<Folder> getChilds(Folder folder) throws PersistenceException{
		return FolderDCS.getChilds(folder);
	}

	public static int getLastCode() throws PersistenceException {
		return FolderDCS.getLastCode();
	}

}
