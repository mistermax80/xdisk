package xdisk.net;

import java.io.IOException;
import java.net.Socket;

public class ServerThreadPool
{
	private int maxConnection;
	private int activeConnection;
	
	private Server server;
	
	public ServerThreadPool(Server server) 
	{
		maxConnection = 20;
		this.server = server;
	}	
		
	public synchronized Thread getServerThread(Socket client)
	{
		//System.out.println(" + Connessioni attive: " + activeConnection);
		if (activeConnection < maxConnection)
		{
			//System.out.println("    connessione permessa");
			activeConnection++;
			return new Connection(client);
		}
		else
		{
			//System.out.println("    connessione negata");
			return new ConnectionError(client);
		}
	}
	
	public synchronized void releaseServerConnection()
	{
		//System.out.println(" - connessione rilasciata");
		if (activeConnection > 0)
			activeConnection--;
	}

	public int getMaxConnection() 
	{
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) 
	{
		this.maxConnection = maxConnection;
	}
	
	private class ConnectionError extends Thread
	{
		private Socket client;
		public ConnectionError(Socket client) 
		{
			this.client = client;
		}
		
		public void run()
		{
			server.getServerProcess().requestError(client);
			
			try 
			{
				client.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			releaseServerConnection();
		}
	}
	
	private class Connection extends Thread
	{
		private Socket client;
		public Connection(Socket client) 
		{
			this.client = client;
		}
		
		public void run()
		{
			server.getServerProcess().request(client);
			
			try 
			{
				client.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			releaseServerConnection();
		}
	}
	
	
}
