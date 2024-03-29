package xdisk;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.swing.JOptionPane;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;
import xdisk.persistence.Folder;
import xdisk.persistence.User;
import xdisk.persistence.database.ExtensionController;
import xdisk.persistence.database.FolderController;
import xdisk.persistence.database.UserController;

public class ActionAddNew{

	private boolean error = true;


	public ActionAddNew() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String selectPage(){
		String page = "";
		if(!error)
			page="return";
		return page;
	}
	
	public void updateUser(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		System.out.println(session.values());
		User user = ((UserBean)session.get("userBean")).getUser();
		//JOptionPane.showMessageDialog(null,user);

		try {
			UserController.update(user);
			error=false;
			JOptionPane.showMessageDialog(null,"Utente Modificato!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Utente non modifcato!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteUser(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		System.out.println(session.values());
		String username = ((User)session.get("newUserBean")).getUsername();
		User user = new User();
		user.setUsername(username);

		try {
			UserController.load(user);
			//JOptionPane.showMessageDialog(null,user);
			UserController.delete(user);
			error=false;
			JOptionPane.showMessageDialog(null,"Utente Eliminato!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Utente non eliminato!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveFolder(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		Folder folder = (Folder)session.get("folder");

		try {
			int code = FolderController.getLastCode();
			folder.setCodice(code+1);
			//JOptionPane.showMessageDialog(null,folder);
			FolderController.insert(folder);
			error=false;
			JOptionPane.showMessageDialog(null,"Cartella aggiunta!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Cartella non inserita!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void delFolder(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		Folder folder = (Folder)session.get("folder");

		try {
			FolderController.load(folder);
			JOptionPane.showMessageDialog(null,folder);
			FolderController.delete(folder);
			error=false;
			JOptionPane.showMessageDialog(null,"Cartella eliminata!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Cartella non eliminata!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveUser(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		User user = (User)session.get("newUserBean");
		//JOptionPane.showMessageDialog(null,user);

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
	
	public void delExtension(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		Extension extension = (Extension)session.get("extension");

		try {
			//JOptionPane.showMessageDialog(null,extension);
			ExtensionController.delete(extension);
			error=false;
			JOptionPane.showMessageDialog(null,"Estensione eliminata!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Estensione non eliminata!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void saveExtension(ActionEvent arg0) throws AbortProcessingException {

		Map<String,Object> session = FacesContext.getCurrentInstance().getExternalContext().getRequestMap(); 
		System.out.println(session.values());
		Extension extension = (Extension)session.get("extension");

		try {
			//JOptionPane.showMessageDialog(null,extension);
			ExtensionController.insert(extension);
			error=false;
			JOptionPane.showMessageDialog(null,"Estensione aggiunta!");
		} catch (PersistenceException e) {
			e.printStackTrace();
			error  = true;
			JOptionPane.showMessageDialog(null,"Estensione non inserita!","Errore accesso dati!",JOptionPane.ERROR_MESSAGE);
		}
	}
}
