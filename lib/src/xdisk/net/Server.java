package xdisk.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import xdisk.net.ServerProcess;

// TODO controllo numero massimo di connessioni contemporanee
public abstract class Server extends ServerSocket implements Runnable
{
	private int listenerThread = 5;
	private boolean inited = false;
	
	protected ServerProcess serverProcess;

	public Server(int port) throws IOException 
	{
		super(port);
		serverProcess = null;
	}

	public void addServerProcess(ServerProcess serverProcess) 
	{
		this.serverProcess = serverProcess;
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

	public void run()
	{
		try 
		{
			while (true)
			{
				if (serverProcess != null)
				{
					Socket client = accept();
					serverProcess.request(client);
				}
				else
				{
					throw new Exception("server process non inited");
				}
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
