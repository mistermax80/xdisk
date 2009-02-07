package xdisk;

import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.database.FileController;

public class DataTableVirtualFile {

	private DataModel fileModel;
		
	public DataModel getVFile() {
        try {
        	String query = null;
			fileModel = new ListDataModel((List<File>)FileController.search(query));
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
        return fileModel;
    }
}
