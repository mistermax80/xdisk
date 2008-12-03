package xdisk.test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


import xdisk.net.Server;
import xdisk.net.ServerProcess;

public class EchoServer extends Server implements ServerProcess
{

	public EchoServer(int port) throws IOException 
	{
		super(port);
		addServerProcess(this);
	}
	
	public void request(Socket client)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String mex = new String(in.readLine());
			
			while (mex != null)
			{
				System.out.println("Server: " + mex);
				mex = in.readLine();
			}
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
		
	}
	
}
