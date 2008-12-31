package xdisk;

import xdisk.exception.PasswordLoginException;
import xdisk.exception.PersistenceException;
import xdisk.exception.UsernameLoginException;
import xdisk.persistence.database.UserController;

public class UserBean {

	private String username;
	private String password;
	private boolean error;
	private String msgError;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
			UserController.login(username, password);
			ret = "login";
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
		return ret;
	}
}