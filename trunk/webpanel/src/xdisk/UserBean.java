package xdisk;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import xdisk.exception.PasswordLoginException;
import xdisk.exception.PersistenceException;
import xdisk.exception.UsernameLoginException;
import xdisk.persistence.User;
import xdisk.persistence.database.UserController;

public class UserBean{

	private User user;
	private boolean error;
	private String msgError;
	private String password;
	private String username;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public void saveUser(ActionEvent arg0) throws AbortProcessingException {
		try {
			Map<String,Object> sss = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 
			UserBean us = (UserBean)sss.get("userBean");
			User user = us.getUser();
			System.out.println("Sono nell'action listener"+user);
			UserController.insert(user);
			System.out.println("Salvato nel db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String selectPage(){
		String ret = "";
		error=true;
		try {
			user=UserController.login(this.username, this.password);
			if(true)
				ret = "loginAdmin";
			else
				ret = "loginUser";
			error=false;
		} catch (UsernameLoginException e) {
			msgError="Username non presente nel sistema!!!";
			e.printStackTrace();
		} catch (PasswordLoginException e) {
			msgError="Password errata!!!";
			e.printStackTrace();
		} catch (PersistenceException e) {
			msgError="Problemi con la comunicazione dati!!!";
			e.printStackTrace();
		}	
		System.out.println("Link di UserBean usato nelle index: "+ret);
		return ret;
	}
}