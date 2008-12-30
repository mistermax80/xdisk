package xdisk.persistence;

/**
 * @author massimo
 *
 */
public class Disk {
	
	private String name;
	private int dimension;
	private String useridAdmin;
	private int idRoot;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public String getAdmin() {
		return useridAdmin;
	}
	public void setAdmin(String admin) {
		this.useridAdmin = admin;
	}
	public int getIdRoot() {
		return idRoot;
	}
	public void setIdRoot(int idRoot) {
		this.idRoot = idRoot;
	}
	
    public String toString(){
    	String res = "\n*****" +this.getClass()+"*****"+
    				"\nname:"+name+
    				"\nadmin:"+useridAdmin+
    				"\nroot:"+idRoot+
    				"\ndimension:"+dimension+
    				"\n*******************";
    	return res;
    }
}
