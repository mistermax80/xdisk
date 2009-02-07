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
 * Sarà possibile fermare e far ripartire il download ed aggiungere i client alla
 * lista delle fonti. L'aggiunta delle fonti (ip e address), avviene in maniera
 * dinamica.
 * @author biio
 * @version 6/2/2009
 */
public class Downloader 
{
	private VirtualFile virtualFile;
	private String tiketId;
	
	private static final int TOKEN_SIZE = 1024;
	
	private ArrayList<SourceDownloader> sources;
	private HashMap<Integer, Token> tokens;
	private HashMap<Integer, Token> tokensAvaible;
	
	private boolean isStarted;
	
	private int numToken;
	
	
	/**
	 * Crea un nuovo oggetto downloader per scaricare un {@link VirtualFile}
	 * da diverse fonti.
	 * @param virtualFile
	 * @throws IOException 
	 */
	public Downloader(VirtualFile virtualFile, String ticketId) throws IOException 
	{
		this.virtualFile = virtualFile;
		this.tiketId = ticketId;
		
		this.isStarted = false;
		
		// preparazione dei token
		numToken = (int)(virtualFile.getSize() / TOKEN_SIZE);
		
		if ( (virtualFile.getSize() % TOKEN_SIZE) > 0) // controllo l'ultimo token
			numToken++;
		
		File f = new File(getFileName());
		if (f.exists())
		{
			loadPartFile();
		}
		else // il file non esiste, creiamo i token manualmente
		{
			long byteRemaining = virtualFile.getSize();
			// creazione dei token	
			for (int i=0; i<numToken; i++)
			{
				if (byteRemaining < TOKEN_SIZE)
				{
					Token token = new Token(i*TOKEN_SIZE, (int)byteRemaining);
					tokens.put(i*TOKEN_SIZE, token);
				}
				else
				{
					Token token = new Token(i*TOKEN_SIZE, TOKEN_SIZE);
					tokens.put(i*TOKEN_SIZE, token);
				}
				
				byteRemaining-= TOKEN_SIZE;
			}
		}
		
		// creazione dei token liberi
		for (int i=0; i<numToken; i++)
		{
			Token token = tokens.get(i*TOKEN_SIZE);
			if (!token.isCompleted())
				tokensAvaible.put(i*TOKEN_SIZE, token);
		}
	}
	
	/**
	 * Fa partire il download del file. Se non ci sono risorse, lo scaricamento
	 * viene considerato in coda, e all'aggiunta della prima risorsa client
	 * disponibile partirà lo scaricamento.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized void start() throws UnknownHostException, IOException
	{
		Iterator<SourceDownloader> iterator = sources.iterator();
		
		while (iterator.hasNext())
		{
			iterator.next().start();
		}
			
		isStarted = true;
	}
	
	/**
	 * Ferma il download del file e salva lo stato attuale
	 * @throws IOException 
	 */
	public synchronized void stop() throws IOException
	{
		Iterator<SourceDownloader> iterator = sources.iterator();
		
		while (iterator.hasNext())
		{
			iterator.next().stop();
		}
		
		savePartFile();
		
		isStarted = true;
	}
	
	/**
	 * Aggiunge una risorsa client alla lista. Se il download è attivo, si 
	 * avvierà il thread di scaricamento da questo client.
	 * @param source il client fornitore da aggiungere.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized void addSource(ClientResource source) 
		throws UnknownHostException, IOException
	{
		SourceDownloader sourceDownloader = new SourceDownloader(this, source); 
		sources.add(sourceDownloader);
		
		if (isStarted)
			sourceDownloader.start();
	}
	
	/**
	 * Ritorna un token non ancora scaricato. Il token da questo momento è 
	 * considerato prenotato ed assegnato ad un thread di scaricamento.
	 * @return il token da scaricare e riempire, o null se non ci sono più 
	 * token da scaricare.
	 */
	public synchronized Token getToken()
	{
		Token token = tokensAvaible.get(0);
		
		tokensAvaible.remove(token);
		
		return token;
	}
	
	/**
	 * Segnala al downloader che il token, è stato completamente scaricato e 
	 * riempito.
	 * @param token il token completato.
	 */
	public synchronized void tokenCompleted(Token token)
	{
//		tokensAvaible.remove(token);
	}
	
	/**
	 * Segnala al downloader che il token in questione non è stato scaricato
	 * correttamente. 
	 * @param token il token da rimettere a disposizione per il download
	 */
	public synchronized void tokenFree(Token token)
	{
		tokensAvaible.put(token.getOffset(), token);
	}
	
	/**
	 * Ritorna il ticket id di scaricamento del file
	 * @return il ticket id di scaricamento del file
	 */
	public String getTicketId()
	{
		return tiketId;
	}
	
	/**
	 * Salva lo stato attuale del download su un file.
	 * @throws IOException 
	 */
	protected void savePartFile() throws IOException
	{
		DataOutputStream out = new DataOutputStream(
				new FileOutputStream(getFileName()));
		
		// salvataggio dei dati del virtual file
		out.writeUTF(virtualFile.getFilename());
		out.writeUTF(virtualFile.getDescription());
		out.writeUTF(virtualFile.getExtension());
		out.writeUTF(virtualFile.getMime());
		out.writeUTF(virtualFile.getOwner());
		out.writeUTF(virtualFile.getPath());
		out.writeUTF(virtualFile.getTags());
		out.writeLong(virtualFile.getSize());
		
		// salvataggio dei token
		Iterator<Integer> iterator = tokens.keySet().iterator();
		while (iterator.hasNext())
		{
			Token token = tokens.get(iterator.next());
			
			out.writeInt(token.getOffset());
			out.writeInt(token.getSize());
			out.writeBoolean(token.isCompleted());
			out.write(token.getData(), token.getOffset(), token.getSize());
		}
		
		// chiusura del file
		out.close();
	}
	
	/**
	 * Carica lo stato di un download da un file di specifica per poter eseguire
	 * il resume.
	 * @throws IOException 
	 */
	protected void loadPartFile() throws IOException
	{
		DataInputStream in = new DataInputStream(
				new FileInputStream(getFileName()));
		
		// lettura del file virtuale
		virtualFile.setFilename(in.readUTF());
		virtualFile.setDescription(in.readUTF());
		virtualFile.setExtension(in.readUTF());
		virtualFile.setMime(in.readUTF());
		virtualFile.setOwner(in.readUTF());
		virtualFile.setPath(in.readUTF());
		virtualFile.setTags(in.readUTF());
		virtualFile.setSize(in.readLong());
		
		// lettura dei token dal file
		int offset, size;
		byte[] buffer;
		for (int i=0; i<numToken; i++)
		{
			offset = in.readInt();
			size = in.readInt();
			
			Token token = new Token(offset, size);
			token.setCompleted(in.readBoolean());

			buffer = new byte[size];
			in.read(buffer, offset, size);
			token.setData(buffer);
		}
		
		in.close();
	}
	
	/**
	 * Ritorna il nome del file part di download
	 * @return il nome del file part
	 */
	private String getFileName()
	{
		return virtualFile.getFilename() + ".part";
	}
	
}
