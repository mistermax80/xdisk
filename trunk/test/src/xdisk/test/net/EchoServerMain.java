package xdisk.test.net;

import java.io.IOException;

import xdisk.net.Server;
import xdisk.test.net.Client;


public class EchoServerMain 
{
	public static void main(String[] args)
	{
		try 
		{ 
			Server server = new Server(new EchoServer(), 4444);
			server.setListenerThread(15);
			server.setMaxConnection(5);
			server.start();
			
			Thread.sleep(200);
			
			for (int i=0; i<10; i++)
				new Client().start();
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
