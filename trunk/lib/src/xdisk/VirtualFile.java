package xdisk;

/**
 * Risorsa virtuale di tipo file.
 * @author biio
 * @version 31/1/2009
 */
public class VirtualFile implements VirtualResource 
{

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

}
