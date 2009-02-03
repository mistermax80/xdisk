package xdisk.client.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import xdisk.VirtualFile;

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
			System.out.println("===============================");
			disk.getList("/");
			System.out.println("===============================");
			disk.getList("/prova/picchio/ciao/");
			System.out.println("===============================");
			
			VirtualFile file = new VirtualFile();
			file.setFilename("picchio");
			file.setExtension("sys");
			file.setDescription("description");
			file.setOwner("ciips");
			file.setTags("tag,tags,ta,t");
			file.setSize(2324);
			file.setMime("mime");
			file.setPath("/prova/");
			
			disk.insertFile(file);
			disk.getList("/prova/picchio/");
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
