package xdisk.client;

import java.net.URL;
import java.util.ArrayList;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualResource;

/**
 * Client del disco virtuale. Fornisce le funzionaltà di connessione e 
 * impostazione dei parametri del disco. Fornisce inoltre tutte le operazioni
 * consentite al disco virtuale.
 * 
 * @author Fabrizio Filieri
 * @version 31/1/2009
 *
 */
public class VirtualDisk 
{
	private String name;
	private URL url;
	private int serverPort;
	private String userid;
	private String password;
	
	private String sessionId;
	
	/**
	 * Crea un nuovo disco virtuale.
	 * 
	 * @param name il nome del disco virtuale
	 * @param url l'indirizzo di connessione al disco virtuale
	 * @param serverPort la porta di connessione al disco virtuale
	 * @param username lo userid di connessione al disco
	 * @param password la password per la connessione al disco
	 */
	public VirtualDisk(String name, URL url, int serverPort, String userid, 
			String password) 
	{
		this.name = name;
		this.url = url;
		this.serverPort = serverPort;
		this.userid = userid;
		this.password = password;
	}
	
	/**
	 * Effettua la connessione al disco
	 * @return true se la connessione riesce, false altrimenti
	 */
	public boolean connect()
	{
		
		return true;
	}
	
	/**
	 * Effettua la disconnessione dal server del disco virtuale
	 */
	public void disconnect()
	{
		
	}

	/**
	 * Invia il segnale di keep alive al server, per segnalare la presenza 
	 * del client nella rete virtuale del disco.
	 */	
	public void keepAlive()
	{
		
	}
	
	/**
	 * Ritorna la lista dei file e delle directory di uno specifico path 
	 * @param path il path di cui ottenere la lista dei file e delle directory
	 * @return La lista dei file e delle directory
	 */
	public ArrayList<VirtualResource> getList(String path)
	{
		return null;
	}
	
	/**
	 * Aggiunge un nuovo file al disco
	 * @return
	 */
	public boolean insertFile(VirtualFile file)
	{
		return false;
	}
	
	
	/**
	 * Cerca un file nel disco, utilizzando query per cercare nella descrizione,
	 * nei tags, e nel nome del file.
	 * @param query il parametro di ricerca
	 * @return una lista di file virtuali corrispondenti alla ricerca, o null
	 * in caso non venga trovata alcun file corrispondente alla ricerca.
	 */
	public ArrayList<VirtualFile> search(String query)
	{
		return null;
	}

	/**
	 * Ritorna il file virtuale sul disco con con nome del file pari a filename
	 * @param filename il nome del file del disco virtuale da recuperare
	 * @return il file virtuale se presente sul disco, false altrimenti
	 */
	public VirtualFile getFile(String filename)
	{
		return null;
	}
	
	/**
	 * Restituisce la lista dei client che possiedono la risorsa file  
	 * @param file il file di cui conoscere le sorgenti client 
	 * @return la lista dei client che possiedono la risorsa, o null se non 
	 * esistono.
	 */
	public ArrayList<ClientResource> getSource(VirtualFile file)
	{
		return null;
	}
	
	/**
	 * Segnala al disco che il file è presente in locale, ossia il client 
	 * segnala la possibilità di fornire il file alla rete 
	 * @param file il file da segnalare come presente sul disco locale
	 * @return true se l'operazione riesce, false altrimenti.
	 */
	public boolean gotFile(VirtualFile file)
	{
		return false;
	}
	
	/**
	 * Indica al server che il file non è presente, o non è più presente in 
	 * locale, e che quindi non può più fornire alla rete la risorsa.
	 * @param file il file che non si può fornire
	 * @return true se la segnalazione riesce, false altrimenti.
	 */
	public boolean notGotFile(VirtualFile file)
	{
		return false;
	}
	
	/**
	 * Chiede al server, se il ticket di scricamento richiesto da altri client
	 * è valido. Se è valido, ritorna il fil viruale da scaricare.
	 * @param ticket il ticket di scaricamento
	 * @return il file virtuale da fornire se il ticket è valido, null in caso 
	 * contrario. 
	 */
	public VirtualFile isValidTicket(String ticket)
	{
		return null;
	}

	/**
	 * Ritorna il nome del disco
	 * @return il nome del disco
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Ritorna l'url del disco
	 * @return l'url del disco
	 */
	public URL getUrl() 
	{
		return url;
	}

	/**
	 * Ritorna la porta di connessione al server
	 * @return la porta di connessione al server
	 */
	public int getServerPort() 
	{
		return serverPort;
	}

	/**
	 * Ritorna lo userid di accesso al disco
	 * @return lo userid di accesso al disco
	 */
	public String getUserid() 
	{
		return userid;
	}

	/**
	 * Ritorna la password di accesso al disco
	 * @return la password di accesso al disco 
	 */
	public String getPassword() 
	{
		return password;
	}

}
