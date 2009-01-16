package xdisk;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import xdisk.persistence.User;

public class addUser implements ActionListener {
	
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		try {
			Map<String,Object> sss = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
			UserBean us = (UserBean)sss.get("userBean");
			User user = us.getUser();
			//JOptionPane.showMessageDialog(null,us.getUsername());
			System.out.println("Sono nell'action listener"+user);
			//UserController.insert(user);
			//JOptionPane.showMessageDialog(null,"Aggiunto");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
