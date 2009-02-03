package xdisk.client.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class XDiskClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try 
		{
			VirtualDisk disk = new VirtualDisk("massimo", "localhost", 
					4444, "ciips", "c");
			
			disk.connect();
			disk.getList("/prova/picchrerio/ciao/bubu");

		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
