package xdisk.persistence;

import java.sql.Timestamp;

/**
 * @author massimo
 *
 */
public class Download {
	
	private int ticket;
	private int file;
	private String user;
	private java.sql.Timestamp timestamp;
	
	public Download(int ticket, int file, String user, Timestamp timestamp) {
		super();
		this.ticket = ticket;
		this.file = file;
		this.user = user;
		this.timestamp = timestamp;
	}

	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	public int getFile() {
		return file;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String toString(){
    	String res = "\n*****" +this.getClass()+"*****"+
    				"\nticket:"+ticket+
    				"\nfile:"+file+
    				"\nuser:"+user+
    				"\ntimestamp:"+timestamp+
    				"\n*******************";
    	return res;
    }
}
