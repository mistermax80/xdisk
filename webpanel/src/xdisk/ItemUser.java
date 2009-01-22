package xdisk;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import xdisk.exception.PersistenceException;
import xdisk.persistence.User;
import xdisk.persistence.database.UserController;

public class ItemUser {

	private List options;

	public ItemUser() 
	{
		options = new ArrayList();
		SelectItem option = null;
		LinkedList<User> users = new LinkedList<User>();
		try {
			users.addAll(UserController.getAll());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<users.size();i++){
			String value = users.get(i).getUsername();
			String name = users.get(i).getName()+"-"+String.valueOf(users.get(i).getUsername());
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
