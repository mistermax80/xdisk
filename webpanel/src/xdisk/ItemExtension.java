package xdisk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;
import xdisk.persistence.database.ExtensionController;

public class ItemExtension {

	private List options;

	public ItemExtension() 
	{
		options = new ArrayList();
		SelectItem option = null;
		LinkedList<Extension> extensions = new LinkedList<Extension>();
		try {
			extensions.addAll(ExtensionController.getAll());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<extensions.size();i++){
			String name = extensions.get(i).getName();
			option = new SelectItem(name, name, "estensione:"+name, false);
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
