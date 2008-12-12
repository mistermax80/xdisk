package xdisk.test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import xdisk.net.Server;
import xdisk.net.ServerProcess;

public class EchoServer implements ServerProcess
{
	@Override
	public void request(Socket client)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("HELLO");
			
			String mex = new String(in.readLine());
			
			while (mex != null)
			{
				System.out.println("CLIENT: " + mex);
				out.println("OK RCV: " + mex);
				mex = in.readLine();
			}
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
	}

	@Override
	public void requestError(Socket client) 
	{
		try 
		{
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			out.println("EMPTY - SORRY");
			
			out.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
}
