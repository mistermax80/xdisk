package xdisk.persistence;

public class File {
	
	private int code;
	private String name;
	private int dimension;
	private int folder;
	private String author;
	private String loaderUserid;
	
	public File() {
		super();
	}

	public File(int code, String name, int dimension, int folder,
			String author, String loaderUserid) {
		super();
		this.code = code;
		this.name = name;
		this.dimension = dimension;
		this.folder = folder;
		this.author = author;
		this.loaderUserid = loaderUserid;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

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

	public int getFolder() {
		return folder;
	}

	public void setFolder(int folder) {
		this.folder = folder;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLoaderUserid() {
		return loaderUserid;
	}

	public void setLoaderUserid(String loaderUserid) {
		this.loaderUserid = loaderUserid;
	}
	
	public String toString(){
    	String res = "\n*****" +this.getClass()+"*****"+
    				"\ncode:"+code+
    				"\nname:"+name+
    				"\nfolder:"+folder+
    				"\ndimension:"+dimension+
    				"\nauthor:"+author+
    				"\nloaderUserid:"+loaderUserid+
    				"\n*******************";
    	return res;
    }	
}
