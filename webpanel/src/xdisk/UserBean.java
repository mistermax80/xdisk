package xdisk;

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
	
	public String selectPage(){
		String ret = "";
		error=true;
		try {
			System.out.println("Controllo login!");
			user=UserController.login(this.username, this.password);
			if(user.getAdmin())
				ret = "loginAdmin";
			else
				ret = "loginClient";
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