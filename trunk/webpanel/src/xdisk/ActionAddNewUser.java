package xdisk;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.swing.JOptionPane;

import xdisk.exception.PersistenceException;
import xdisk.persistence.User;
import xdisk.persistence.database.UserController;

public class ActionAddNewUser{

	private boolean error = true;


	public ActionAddNewUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String selectPage(){
		String page = "";
		if(!error)
			page="return";
		return page;
	}
	public void saveUser(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		User user = (User)session.get("newUserBean");
		JOptionPane.showMessageDialog(null,user);

		try {
			UserController.insert(user);
			error=false;
			JOptionPane.showMessageDialog(null,"Utente aggiunto!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Utente non inserito!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
}