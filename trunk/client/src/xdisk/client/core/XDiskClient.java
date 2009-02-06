package xdisk.client.core;

import xdisk.client.gui.Xdisk;

public class XDiskClient {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		VirtualDiskManager diskManager = VirtualDiskManager.getInstance();
		diskManager.readConfig();
		System.out.println(diskManager.getNum());
		
		VirtualDisk vDisk = new VirtualDisk();
		for(int i=0;i<diskManager.getNum();i++){
			vDisk = diskManager.get(i);
			Xdisk xdisk = new Xdisk(vDisk);
			xdisk.execute();
		}
	}

}
