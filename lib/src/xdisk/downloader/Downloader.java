package xdisk.downloader;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	private ArrayList<ClientResource> source;
	private HashMap<Integer, Token> tokens;
	private HashMap<Integer, Token> tokensAvaible;
	
	
	/**
	 * Crea un nuovo oggetto downloader per scaricare un {@link VirtualFile}
	 * da diverse fonti.
	 * @param virtualFile
	 */
	public Downloader(VirtualFile virtualFile, String ticketId) 
	{
		this.virtualFile = virtualFile;
		this.tiketId = ticketId;
	}
	
	/**
	 * Fa partire il download del file. Se non ci sono risorse, lo scaricamento
	 * viene considerato in coda, e all'aggiunta della prima risorsa client
	 * disponibile partirà lo scaricamento.
	 */
	public synchronized void start()
	{
		
	}
	
	/**
	 * Ferma il download del file.
	 */
	public synchronized void stop()
	{
		
	}
	
	/**
	 * Aggiunge una risorsa client alla lista. Se il download è attivo, si 
	 * avvierà il thread di scaricamento da questo client.
	 * @param source il client fornitore da aggiungere.
	 */
	public synchronized void addSource(ClientResource source)
	{
		
	}
	
	/**
	 * Ritorna un token non ancora scaricato. Il token da questo momento è 
	 * considerato prenotato ed assegnato ad un thread di scaricamento.
	 * @return il token da scaricare e riempire.
	 */
	public synchronized Token getToken()
	{
		return null;
	}
	
	/**
	 * Segnala al downloader che il token, è stato completamente scaricato e 
	 * riempito.
	 * @param token il token completato.
	 */
	public synchronized void tokenCompleted(Token token)
	{
		
	}
	
	/**
	 * Segnala al downloader che il token in questione non è stato scaricato
	 * correttamente. 
	 * @param token il token da rimettere a disposizione per il download
	 */
	public synchronized void tokenFree(Token token)
	{
		
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
	 */
	protected void savePartFile()
	{
		
	}
	
	/**
	 * Carica lo stato di un download da un file di specifica per poter eseguire
	 * il resume.
	 */
	protected void loadPartFile()
	{
		
	}
}
