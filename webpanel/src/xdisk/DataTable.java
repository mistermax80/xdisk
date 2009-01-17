package xdisk;

import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import xdisk.exception.PersistenceException;
import xdisk.persistence.User;
import xdisk.persistence.database.UserController;

public class DataTable {

	private DataModel users;
	public DataModel getUsers() {
	        try {
				users = new ListDataModel((List<User>) UserController.getAll());
				System.out.println((List<User>) UserController.getAll());
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
	        return users;
	    }
}
