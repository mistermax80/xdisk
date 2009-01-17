package xdisk.persistence.database;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;

public class Test {

	public static void main(String[] args) throws PersistenceException{
		Folder folr = new Folder(12,"nnn",1234,23,true);
		FolderDAO.insert(folr);
	}
}
