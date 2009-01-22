package xdisk;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

public class ActionGeneric {

	public void logout(ActionEvent arg0) throws AbortProcessingException {
		ExternalContext extCon = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession ses = (HttpSession) extCon.getSession(false);
		ses.invalidate();
	}
}
