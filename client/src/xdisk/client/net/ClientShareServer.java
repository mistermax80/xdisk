package xdisk.client.net;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;
import xdisk.client.core.XDiskClient;
import xdisk.net.Server;
import xdisk.net.ServerProcess;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;
import xdisk.persistence.Client;

/**
 * Il server utilizzato dal client per condividere le proprie risorse
 * @author biio
 * @version 7/2/2009
 *
 */
public class ClientShareServer implements ServerProcess
{

	private Server server;
	private VirtualDisk virtualDisk;

	private XDiskOutputStream output;
	private XDiskInputStream input;
	

	/**
	 * Crea un nuovo server per la condivisione delle risorse
	 * @param virtualDisk
	 * @throws IOException
	 */
	public ClientShareServer(VirtualDisk virtualDisk) throws IOException 
	{
		this.virtualDisk = virtualDisk;
		
		server = new Server(this, virtualDisk.getLocalPort());
		server.setListenerThread(15);
		server.setMaxConnection(5);
		server.start();
		
		System.out.println("Server Client Share Started listening on port:" + 
				virtualDisk.getLocalPort());
	}
	
	@Override
	public void request(Socket client) 
	{
		String response;
		String ipClient = client.getInetAddress().getHostAddress();
		String hostname = client.getInetAddress().getCanonicalHostName();
		int portClient = client.getPort();

		System.out.println("Connection required from \n\thost:"+hostname+"\n\tip client:"+ipClient+"\n\tport:"+portClient);
		

		try 
		{
			// creazione del sistema dei messaggio input/output con il server
			output = new XDiskOutputStream(client.getOutputStream());
			input = new XDiskInputStream(client.getInputStream());
			
			// ricezione del messaggio di saluto
			input.receive();
			response = input.readUTF();
			
			if (response.indexOf("HELO")!= -1)
			{
				// risposta al messaggio di benvenuto
				output.writeUTF("HELO");
				output.send();
				
				// attendiamo la richiesta
				input.receive();
				response = input.readUTF();
				if (response.indexOf("GETFILE") != -1)
				{
					// leggiamo il tikedId
					String tiketId = input.readUTF();
					
					// chediamo al server se il token è valido
					VirtualFile virtualFile = virtualDisk.isValidTicket(tiketId);
					
					if (virtualFile != null) // file valido
					{
						System.out.println("TIKET IS VALID: " + tiketId);
						
						DataInputStream source = getLocalFile(virtualFile);
						if (source != null)
						{
						
							// indichiamo la disponibilità di fornire il file..
							output.writeUTF("OK");
							output.send();
							
							// ascoltiamo le richieste dei token
							int offset, size;
							input.receive();
							if (input.readUTF().indexOf("GETTOKEN") != -1)
							{
								offset = input.readInt();
								size = input.readInt();
								
								System.out.println("REQUEST TOKEN offset: " + offset +
										", size: " + size);
								
								// leggiamo i dati e mettiamoli in un buffer
								byte[] buffer = new byte[size];
								source.read(buffer, offset, size);
								
								// inviamo il token
								output.writeUTF("OK");
								output.write(buffer, offset, size);
								output.send();
								
								System.out.println("TOKEN SENDED offset: " + offset +
										", size: " + size);
														
							}
							source.close();
						}
						else
						{
							output.writeUTF("FILENOTFOUND");
							output.send();
						}
					}
					else // tiketId invalido
					{
						System.out.println("INVALID TIKET: " + tiketId);
					}
				}
				else
				{
					System.out.println("UNK MESSAGE: " + response + " // NO GETFILE");
				}
			}
			else
			{
				System.out.println("UNK MESSAGE: " + response);
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
		
	}
	
	private DataInputStream getLocalFile(VirtualFile virtualFile) throws FileNotFoundException
	{
		// apriamo il file locale e vediamo se esiste...
		System.out.println("cerco il file " + virtualFile);
		
		String localFilename=null;
		try {
			localFilename = virtualDisk.getLibrary().
											getLocalFileName(virtualFile);
			System.out.println("Trovato locale: " + localFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (localFilename != null)
		{
			return new DataInputStream(new FileInputStream(localFilename));
		}
		
		return null;
	}

}
