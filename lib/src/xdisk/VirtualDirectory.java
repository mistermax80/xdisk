package xdisk;

/**
 * Risorsa virtuale di tipo directory
 * @author biio
 * @version 31/1/2009
 */
public class VirtualDirectory implements VirtualResource 
{

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

}
