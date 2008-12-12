package xdisk.test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread
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
			
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			String helloMex = in.readLine();
			System.out.println("Connesso al server: " + helloMex);
			
			if (helloMex.equals("HELLO"))
			{
				out.println("ciao!!");
				System.out.println("SERVER: " + in.readLine());
				try {
					Thread.sleep(2002);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				out.println("addio");
				System.out.println("SERVER: " + in.readLine());
			}
			
			
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
