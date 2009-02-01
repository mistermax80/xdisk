package xdisk.client.core;

import java.util.ArrayList;


/**
 * Classe singleton per la gestione dei dischi del client.
 *  
 * @author biio
 * @version 31/1/2009
 */
public class VirtualDiskManager 
{
	private VirtualDiskManager instance = null;
	private ArrayList<VirtualDisk> virtualDisks;
	
	
	private VirtualDiskManager() 
	{
		virtualDisks = new ArrayList<VirtualDisk>();
		
	}
	
	public VirtualDiskManager getInstance()
	{
		if (instance == null)
			instance = new VirtualDiskManager();
		
		return instance;
	}
	
	public ArrayList<VirtualDisk> getVirtualDisk()
	{
		return virtualDisks;
	}
	
}
