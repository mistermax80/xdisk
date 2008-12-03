package xdisk.test.net;

import java.io.IOException;

import xdisk.test.net.Client;


public class EchoServerMain 
{
	public static void main(String[] args)
	{
		try 
		{
			EchoServer server = new EchoServer(4444);
			server.start();
			
			Thread.sleep(200);
			
			Client client = new Client();
			client.run();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}		
	}

}
