package xdisk.downloader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import xdisk.ClientResource;
import xdisk.VirtualFile;

/**
 * La classe che si occupa dello scaricamento del file dalle varie fonti client.
 * 
 * @author biio
 * @version 6/2/2009
 */
public class Downloader 
{
	private VirtualFile virtualFile;
	private String tiketId;
	private DownloadListener listener;
	
	private static final int TOKEN_SIZE = 1024;
	
	private ArrayList<SourceDownloader> sources;
	private ArrayList<Token> tokens;
	private ArrayList<Token> tokensCompleted;
	
	private int numToken;
	private int numTokenCompleted;
	
	private String path = System.getProperty("user.dir") + "/download/";
	
	
	/**
	 * Crea un nuovo oggetto downloader per scaricare un {@link VirtualFile}
	 * da diverse fonti.
	 * @param virtualFile
	 * @throws IOException 
	 */
	public Downloader(VirtualFile virtualFile, String ticketId,
			DownloadListener listener) throws IOException 
	{
		this.virtualFile = virtualFile;
		this.tiketId = ticketId;
		this.listener = listener;
		
		tokens = new ArrayList<Token>();
		tokensCompleted = new ArrayList<Token>();
		sources  = new ArrayList<SourceDownloader>();
		
	}
	
	/**
	 * Fa partire il download del file. 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized void start() throws UnknownHostException, IOException
	{
		Iterator<SourceDownloader> iterator = sources.iterator();

		// preparazione dei token
		numToken = sources.size();
		numTokenCompleted = 0;
		
		int tokenSize = (int)(virtualFile.getSize() / numToken);
		int offset = 0;
		for (int i=0; i<numToken -1; i++)
		{
			Token token = new Token(offset, tokenSize);
			offset+=tokenSize;
			tokens.add(token);
		}
		Token token = new Token(offset, (int)(virtualFile.getSize() - offset));
		tokens.add(token);
		
		while (iterator.hasNext())
		{
			iterator.next().start();
		}
	}
	
	/**
	 * Aggiunge una risorsa client alla lista.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized void addSource(ClientResource source) 
		throws UnknownHostException, IOException
	{
		SourceDownloader sourceDownloader = new SourceDownloader(this, source); 
		sources.add(sourceDownloader);
	}
	
	/**
	 * Ritorna un token non ancora scaricato. Il token da questo momento è 
	 * considerato prenotato ed assegnato ad un thread di scaricamento.
	 * @return il token da scaricare e riempire, o null se non ci sono più 
	 * token da scaricare.
	 */
	public synchronized Token getToken()
	{
		System.out.println(tokens);
		Token token = tokens.get(0);
		
		
		tokens.remove(token);
		
		return token;
	}
	
	/**
	 * Segnala al downloader che il token, è stato completamente scaricato e 
	 * riempito.
	 * @param token il token completato.
	 */
	public synchronized void tokenCompleted(Token token)
	{
		numTokenCompleted++;
		tokensCompleted.add(token);
		
		if (numToken == numTokenCompleted) // scaricamento completato
		{
			Token t = null;
			try {
				DataOutputStream out = new DataOutputStream(
						new FileOutputStream(path + virtualFile.getFilename() + "." +
								virtualFile.getExtension()));
				while ((t = getLowOffset()) != null)
				{
					out.write(t.getData(), t.getOffset(), t.getSize());
				}
				out.close();
				listener.completed(path + virtualFile.getFilename() + "." +
						virtualFile.getExtension(), virtualFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}			
	}
	
	private Token getLowOffset()
	{
		Token t = null;
		Iterator<Token> i = tokensCompleted.iterator();
		
		int offset = (int)virtualFile.getSize();
		while (i.hasNext())
		{
			Token curr = i.next();
			if (curr.getOffset() <= offset)
				t = curr;
		}
		if (t != null)
			tokensCompleted.remove(t);
		
		return t;
	}
	
	/**
	 * Ritorna il ticket id di scaricamento del file
	 * @return il ticket id di scaricamento del file
	 */
	public String getTicketId()
	{
		return tiketId;
	}
	
}
