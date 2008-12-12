package xdisk.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import xdisk.net.ServerProcess;

// TODO inclusione della classe ServerSocket per nascondere le sue funzionalità
// TODO chiusura del server e delle accept aperte
public class Server extends ServerSocket implements Runnable
{
	final static int DEFAULT_LISTENER_THREAD = 5;
	
	private int listenerThread;
	
	protected ServerThreadPool serverConnectionPool;
	
	private ServerProcess serverProcess;

	public Server(ServerProcess serverProcess, int port) throws IOException 
	{
		super(port);
		this.serverProcess = serverProcess;
		
		serverConnectionPool = new ServerThreadPool(this);
	}
	
	public ServerProcess getServerProcess()
	{
		return serverProcess;
	}	
	
	public ServerThreadPool getServerConnectionPool() 
	{
		return serverConnectionPool;
	}

	public void setServerConnectionPool(ServerThreadPool serverThread) 
	{
		this.serverConnectionPool = serverThread;
	}

	public void start()
	{
		for(int i = 0; i < this.listenerThread; i++) 
		{
			new Thread(this).start();
		}
	}
	
	public int getListenerThread() 
	{
		return listenerThread;
	}

	public void setListenerThread(int listenerThread) 
	{
		this.listenerThread = listenerThread;
	}
	
	public int getMaxConnection()
	{
		return serverConnectionPool.getMaxConnection();
	}
	
	public void setMaxConnection(int maxConnection)
	{
		serverConnectionPool.setMaxConnection(maxConnection);
	}

	public void run()
	{
		try 
		{
			while (true)
			{
				Socket client = accept();
					
				serverConnectionPool.getServerThread(client).start();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
	}
	
	

}
