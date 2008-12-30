package xdisk.persistence;

public class User {

	private String username;
	private String name;
	private String password;
	private String email;
	private boolean admin;

	public User() {
		super();
	}

	public User(String username, String name, String password, String email) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String toString(){
		String res = "\n*****" +this.getClass()+"*****"+
						"\nusername:"+username+
						"\nname:"+name+
						"\npassword:"+password+
						"\nemail:"+email+
						"\n*******************";
		return res;
	}
}
