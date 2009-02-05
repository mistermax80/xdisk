package xdisk.client.core;

import java.util.ArrayList;

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
		for(int i=0;i<diskManager.getNum();i++){
			diskManager.get(i);
			Xdisk xdisk = new Xdisk(i);
			xdisk.execute();
		}
	}

}
