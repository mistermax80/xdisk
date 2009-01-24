package xdisk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.database.FileController;

public class ItemFile {

	private List options;

	public ItemFile() 
	{
		options = new ArrayList();
		SelectItem option = null;
		LinkedList<File> file = new LinkedList<File>();
		try {
			file.addAll(FileController.getAll());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<file.size();i++){
			String name = String.valueOf(file.get(i).getCode());
			String label = file.get(i).getName();
			option = new SelectItem(name, name+"-"+label, "file:"+name, false);
			options.add(option);
		}
	}

	public void setOptions(List opt)
	{
		options = opt;
	}

	public List getOptions()
	{
		return options;
	}
}
