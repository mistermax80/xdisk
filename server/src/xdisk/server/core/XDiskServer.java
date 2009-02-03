package xdisk.server.core;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualFolder;
import xdisk.exception.PersistenceException;
import xdisk.net.Server;
import xdisk.net.ServerProcess;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;
import xdisk.persistence.Client;
import xdisk.persistence.File;
import xdisk.persistence.Folder;
import xdisk.persistence.Ownership;
import xdisk.persistence.User;
import xdisk.persistence.database.ClientController;
import xdisk.persistence.database.FileController;
import xdisk.persistence.database.FolderController;
import xdisk.persistence.database.OwnershipController;
import xdisk.persistence.database.UserController;
import xdisk.utils.Md5;

public class XDiskServer implements ServerProcess{

	private Server server;
	private int port;

	private XDiskOutputStream output;
	private XDiskInputStream input;

	/**
	 * @throws IOException
	 */
	public XDiskServer() throws IOException{
		super();
		port = 4444;
		server = new Server(this, port);
		server.setListenerThread(15);
		server.setMaxConnection(5);
		server.start();
		System.out.println("Server Started listening on port:"+port);
	}

	@Override
	public void request(Socket client) {
		System.out.println("\n\n\n\n=======================================================================");
		String ipClient = client.getInetAddress().getHostAddress();
		String hostname = client.getInetAddress().getCanonicalHostName();
		int portClient = client.getPort();
		Client userClient = new Client();

		System.out.println("Connection required from \n\thost:"+hostname+"\n\tip client:"+ipClient+"\n\tport:"+portClient);
		try {
			output = new XDiskOutputStream(client.getOutputStream());
			input = new XDiskInputStream(client.getInputStream());

			input.receive();
			String response = input.readUTF();
			System.out.println("\tResponse:"+response);
			if(response.equals("HELO")){
				//Fase Handshake
				System.out.print("\nFase HandShake HELO");
				XDiskOutputStream output = new XDiskOutputStream(client.getOutputStream());
				output.writeUTF("HELO");
				output.writeUTF("WELCOME IN XDISK!");			
				output.send();
				System.out.print(":OK");
				//Fase Login
				System.out.print("\nFase LOGIN");
				input.receive();
				if(input.readUTF().equals("LOGIN")){
					String userid = input.readUTF();
					String password = input.readUTF();
					User user = new User();
					user.setUsername(userid);
					//Carico dati utente
					UserController.load(user);
					//Controllo Password
					if(!UserController.checkPassword(user, password)){
						System.err.print(":ERROR");
						output.writeUTF("ERROR LOGIN");	
						output.send();
						client.close();
						//controllo se presente nel client db e lo rimuovo
						userClient.setUserid(user.getUsername());
						if(ClientController.isPresent(userClient)){
							ClientController.delete(userClient);
						}
					}else{
						System.out.print(":OK");
						//Assegnazione id
						System.out.print("\nFase Assegnazione ID");
						String id = Md5.md5(String.valueOf(System.currentTimeMillis()));
						output.writeUTF("OK");
						output.writeUTF(id);
						output.send();
						System.out.print(":OK\nID:"+id);
						//Salvataggio id nel db
						System.out.print("\nSalvataggio del DB dati client");
						userClient.setUserid(user.getUsername());
						userClient.setIdSession(id);
						userClient.setIpAddress(client.getInetAddress().getHostAddress());
						userClient.setPortNumber(client.getPort());
						userClient.setConnType("TCP");
						//inserimanto o aggiornamento
						if(ClientController.isPresent(userClient)){
							ClientController.update(userClient);
						}
						else{
							ClientController.insert(userClient);
						}
						System.out.print(":OK");
					}
				}
			}
			else if(response.equals("HELO I")){
				//Fase di HELO I richiesta di operazioni
				System.out.print("\nFase HELO I");
				//Controllo idSessione
				String id = input.readUTF();
				String userid = input.readUTF();
				System.out.println("\n  ID: "+id+"\n  UserId: "+userid);
				if(ClientController.checkSession(id,userid)){
					output.writeUTF("OK");
					System.out.print("Session Valide OK\n");
				}
				else{
					output.writeUTF("ERROR SESSION");
					System.err.print("Session Not Valide ERROR SESSION\n");
				}
				output.send();
				//Richiesta Operazioni
				input.receive();
				response= input.readUTF();
				if(response.equals("GETLIST")){//GETLIST
					System.out.print("\nRequest GETLIST");
					try{
						String path = input.readUTF();

						output.writeUTF("OK");
						//Invio lista folders
						LinkedList<Folder> folders = new LinkedList<Folder>();
						folders.addAll(FolderController.getFolders(path));
						//invio num risorse
						int numFolders = folders.size();
						System.out.print("\n\t#folders:"+numFolders);
						output.writeInt(numFolders);
						for(int i=0;i<numFolders;i++){
							VirtualFolder f = new VirtualFolder();
							f.setPath(path+folders.get(i).getNome()+"/");
							//invio di folder
							output.writeVirtualFolder(f);
						}
						//Invio lista folders
						LinkedList<File> files = new LinkedList<File>();
						files.addAll(FileController.getFile(path));
						//invio num risorse
						int numFiles = files.size();
						System.out.print("\t#files:"+numFiles);
						output.writeInt(numFiles);
						for(int i=0;i<numFiles;i++){
							VirtualFile file = new VirtualFile();
							file.setFilename(files.get(i).getName());
							file.setExtension(files.get(i).getExtension());
							file.setDescription(files.get(i).getDescription());
							file.setOwner(files.get(i).getOwner());
							file.setTags(files.get(i).getTags());
							file.setSize(files.get(i).getSize());
							file.setMime(files.get(i).getMime());
							file.setPath(path);
							//invio di file
							output.writeVirtualFile(file);
						}
						output.send();
						System.out.print(":OK");
					}
					catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("INSERT")){//INSERT
					System.out.print("\tRequest INSERT");
					try{
						//Ricevo il file
						VirtualFile vFile = input.readVirtualFile();
						//Controllo che il file non sia già presente nel path specificato
						LinkedList<File> files = new LinkedList<File>();
						files.addAll(FileController.getFile(vFile.getPath()));
						int i=0;
						boolean present=false;
						System.out.println("\n\t\tControllo se inserimento possibile: " + vFile.getPath()+vFile.getFilename()+"."+vFile.getExtension());
						while(!present && i<files.size()){
							if(files.get(i).getName().equalsIgnoreCase(vFile.getFilename()))
								present=true;
							i++;
						}
						if(!present){
							//Invio conferma del caricamento
							output.writeUTF("OK");
							//Inserisco il file nel db
							File file = new File();
							file.setCode(Md5.md5(vFile.getFilename()+System.currentTimeMillis()));
							file.setName(vFile.getFilename());
							file.setExtension(vFile.getExtension());
							file.setDescription(vFile.getDescription());
							file.setOwner(vFile.getOwner());
							file.setTags(vFile.getTags());
							file.setSize(vFile.getSize());
							file.setMime(vFile.getMime());
							file.setParent(FolderController.getFolder(vFile.getPath()).getCodice());
							FileController.insert(file);
							output.send();
							System.out.print(":OK");
						}
						else{
							//Invio Errore perchè file già presente
							output.writeUTF("PRESENT");
							output.send();
							System.err.print(":OK - file già presente, non è possibile aggiungere il file!");
						}
					}
					catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("SEARCH")){//SEARCH
					System.out.print("\nRequest SEARCH");
					try{
						//Letto il file da cercare
						String query = input.readUTF();
						LinkedList<File> files = new LinkedList<File>();
						files.addAll(FileController.search(query));
						int numFiles=files.size();
						output.writeUTF("OK");
						output.writeInt(numFiles);
						for(int i=0;i<numFiles;i++){
							VirtualFile file = new VirtualFile();
							file.setFilename(files.get(i).getName());
							file.setExtension(files.get(i).getExtension());
							file.setDescription(files.get(i).getDescription());
							file.setOwner(files.get(i).getOwner());
							file.setTags(files.get(i).getTags());
							file.setSize(files.get(i).getSize());
							file.setMime(files.get(i).getMime());
							Folder folder = new Folder();
							folder.setCodice(files.get(i).getParent());
							FolderController.load(folder);
							file.setPath(FolderController.getPath(folder));
							output.writeVirtualFile(file);
						}
						output.send();
						System.out.print(":OK");
					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("GET")){//GET
					System.out.print("\nRequest GET");
					try{
						output.writeUTF("OK");
						//Leggo il cononical name del file
						String canonicalName = input.readUTF();
						System.out.println("\n"+canonicalName);
						//Cerco se presente il file
						String[] dirs = canonicalName.substring(1).split("/");						
						String path = "/";
						if(dirs.length>0){
							for(int i=0;i<dirs.length-1;i++){
								path+=dirs[i]+"/";
							}
							String nameFile = dirs[dirs.length-1].split("\\p{Punct}")[0];
							LinkedList<File> files = new LinkedList<File>();
							files.addAll(FileController.getFile(FolderController.getFolder(path)));
							VirtualFile vFile = new VirtualFile();
							boolean present = false;
							int i=0;
							while(!present&&i<files.size()){
								File file = files.get(i);
								if(file.getName().equalsIgnoreCase(nameFile)){
									vFile.setFilename(file.getName());
									vFile.setExtension(file.getExtension());
									vFile.setDescription(file.getDescription());
									vFile.setOwner(file.getOwner());
									vFile.setTags(file.getTags());
									vFile.setSize(file.getSize());
									vFile.setMime(file.getMime());
									vFile.setPath(path);
									present=true;
								}
								i++;
							}
							if(present){
								output.writeVirtualFile(vFile);
							}
							else{
								//Svuoto l'output stream per inviare il messaggio cyhe il file non è presente
								output.reset();
								System.err.print("Il file non è presente");
								output.writeUTF("NOTPRESENT");
							}
							output.send();
						}
						else{
							//Svuoto l'output stream per inviare il messaggio cyhe il file non è presente
							output.reset();
							System.err.print("Il file non è presente");
							output.writeUTF("NOTPRESENT");
							output.send();
						}
					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("GETSOURCE")){//GETSOURCE
					System.out.print("\nRequest GETSOURCE");
					try{
						//Leggo il file per trovare chi lo possiede
						VirtualFile vFile = input.readVirtualFile();
						System.out.println("\tRicevuto il file");
						File file = new File();
						String nameFile=vFile.getFilename();
						int parent = FolderController.getFolder(vFile.getPath()).getCodice();
						file.setCode(FileController.getCode(nameFile,parent));
						FileController.load(file);
						//Interrogo il db
						LinkedList<Ownership> owners = new LinkedList<Ownership>();
						owners.addAll(OwnershipController.getUserOnlineByCode(file.getCode()));
						int numOwners = owners.size();
						output.writeUTF("OK");
						output.writeInt(numOwners);
						System.out.println("\tNum owners online:"+numOwners);
						for(int i=0;i<numOwners;i++){
							Client cli = new Client();
							cli.setUserid(owners.get(i).getUser());
							ClientController.load(cli);
							ClientResource clientRes = new ClientResource(cli.getIpAddress(),cli.getPortNumber());
							output.writeClientResource(clientRes);
						}
						output.send();
						System.out.println(":OK");
					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("NOTGOT")){//NOTGOT
					System.out.print("\nRequest NOTGOT");
					try{

					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("GOT")){//GOT
					System.out.print("\nRequest GOT");
					try{

					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(response.equals("ISVALIDETIKET")){//ISVALIDETIKET
					System.out.print("\nRequest ISVALIDETIKET");
					try{

					}catch (Exception e) {
						//Svuoto l'output stream per inviare il messaggio di errore
						output.reset();
						System.err.print("Non è possibile soddisfare la richiesta");
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else{
					System.err.print("PROTOCOL COMMAND UNKNOWN!!!");
				}				
			}
			else{
				System.err.print("Protocol Command unknow!!!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n=======================================================================");
	}

	@Override
	public void requestError(Socket client) {	
	}

	public static void main(String[] args){
		try {
			new XDiskServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
