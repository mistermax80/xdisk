package xdisk.client.test;

import xdisk.client.core.VirtualDisk;
import xdisk.client.core.VirtualDiskManager;

public class VirtualDiskConfig {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		VirtualDisk virtualDisk = new VirtualDisk();
		
		virtualDisk.setName("prova");
		virtualDisk.setDescription("File di prova...");
		virtualDisk.setServerAddress("localhost");
		virtualDisk.setServerPort(4444);
		virtualDisk.setWebPanelAddress("loclahost");
		virtualDisk.setWebPanelPort(8080);
		virtualDisk.setUserid("picchio");
		virtualDisk.setPassword("m");
		
		VirtualDiskManager.getInstance().add(virtualDisk);
		VirtualDiskManager.getInstance().saveConfig();
	}

}
