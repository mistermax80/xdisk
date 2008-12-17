package xdisk.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import xdisk.net.ServerProcess;

public class Server implements Runnable
{
	final static int DEFAULT_LISTENER_THREAD = 5;
	
	private int listenerThread;
	
	private ServerSocket serverSocket;
	
	protected ServerThreadPool serverConnectionPool;
	private ServerProcess serverProcess;
	
	private Thread[] thread;

	public Server(ServerProcess serverProcess, int port) throws IOException 
	{
		serverSocket = new ServerSocket(port);
		listenerThread = DEFAULT_LISTENER_THREAD;
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
		thread = new Thread[listenerThread];
		for(int i = 0; i < this.listenerThread; i++) 
		{
			thread[i] = new Thread(this);
			thread[i].start();
		}
	}
	
	public void stop()
	{
		try 
		{
			serverSocket.close();
		} 
		catch (IOException e) 
		{
		}
		
		//System.out.println("server chiuso...");
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
				Socket client = serverSocket.accept();
					
				serverConnectionPool.getServerThread(client).start();
			}
		} 
		catch (IOException e) 
		{
		} 
		catch (Exception e) 
		{	
		}
	}
	
	

}
