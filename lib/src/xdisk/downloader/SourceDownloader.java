package xdisk.downloader;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import xdisk.ClientResource;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;

/**
 * Il download di una sorgente. Effettua la connessione ad una sorgente, invia
 * il tiket, e se la sorgente è disponibile, comincia il trasferimento dei 
 * token del file.
 * 
 * @author biio
 * @version 6/2/2009
 */
public class SourceDownloader implements Runnable 
{
	private Downloader downloader;
	private ClientResource source;
	
	private Thread clientThread;
	
	private Socket socket;
	private XDiskOutputStream output;
	private XDiskInputStream input;
	
	private boolean isConnect;
	
	/**
	 * Crea un nuovo oggetto per lo scaricamento da una fonte.
	 * @param downloader il downloader del file.
	 * @param source la sorgente da cui scaricare il file.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SourceDownloader(Downloader downloader, ClientResource source) 
			throws UnknownHostException, IOException 
	{
		this.source = source;
		this.downloader = downloader;
		
		isConnect = false;

	}
	
	/**
	 * Ferma il download dalla fonte
	 */
	public void stop()
	{
		clientThread = null;
	}
	
	/**
	 * Effettua la connessione al fornitore, e se il client decide di fornire
	 * la risorsa, fa partire il thread per lo scaricamento dei token.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start() throws UnknownHostException, IOException
	{
		String response = "";
		
		// connessione al client
		System.out.println("Provo a conettermi a client: " + source);
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
			// invio la richiesta..
			output.writeUTF("GETFILE");
			output.writeUTF(downloader.getTicketId());
			output.send();
			
			// aspettiamo la risposta
			input.receive();
			response = input.readUTF();
			
			if (response.indexOf("OK") != -1)
			{
				System.out.println("Il client mi può fornire il file...");
				isConnect = true;
				// .. facciamo partire il thread che controlla il download
				clientThread = new Thread(this);
				clientThread.start();
			}
			else
			{
				System.out.println("Il client non mi darà il file..." + response);
				output.close();
				input.close();
				socket.close();
				isConnect = false;
			}
			
		}		
	}
	
	/**
	 * Ritorna true se il {@link SourceDownloader} è connesso al fornitore, false
	 * altrimenti
	 * @return true se si è connessi al fornitore, false altrimenti.
	 */
	public boolean isConnect()
	{
		return isConnect;
	}

	@Override
	public void run() 
	{
		// invia il comando keep alive al server per indicare la presenza nella
		// rete virtuale
		Thread thisThread = Thread.currentThread();
		String response = "";
            
		if (clientThread == thisThread)
		{
			// ottiene un token			
			Token token = downloader.getToken();
			
			if (token != null)
			{
				try
				{
					// scarica il token, se ok, lo segnala, se errore, o restituisce
					output.writeUTF("GETTOKEN");
//					output.writeUTF(downloader.getTicketId());
					output.writeInt(token.getOffset());
					output.writeInt(token.getSize());
					output.send();
					
					System.out.println("Richiesto token: " + token.getOffset());
		
					input.receive();
					response = input.readUTF();
					
					if (response.indexOf("OK") != -1)
					{
						byte[] buffer = new byte[token.getSize()];
						input.read(buffer, token.getOffset(), token.getSize());
						token.setData(buffer);
						downloader.tokenCompleted(token);
						System.out.println("Token salvato " + token.getOffset());
					}
					else
					{
						System.out.println("Impossibile salvare il token " + token.getOffset());
					}
					
				}
				catch (Exception e) 
				{
					isConnect = false;
					clientThread = null;
				}
			}
		}
	}

}
