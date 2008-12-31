package xdisk.utils;

import xdisk.persistence.Disk;
import xdisk.persistence.User;
import xdisk.persistence.database.DiskController;
import xdisk.persistence.database.UserController;

public class Test {
	public static void main(String[] args) throws Exception{
		DiskController.removeAll();		
		UserController.removeAll();
		
		User utente = new User("picchio","massimo","max","max@xdisk.org");
		UserController.insert(utente);
		System.out.println("Inserimento avvenuto con successo!!!"+utente);

		Disk disco = new Disk("disco1",123,"picchio",23);
		DiskController.insert(disco);
		System.out.println("Inserimento avvenuto con successo!!!"+disco);
		/*DiskController.delete(disco);
		System.out.println("Cancellazione avvenuta con successo!!!"+disco);*/
		
		System.out.println("JSP inizio");
		User user = UserController.login("picchio", "max");
		System.out.println(user);
	}
}