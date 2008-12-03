package xdisk.test.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client 
{
	private String address;
	private int port;
	
	private Socket echoSocket;
	
	public Client()
	{
		address = "localhost";
		port = 4444;
	}
	
	public void run()
	{
		try 
		{
			echoSocket = new Socket(address, port);
			System.out.println("Connesso al server... invio il messaggio..");
			
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			
			out.println("Ciaoooo!!");
			out.println("Ciaoooo 2!!");
			out.println("Ciaoooo 3!!");
			out.println("addio");
			
			out.close();
			echoSocket.close();
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
