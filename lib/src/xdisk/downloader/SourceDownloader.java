package xdisk.downloader;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import xdisk.ClientResource;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;

public class SourceDownloader implements Runnable 
{
	private Downloader downloader;
	private ClientResource source;
	private Thread clientThread;
	
	private Socket socket;
	private XDiskOutputStream output;
	private XDiskInputStream input;
	
	public SourceDownloader(Downloader downloader, ClientResource source) 
			throws UnknownHostException, IOException 
	{
		this.source = source;
		
		
		connect();
	}
	
	public void connect() throws UnknownHostException, IOException
	{
		String response = "";
		
		// connessione al client
		socket = new Socket(source.getIp(), source.getPort());

		// creazione sistema messaggi input/output
		output = new XDiskOutputStream(socket.getOutputStream());
		input = new XDiskInputStream(socket.getInputStream());
		
		// invio del comando di saluto
		output.writeUTF("HELO");
		output.send();

		input.receive();
		response = input.readUTF();
		if (response.indexOf("HELO") != -1) // il client ha risposto..
		{
			// .. facciamo partire il thread che controlla il sistema
			clientThread = new Thread();
			clientThread.start();
		}		
	}

	@Override
	public void run() 
	{
		// invia il comando keep alive al server per indicare la presenza nella
		// rete virtuale
		Thread thisThread = Thread.currentThread();
            
		while (clientThread == thisThread)
		{
			// ottiene un token			
			Token token = downloader.getToken();
			
			// scarica il token, se ok, lo segnala, se errore, o restituisce
			
		}
	}

}
