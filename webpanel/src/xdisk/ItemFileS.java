package xdisk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;
import xdisk.persistence.database.FileController;
import xdisk.persistence.database.FolderController;

public class ItemFileS {

	private List options;

	public ItemFileS() 
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
			String path = null;
			String name = file.get(i).getCode();
			String label = file.get(i).getName();
			Folder folder = new Folder();
			folder.setCodice(file.get(i).getParent());
			try {
				FolderController.load(folder);
				path=FolderController.getPath(folder);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			option = new SelectItem(name, path+label+"."+file.get(i).getExtension());
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
