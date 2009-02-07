package xdisk;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;
import xdisk.persistence.File;
import xdisk.persistence.User;
import xdisk.persistence.database.ExtensionController;
import xdisk.persistence.database.FileController;
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
	
	private DataModel fileModel;
	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public DataModel getFile() {
        try {
        	System.out.println("=============================="+query);
        	LinkedList<File> f = new LinkedList<File>();
        	f.addAll(FileController.search(query));
        	System.out.println(f);
			fileModel = new ListDataModel((List<File>)FileController.search(query));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
        return fileModel;
    }
}
