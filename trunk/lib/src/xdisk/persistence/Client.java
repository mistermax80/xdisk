package xdisk.persistence;

public class Client extends User{

	private String userid;
	private String idSession;
	private String ipAddress;
	private int portNumber;
	private String connType;

	public Client(String userid, String idSession, String ipAddress, int portNumber, String connType) {
		super();
		this.userid = userid;
		this.idSession = idSession;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.connType = connType;
	}
	
	public Client() {
		super();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}
	
	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public String toString(){
		String res = "\n*****" +this.getClass()+"*****"+
		"\nuserId:"+userid+
		"\nidSession:"+idSession+
		"\nipAddress:"+ipAddress+
		"\nportNumber:"+portNumber+
		"\nconnType:"+connType+
		"\n*******************";
		return res;
	}
}
