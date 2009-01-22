package xdisk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;
import xdisk.persistence.database.FolderController;

public class ItemFolders {

	private List options;

	public ItemFolders() 
	{
		options = new ArrayList();
		SelectItem option = null;
		LinkedList<Folder> folders = new LinkedList<Folder>();
		try {
			folders.addAll(FolderController.getAll());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<folders.size();i++){
			String value = String.valueOf(folders.get(i).getCodice());
			String name = folders.get(i).getNome()+"-"+String.valueOf(folders.get(i).getCodice());
			option = new SelectItem(value, name, "una scelta delle cartelle", false);
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
