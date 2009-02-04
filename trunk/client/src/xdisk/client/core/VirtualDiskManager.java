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
	
	public void add(VirtualDisk virtualDisk)
	{
		
	}
	
	public void removeByAddress(String address)
	{
		
	}
	
	public int getNum()
	{
		return 0;
	}
	
	public VirtualDisk get(int index)
	{
		return null;
	}
	
	/**
	 * Salva la configurazione corrente dei dischi in locale in un file XML
	 * di configurazione.
	 */
	protected void saveConfig()
	{
		
	}
	
	/**
	 * Legge la configurazione dei dishci virtuali presenti sul client dal 
	 * file XML di configurazione.
	 */
	protected void readConfig()
	{
		
	}
	
}
