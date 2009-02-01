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
	private int size;
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
	public int getSize() 
	{
		return size;
	}

	/**
	 * Imposta la dimensione del file in byte
	 * @param size la dimensione del file in byte
	 */
	public void setSize(int size) 
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
	
	

}
