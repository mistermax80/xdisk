package xdisk.server.core;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
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
import xdisk.persistence.database.UserController;

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
		
		System.out.println("\n\nConnection required from \nhost:"+hostname+"\nip client:"+ipClient+"\n port:"+portClient);
		try {
			output = new XDiskOutputStream(client.getOutputStream());
			input = new XDiskInputStream(client.getInputStream());

			input.receive();
			String response = input.readUTF();
			if(response.equals("HELO")){
				//Fase Handshake
				System.out.print("\nFase HandShake HELO");
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
						String id = md5(String.valueOf(System.currentTimeMillis()));
						output.writeUTF("OK");
						output.writeUTF(id);
						output.send();
						System.out.print(":OK\nID:"+id);
						//Salvataggio id nel db
						System.out.print("\nSalvataggio del DB dati client");
						userClient.setUserid(user.getUsername());
						userClient.setIdSession(id);
						userClient.setIpAddress(ipClient);
						userClient.setPortNumber(portClient);
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
				System.out.println("\nID:"+input.readUTF());
				output.writeUTF("OK");
				output.send();
				input.receive();
				if(input.readUTF().equals("GETLIST")){
					System.out.print("\nRequest GETLIST");
					String path = input.readUTF();
					Folder folder = new Folder();
					folder.setCodice(23);
					LinkedList<File> files = new LinkedList<File>();
					output.writeUTF("OK");
					output.writeInt(files.size());
					files.addAll(FileController.getFile(folder));
					for(int i=0;i<files.size();i++){
						VirtualFolder f = new VirtualFolder();
						f.setPath(files.get(i).getName());
						output.writeVirtualFolder(f);
					}				
					output.writeInt(files.size());
					files.addAll(FileController.getFile(folder));
					for(int i=0;i<files.size();i++){
						VirtualFile f = new VirtualFile();
						f.setFilename(files.get(i).getName());
						output.writeVirtualFile(f);
					}
				}
				else{
					System.out.print("\nComando sconosciuto");				
				}
				System.out.print("\nFUORI dal Request GETLIST");
				output.send();
				System.out.print(":OK");
			}
			else{
				System.out.print("Protocol Command unknow!!!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void requestError(Socket client) {	
	}

	public String md5(String s){
		String md5="";
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			md5 = new BigInteger(1,m.digest()).toString(16);
			//System.out.println("MD5: "+md5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	public static void main(String[] args) throws IOException {
		new XDiskServer();
	}
}
