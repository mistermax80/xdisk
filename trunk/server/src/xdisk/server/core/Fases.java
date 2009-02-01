package xdisk.server.core;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

import xdisk.exception.PersistenceException;
import xdisk.net.XDiskInputStream;
import xdisk.net.XDiskOutputStream;
import xdisk.persistence.Client;
import xdisk.persistence.User;
import xdisk.persistence.database.ClientController;
import xdisk.persistence.database.UserController;
import xdisk.utils.Md5;

public class Fases {
	
	public static void handshake(Socket client) throws IOException{
		System.out.print("\nFase HandShake HELO");
		XDiskOutputStream output = new XDiskOutputStream(client.getOutputStream());
		output.writeUTF("HELO");
		output.writeUTF("WELCOME IN XDISK!");			
		output.send();
		System.out.print(":OK");
	}
	
	public static void login(Client userClient, Socket client) throws IOException, PersistenceException, NoSuchAlgorithmException{
		XDiskOutputStream output = new XDiskOutputStream(client.getOutputStream());
		XDiskInputStream input = new XDiskInputStream(client.getInputStream());

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
	
	public static void checkSession(Client userClient, XDiskInputStream input, XDiskOutputStream output) throws IOException, PersistenceException{
		String id = input.readUTF();
		System.out.println("\nID:"+id);
		if(ClientController.checkSession(id)){
			output.writeUTF("OK");
			System.out.print("Session Valide OK\n");
		}
		else{
			output.writeUTF("ERROR SESSION");
			System.err.print("Session Not Valide ERROR SESSION\n");
		}
		output.send();
	}
}
