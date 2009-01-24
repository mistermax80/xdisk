package xdisk;

import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;
import xdisk.persistence.User;
import xdisk.persistence.database.ExtensionController;
import xdisk.persistence.database.UserController;

public class DataTable {

	private DataModel users;
	private DataModel extension;
	
	public DataModel getUsers() {
	        try {
				users = new ListDataModel((List<User>) UserController.getAll());
				System.out.println((List<User>) UserController.getAll());
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
	        return users;
	    }
	
	public DataModel getExtension() {
        try {
			extension = new ListDataModel((List<Extension>) ExtensionController.getAll());
			System.out.println((List<Extension>) ExtensionController.getAll());
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
        return extension;
    }
}
