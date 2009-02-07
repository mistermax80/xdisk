package xdisk;

/**
 * Risorsa virtuale di tipo file.
 * @author biio
 * @version 31/1/2009
 */
public class VirtualFile implements VirtualResource 
{
	private String filename;
	private String extension;
	private String description;
	private String owner;
	private String tags;
	private long size;
	private String mime;
	private String path;
	

	@Override
	public boolean isDirectory() 
	{
		return false;
	}

	@Override
	public boolean isFile() 
	{
		return true;
	}

	/**
	 * Ritorna il nome del file
	 * @return il nome del file
	 */
	public String getFilename() 
	{
		return filename;
	}

	/**
	 * Imposta il nome del file
	 * @param filename il nome del file
	 */
	public void setFilename(String filename) 
	{
		this.filename = filename;
	}

	/**
	 * Ritorna l'estensione del file 
	 * @return l'estensione del file
	 */
	public String getExtension() 
	{
		return extension;
	}

	/**
	 * Imposta l'estensione del file
	 * @param extension l'estensione del file
	 */
	public void setExtension(String extension) 
	{
		this.extension = extension;
	}

	/**
	 * Ritorna la descrizione del file
	 * @return la descrizione del file
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * Imposta la descrizione del file 
	 * @param description la descrizione del file
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * Ritorna il proprietario del file (colui che ha fatto il primo inserimento
	 * @return il proprietario del file
	 */
	public String getOwner() 
	{
		return owner;
	}

	/**
	 * Imposta il proprietario del file
	 * @param owner il proprietario del file
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * Ritorna i tags del file
	 * @return i tags del file
	 */
	public String getTags() 
	{
		return tags;
	}

	/**
	 * Imposta i tags del file
	 * @param tags i tags del file
	 */
	public void setTags(String tags) 
	{
		this.tags = tags;
	}

	/**
	 * Ritorna la dimensione del file in byte
	 * @return la dimensione del file in byte 
	 */
	public long getSize() 
	{
		return size;
	}

	/**
	 * Imposta la dimensione del file in byte
	 * @param size la dimensione del file in byte
	 */
	public void setSize(long size) 
	{
		this.size = size;
	}

	/**
	 * Ritorna il tipo mime del file 
	 * @return il tipo mime del file
	 */
	public String getMime() 
	{
		return mime;
	}

	/**
	 * Imposta il tipo mime del file
	 * @param mime il tipo mime del file
	 */
	public void setMime(String mime) 
	{
		this.mime = mime;
	}

	/**
	 * Ritorna il path del file
	 * @return il path del file
	 */
	public String getPath() 
	{
		return path;
	}

	/**
	 * Imposta il path del file
	 * @param path il path del file
	 */
	public void setPath(String path) 
	{
		this.path = path;
	}

	public String toString() {
		String ret="\n*******"+this.getClass()+
			"\nfilename: "+filename+
			"\nextension: "+extension+
			"\ndescription: "+description+
			"\nowner: "+owner+
			"\ntags: "+tags+
			"\nsize: "+size+
			"\nmime: "+mime+
			"\npath: "+path;
		return ret+"\n***********************";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((extension == null) ? 0 : extension.hashCode());
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((mime == null) ? 0 : mime.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualFile other = (VirtualFile) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (mime == null) {
			if (other.mime != null)
				return false;
		} else if (!mime.equals(other.mime))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (size != other.size)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}
	
	
	
	

}
