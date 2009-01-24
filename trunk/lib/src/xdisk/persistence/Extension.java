package xdisk.persistence;

public class Extension {

	private String name;
	private boolean allow;

	public Extension() {
		super();
	}

	public Extension(boolean allow, String name) {
		super();
		this.allow=allow;
		this.name=name;
	}


	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow=allow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String toString(){
		String ret="*****" +this.getClass()+"*****"+
		"\nallow: "+this.allow+
		"\nname: "+this.name;
		return ret+"******************";
	}
}
