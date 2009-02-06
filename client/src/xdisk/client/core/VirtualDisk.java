package xdisk.client.core;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualFolder;
import xdisk.VirtualResource;
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
public class VirtualDisk implements Runnable
{
	private String name;
	private String serverAddress;
	private int serverPort;
	private String webPanelAddress;
	private int webPanelPort;
	private String description; 

	private String userid;
	private String password;
	private int localPort;

	private String sessionId;

	private Socket socket;
	private XDiskOutputStream output;
	private XDiskInputStream input;

	private boolean connect;
	
	private Thread keepAliveThread;
	
	private static final int KEEP_ALIVE_SLEEP = 1000;//1000 * 60;

	public VirtualDisk() 
	{
		super();
	}	


	/**
	 * Crea un nuovo disco virtuale.
	 * 
	 * @param name il nome del disco virtuale
	 * @param description la descrizione del disco 
	 * @param serverAddress l'indirizzo di connessione al disco virtuale
	 * @param serverPort la porta di connessione al disco virtuale
	 * @param webPanelAddress l'indirizzo di connessione al pannello web del 
	 * 			disco virtuale
	 * @param webPanelPort la porta di connessione al pannello web del 
	 * 			disco virtuale
	 * @param username lo userid di connessione al disco
	 * @param password la password per la connessione al disco
	 */
	public VirtualDisk(String name, String description, 
			String serverAddress, int serverPort,
			String webPanelAddress, int webPanelPort, 
			String userid, String password, int localPort) 
	{
		this.name = name;
		this.description = description;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.webPanelAddress = webPanelAddress;
		this.webPanelPort = webPanelPort;
		this.userid = userid;
		this.password = password;
		this.localPort = localPort;
	}

