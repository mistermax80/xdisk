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
				Fases.handshake(client);
				//Fase Login
				Fases.login(userClient, client);
			}
			else if(response.equals("HELO I")){
				//Fase di HELO I richiesta di operazioni
				System.out.print("\nFase HELO I");
				//Controllo idSessione
				Fases.checkSession(userClient, input, output);
				input.receive();
				//Richiesta Operazione
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
					System.err.print("Protocol Command unknow!!!");
				}				
				output.send();
				System.out.print(":OK");
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
