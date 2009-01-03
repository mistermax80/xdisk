package xdisk.persistence;

public class Ownership {

	private int file;
	private String user;
	
	public Ownership(int file, String user) {
		super();
		this.file = file;
		this.user = user;
	}

	public Ownership() {
		super();
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
	
	public String toString(){
    	String res = "\n*****" +this.getClass()+"*****"+
    				"\nfile:"+file+
    				"\nuser:"+user+
    				"\n*******************";
    	return res;
    }	
}