	/**
	 * Effettua la connessione al disco
	 * @return true se la connessione riesce, false altrimenti
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized boolean connect() throws UnknownHostException, IOException
	{
		// inizializzazione della connessione
		initConnection();		

		// deinizializzazione della connessione
		deinitConnection();
		
		keepAliveThread = new Thread(this);
		keepAliveThread.start();
		
		return true;
	}

	/**
	 * Effettua la disconnessione dal server del disco virtuale
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized void disconnect() throws UnknownHostException, IOException
	{
		keepAliveThread = null;
		
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta DISCONNECT...");
			// invio la richiesta al server
			output.writeUTF("DISCONNECT");
			output.send();
			// deinizializzazione della connessione
			deinitConnection();
		}
		
		sessionId=null;
		connect=false;
		System.out.println("USCITO DSCONNECT");
	}

	/**
	 * Invia il segnale di keep alive al server, per segnalare la presenza 
	 * del client nella rete virtuale del disco.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */	
	public synchronized void keepAlive() throws UnknownHostException, IOException
	{
		System.out.println("ENTRO NEL KEEP ALIVE");
		if (sessionId == null)
			return;
		
		String response="";
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.print("Invio richiesta KEEPALIVE...");
			// invio la richiesta al server
			output.writeUTF("KEEPALIVE");
			output.send();
//			System.out.println("Messaggio inviato!!!");
			input.receive();
//			System.out.println("Messaggio ricevuto!!");
			response=input.readUTF();
			if(response.equals("OK")){
				connect=true;
				System.out.println(":OK");
			}
			else{
				connect=false;
				sessionId=null;
				System.err.println(":ERROR");
			}
			// deinizializzazione della connessione
			deinitConnection();
		}
		System.out.println("ESCO NEL KEEP ALIVE");
	}

	/**
	 * Ritorna la lista dei file e delle directory di uno specifico path 
	 * @param path il path di cui ottenere la lista dei file e delle directory
	 * @return La lista dei file e delle directory
	 * @throws IOException 
	 */
	public synchronized ArrayList<VirtualResource> getList(String path) throws IOException
	{
		String response;
		ArrayList<VirtualResource> result = new ArrayList<VirtualResource>();

		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta path:"+path);
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
	public synchronized boolean insertFile(VirtualFile file) throws UnknownHostException, IOException
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
	public synchronized ArrayList<VirtualFile> search(String query) throws IOException
	{
		ArrayList<VirtualFile> result = new ArrayList<VirtualFile>();
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta SEARCH...query:"+query);
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
	public synchronized VirtualFile getFile(String canonicalName) throws IOException
	{
		VirtualFile file = new VirtualFile();
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta GET...file:"+canonicalName);
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
	public synchronized ArrayList<ClientResource> getSource(VirtualFile file) throws UnknownHostException, IOException
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
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized boolean gotFile(VirtualFile file) throws UnknownHostException, IOException
	{
		boolean ret=false;
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta GOT...");
			// invio la richiesta al server
			output.writeUTF("GOT");
			output.writeVirtualFile(file);
			output.send();


			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				ret=true;
				System.out.println("Presenza file inviata correttamente!");
			}
			else if(response.equals("NOTPRESENT")){
				System.err.println("Il file non è presente puoi inserirlo!!!");
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
	 * Indica al server che il file non è presente, o non è più presente in 
	 * locale, e che quindi non può più fornire alla rete la risorsa.
	 * @param file il file che non si può fornire
	 * @return true se la segnalazione riesce, false altrimenti.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized boolean notGotFile(VirtualFile file) throws UnknownHostException, IOException
	{
		boolean ret=false;
		String response;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta NOTGOT...");
			// invio la richiesta al server
			output.writeUTF("NOTGOT");
			output.writeVirtualFile(file);
			output.send();


			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				ret=true;
				System.out.println("Rimozione presenza file inviata correttamente!");
			}
			else if(response.equals("NOTPRESENT")){
				System.err.println("Il file non è presente nel sistema!");
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
	 * Chiede al server, se il ticket di scricamento richiesto da altri client
	 * è valido. Se è valido, ritorna il fil viruale da scaricare.
	 * @param ticket il ticket di scaricamento
	 * @return il file virtuale da fornire se il ticket è valido, null in caso 
	 * contrario. 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public synchronized VirtualFile isValidTicket(String ticket) throws UnknownHostException, IOException
	{
		String response;
		VirtualFile file = null;
		// inizializzazione della connessione
		if (initConnection())
		{
			System.out.println("Invio richiesta ISVALIDETIKET: "+ticket);
			// invio la richiesta al server
			output.writeUTF("ISVALIDETIKET");
			output.writeUTF(ticket);
			output.send();


			// ricevo e analizzo la risposta
			input.receive();
			response = input.readUTF();
			System.out.println("Response:"+response);
			if (response.equals("OK"))
			{
				file = input.readVirtualFile();
				System.out.println("Virtual File ricevuto correttamente.");
			}
			else if(response.equals("TIKETNOVALIDE")){
				System.err.println("Il ticket non è Valido!!!");
			}
			else
			{
				System.err.println("Impossibile Ottenere il file!!!");
			}

			// deinizializzazione della connessione
			deinitConnection();
		}
		return file;
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
		return serverAddress;
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
	 * Ritorna l'indirizzo del pannello web del server virtuale
	 * @return l'indirizzo del pannello web del server virtuale
	 */
	public String getWebPanelAddress() 
	{
		return webPanelAddress;
	}

	/**
	 * Ritorna la porta del pannello web
	 * @return la porta del pannello web 
	 */
	public int getWebPanelPort() 
	{
		return webPanelPort;
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



	public String getServerAddress() 
	{
		return serverAddress;
	}


	public void setServerAddress(String serverAddress) 
	{
		this.serverAddress = serverAddress;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}



	public void setServerPort(int serverPort) 
	{
		this.serverPort = serverPort;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setWebPanelAddress(String webPanelAddress) 
	{
		this.webPanelAddress = webPanelAddress;
	}

	public void setWebPanelPort(int webPanelPort) 
	{
		this.webPanelPort = webPanelPort;
	}

	public void setUserid(String userid) 
	{
		this.userid = userid;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	private boolean initConnection() throws UnknownHostException, IOException
	{
		String response;

		// creazione dell classi di connessione
		socket = new Socket(serverAddress.toString(), serverPort);

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
					connect=true;
					return true;
				}
				else
				{
					System.out.println("Login fallito...");
					sessionId=null;
					connect=false;
					return false;
				}
			}
		}
		else // già loggati
		{
			// messaggio di salutoconnect=false;
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


	public boolean isConnect() {
		return connect;
	}


	@Override
	public void run() 
	{
		// invia il comando keep alive al server per indicare la presenza nella
		// rete virtuale
		Thread thisThread = Thread.currentThread();
            
		while (keepAliveThread == thisThread)
		{
			try 
			{
				Thread.sleep(KEEP_ALIVE_SLEEP);
				keepAlive();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
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

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}
	
	public String toString(){
		return name+" - "+description;
	}
}
