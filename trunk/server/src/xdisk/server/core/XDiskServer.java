package xdisk.server.core;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

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
import xdisk.persistence.User;
import xdisk.persistence.database.ClientController;
import xdisk.persistence.database.FileController;
import xdisk.persistence.database.FolderController;
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
		String ipClient = client.getInetAddress().getHostAddress();
		String hostname = client.getInetAddress().getCanonicalHostName();
		int portClient = client.getPort();
		Client userClient = new Client();

		System.out.println("\n\nConnection required from \nhost:"+hostname+"\nip client:"+ipClient+"\nport:"+portClient);
		try {
			output = new XDiskOutputStream(client.getOutputStream());
			input = new XDiskInputStream(client.getInputStream());

			input.receive();
			String response = input.readUTF();
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
				input.receive();
				//Richiesta Operazioni
				if(input.readUTF().equals("GETLIST")){//GETLIST
					System.out.print("\nRequest GETLIST");
					try{
						String path = input.readUTF();

						output.writeUTF("OK");
						//Invio lista folders
						LinkedList<Folder> folders = new LinkedList<Folder>();
						folders.addAll(FolderController.getFolder(path));
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
							System.out.println(files.get(i));
							VirtualFile file = new VirtualFile();
							file.setFilename(files.get(i).getName());
							file.setExtension("extension");
							file.setDescription("description");
							file.setOwner(files.get(i).getAuthor());
							file.setTags("tag");
							file.setSize(files.get(i).getDimension());
							file.setMime("mime");
							file.setPath(path);
							//invio di file
							output.writeVirtualFile(file);
						}
						output.send();
						System.out.print(":OK");
					}
					catch (Exception e) {
						//Da pulire l'output stream ????COME????
						// TODO IMPORTANTE
						output.writeUTF("ERROR");
						output.send();
						e.printStackTrace();
					}
				}
				else if(input.readUTF().equals("INSERT")){//INSERT
					System.out.print("\nRequest GETLIST");
				}
				else if(input.readUTF().equals("SERACH")){//SEARCH
					System.out.print("\nRequest SERACH");
				}
				else if(input.readUTF().equals("GET")){//GET
					System.out.print("\nRequest GET");
				}
				else if(input.readUTF().equals("GETSOURCE")){//GETSOURCE
					System.out.print("\nRequest GETSOURCE");
				}
				else if(input.readUTF().equals("NOTGOT")){//NOTGOT
					System.out.print("\nRequest NOTGOT");
				}
				else if(input.readUTF().equals("GOT")){//GOT
					System.out.print("\nRequest GOT");
				}
				else if(input.readUTF().equals("ISVALIDETIKET")){//ISVALIDETIKET
					System.out.print("\nRequest ISVALIDETIKET");
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
