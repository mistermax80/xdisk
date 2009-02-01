package xdisk.client.core;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XDiskClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try 
		{
			VirtualDisk disk = new VirtualDisk("massimo", "192.168.1.11", 
					4444, "ciips", "c2");
			
			disk.connect();

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
