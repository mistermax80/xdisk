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
	private static VirtualDiskManager instance = null;
	private ArrayList<VirtualDisk> virtualDisks;
	
	
	private VirtualDiskManager() 
	{
		virtualDisks = new ArrayList<VirtualDisk>();
		
	}
	
	public static synchronized VirtualDiskManager getInstance()
	{
		if (instance == null)
			instance = new VirtualDiskManager();

		return instance;
	}
	
	public void add(VirtualDisk virtualDisk)
	{
		virtualDisks.add(virtualDisk);
	}
	
	public void removeByAddress(String address)
	{

	}
	
	public int getNum()
	{
		return virtualDisks.size();
	}
	
	public VirtualDisk get(int index)
	{
		return virtualDisks.get(index);
	}
	
	/**
	 * Salva la configurazione corrente dei dischi in locale in un file XML
	 * di configurazione.
	 */
	protected void saveConfig()
	{
		
	}
	
	/**
	 * Legge la configurazione dei dischi virtuali presenti sul client dal 
	 * file XML di configurazione.
	 */
	protected void readConfig()
	{
		///home/massimo/workspace/client/resource/manifest test/disco1.manifest.xml
		//Scandisce tutti i file di configurazione presenti e crea vari dischi...
		Manifest manifest = new Manifest();
		//File file = FileI 
		String currPath=System.getProperty("user.dir");
		System.out.println(currPath);
		VirtualDisk disk = manifest.getVirtualDisk(currPath+"/disco1.manifest.xml");
		virtualDisks.add(disk);
	}
	
}
