package xdisk;

/**
 * Risorsa virtuale di tipo directory
 * @author biio
 * @version 31/1/2009
 */
public class VirtualFolder implements VirtualResource 
{
	private String path;
	
	@Override
	public boolean isDirectory() 
	{
		return true;
	}

	@Override
	public boolean isFile() 
	{
		return false;
	}

	/**
	 * Ritorna il path della directory
	 * @return il path della directory
	 */
	public String getPath() 
	{
		return path;
	}

	/**
	 * Imposta il path della directory
	 * @param path il path della directory
	 */
	public void setPath(String path) 
	{
		this.path = path;
	}
	
	public String toString()
	{
		return this.getPath();
	}

	
	

}
