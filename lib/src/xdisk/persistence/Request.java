package xdisk.persistence;

public class Request {

	private String ticketid;
	private String file;
	private String userid;

	public Request() {
		super();
	}

	public Request(String ticketid, String file, String userid) {
		super();
		this.ticketid=ticketid;
		this.file=file;
		this.userid=userid;
	}


	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid=ticketid;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file=file;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid=userid;
	}

	public String toString(){
		String ret="\n*****" +this.getClass()+"*****"+
		"\nticketid: "+this.ticketid+
		"\nfile: "+this.file+
		"\nuserid: "+this.userid;
		return ret+"\n******************";
	}
}
