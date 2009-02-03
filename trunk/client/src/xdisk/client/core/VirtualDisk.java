package xdisk.client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import sun.security.provider.MD5;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualFolder;
import xdisk.VirtualResource;
import xdisk.net.MessageInputStream;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;

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
	private String url;
	private int serverPort;
	private String userid;
	private String password;

	private String sessionId;

	private Socket socket;
	private XDiskOutputStream output;
	private XDiskInputStream input;


	/**
	 * Crea un nuovo disco virtuale.
	 * 
	 * @param name il nome del disco virtuale
	 * @param url l'indirizzo di connessione al disco virtuale
	 * @param serverPort la porta di connessione al disco virtuale
	 * @param username lo userid di connessione al disco
	 * @param password la password per la connessione al disco
	 */
	public VirtualDisk(String name, String url, int serverPort, String userid, 
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
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public boolean connect() throws UnknownHostException, IOException
	{
		String response;

		// inizializzazione della connessione
		initConnection();		



		// deinizializzazione della connessione
		deinitConnection();

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
	 * @throws IOException 
	 */
	public ArrayList<VirtualResource> getList(String path) throws IOException
	{
		String response;
		ArrayList<VirtualResource> result = new ArrayList<VirtualResource>();

		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta path...");
			// invio la richiesta al server
			output.writeUTF("GETLIST");
			output.writeUTF(path);
			output.send();


			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				System.out.println("\nRicevuta lista delle risorse:");
				//Ricevo la lista dei Folder
				int numFolder = input.readInt();
				for (int i=0; i<numFolder; i++)
				{
					VirtualFolder folder = input.readVirtualFolder();
					System.out.println("\tFOLDER: " + folder);
					result.add(folder);
				}
				//Ricevo la lista dei File
				int numFile = input.readInt();
				for (int i=0; i<numFile; i++)
				{
					VirtualFile file = input.readVirtualFile();
					System.out.println("\tFILE: " + file.getPath()+file.getFilename()+"."+file.getExtension());
					result.add(file);
				}			

				System.out.println("\n\n");
			}
			else
			{
				System.err.println("Impossibile recuperare la lista delle risorse");
			}

			// deinizializzazione della connessione
			deinitConnection();
		}

		return result;
	}

	/**
	 * Aggiunge un nuovo file al disco
	 * @return
	 * @throws IOException
	 * @throws UnknownHostException 
	 */
	public boolean insertFile(VirtualFile file) throws UnknownHostException, IOException
	{
		boolean ret=false;
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta INSERT...");
			// invio la richiesta al server
			output.writeUTF("INSERT");
			output.writeVirtualFile(file);
			output.send();


			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				ret=true;
				System.out.println("File inserito correttamente!");
			}
			else if(response.equals("PRESENT")){
				System.err.println("Impossibile inserire il file file già presente!!!");
			}
			else
			{
				System.err.println("Impossibile inserire il file!!!");
			}

			// deinizializzazione della connessione
			deinitConnection();
		}
		return ret;
	}


	/**
	 * Cerca un file nel disco, utilizzando query per cercare nella descrizione,
	 * nei tags, e nel nome del file.
	 * @param query il parametro di ricerca
	 * @return una lista di file virtuali corrispondenti alla ricerca, o null
	 * in caso non venga trovata alcun file corrispondente alla ricerca.
	 * @throws IOException 
	 */
	public ArrayList<VirtualFile> search(String query) throws IOException
	{
		ArrayList<VirtualFile> result = new ArrayList<VirtualFile>();
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta SEARCH...");
			// invio la richiesta al server
			output.writeUTF("SEARCH");
			output.writeUTF(query);
			output.send();

			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				int numFiles = input.readInt();
				System.out.println("\tFile trovati:"+numFiles);
				for(int i=0;i<numFiles;i++){
					VirtualFile file = input.readVirtualFile();
					result.add(file);
				}
				System.out.println("\tFile ricevuti correttamente!");
			}
			else
			{
				System.err.println("Impossibile Ricevere la lista di file!!!");
			}

			// deinizializzazione della connessione
			deinitConnection();
		}
		return result;
	}

	/**
	 * Ritorna il file virtuale sul disco con con nome del file pari a filename
	 * @param canonicalName il nome del file del disco virtuale da recuperare con tutto il suo path
	 * @return il file virtuale se presente sul disco, false altrimenti
	 * @throws IOException 
	 */
	public VirtualFile getFile(String canonicalName) throws IOException
	{
		VirtualFile file = new VirtualFile();
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta GET...");
			// invio la richiesta al server
			output.writeUTF("GET");
			output.writeUTF(canonicalName);
			output.send();

			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				file = input.readVirtualFile();
				System.out.println("\tFile trovato correttamente!");
			}
			else if(response.equals("NOTPRESENT")){
				System.err.println("File non esistente!!!");
			}
			else
			{
				System.err.println("Impossibile Ricevere il file!!!");
			}
			// deinizializzazione della connessione
			deinitConnection();
		}
		return file;
	}

	/**
	 * Restituisce la lista dei client che possiedono la risorsa file  
	 * @param file il file di cui conoscere le sorgenti client 
	 * @return la lista dei client che possiedono la risorsa, o null se non 
	 * esistono.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ArrayList<ClientResource> getSource(VirtualFile file) throws UnknownHostException, IOException
	{
		ArrayList<ClientResource> clients = new ArrayList<ClientResource>();
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta GETSOURCE...");
			// invio la richiesta al server
			output.writeUTF("GETSOURCE");
			output.writeVirtualFile(file);
			output.send();
			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				int numClient = input.readInt();
				System.out.println("\nNum Client online:"+numClient);
				for(int i=0;i<numClient;i++){
					clients.add(input.readClientResource());
				}
				System.out.println("\tRicevuta lista dei possessori dei file");
			}
			else if(response.equals("NONE")){
				System.err.println("Nessuno possiede il file!!!");
			}
			else
			{
				System.err.println("Impossibile Ricevere il file!!!");
			}
			// deinizializzazione della connessione
			deinitConnection();
		}
		return clients;
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
	public String getUrl() 
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

	private boolean initConnection() throws UnknownHostException, IOException
	{
		String response;

		// creazione dell classi di connessione
		socket = new Socket(url.toString(), serverPort);

		output = new XDiskOutputStream(socket.getOutputStream());
		input = new XDiskInputStream(socket.getInputStream());

		if (sessionId == null)
		{
			// messaggio di saluto
			output.writeUTF("HELO");
			output.send();


			input.receive();
			response = input.readUTF();

			if (response.equals("HELO"))
			{
				response = input.readUTF();
				System.out.println("Server: " + response);

				output.writeUTF("LOGIN");
				output.writeUTF(userid);
				output.writeUTF(password);
				output.send();

				input.receive();
				response = input.readUTF();

				if (response.equals("OK"))
				{
					sessionId = input.readUTF();
					System.out.println("Login eseguito con successo, sessionId = " + 
							sessionId);
				}
				else
				{
					System.out.println("Login fallito...");
					return false;
				}
			}
		}
		else // già loggati
		{
			// messaggio di saluto
			System.out.println("Send HELO I");
			output.writeUTF("HELO I");
			output.writeUTF(sessionId);
			output.writeUTF(userid);
			output.send();


			input.receive();
			response = input.readUTF();

			if (!response.equals("OK"))
			{
				return false;
			}
		}

		return true;
	}

	private void deinitConnection() throws IOException
	{
		input.close();
		output.close();
		socket.close();
	}


}
