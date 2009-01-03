package xdisk.persistence.database;

import java.util.Collection;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Download;

public class DownloadController {

	public static void load(Download download) throws PersistenceException{DownloadDAO.load(download);}

	public static void insert(Download download) throws PersistenceException{DownloadDAO.insert(download);}

	public static void delete(Download download) throws PersistenceException{DownloadDAO.delete(download);}

	public static void update(Download download) throws PersistenceException{DownloadDAO.update(download);}
	
	public static int removeAll() throws PersistenceException{
		return DownloadDCS.removeAll();
	}

	public static Collection<Download> getAll() throws PersistenceException{
		return DownloadDCS.getAll();
	}
}
