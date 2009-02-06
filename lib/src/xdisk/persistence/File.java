package xdisk.persistence;

public class File {

	private String code;
	private String name;
	private String extension;
	private String description;
	private String tags;
	private long size;
	private String owner;
	private String mime;
	private int parent;

	public File() {
		super();
	}

	public File(String code, String name, String extension, String description, String tags, int size, String owner, String mime, int parent) {
		super();
		this.code=code;
		this.name=name;
		this.extension=extension;
		this.description=description;
		this.tags=tags;
		this.size=size;
		this.owner=owner;
		this.mime=mime;
		this.parent=parent;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code=code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension=extension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description=description;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags=tags;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size=size;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner=owner;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime=mime;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent=parent;
	}

	public String toString(){
		String ret="\n*****" +this.getClass()+"*****"+
		"\ncode: "+this.code+
		"\nname: "+this.name+
		"\nextension: "+this.extension+
		"\ndescription: "+this.description+
		"\ntags: "+this.tags+
		"\nsize: "+this.size+
		"\nowner: "+this.owner+
		"\nmime: "+this.mime+
		"\nparent: "+this.parent;
		return ret+"\n******************";
	}
}
