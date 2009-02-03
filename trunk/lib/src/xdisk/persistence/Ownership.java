package xdisk.persistence;

public class Ownership {

	private String file;
	private String user;
	
	public Ownership(String file, String user) {
		super();
		this.file = file;
		this.user = user;
	}

	public Ownership() {
		super();
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String toString(){
    	String res = "\n*****" +this.getClass()+"*****"+
    				"\nfile:"+file+
    				"\nuser:"+user+
    				"\n*******************";
    	return res;
    }	
}
