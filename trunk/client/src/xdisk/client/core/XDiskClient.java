package xdisk.client.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import xdisk.VirtualFile;

public class XDiskClient {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		try 
		{
			VirtualDisk disk = new VirtualDisk("massimo", "localhost", 
					4444, "ciips", "c");
			
			disk.connect();
			Thread.sleep(1000);
			System.out.println("===============================");
			disk.getList("/");
			Thread.sleep(1000);
			System.out.println("===============================");
			disk.getList("/prova/picchio/ciao/");
			Thread.sleep(1000);
			System.out.println("===============================");
			
			VirtualFile file = new VirtualFile();
			file.setFilename("docoun");
			file.setExtension("sys");
			file.setDescription("description");
			file.setOwner("ciips");
			file.setTags("tag,tags,ta,t");
			file.setSize(2324);
			file.setMime("mime");
			file.setPath("/prova/picchio/");
			
			disk.insertFile(file);
			Thread.sleep(1000);
			disk.getList("/prova/");
			Thread.sleep(1000);
			System.out.println(disk.search(""));
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
