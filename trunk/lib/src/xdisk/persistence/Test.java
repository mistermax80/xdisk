package xdisk.persistence;

import xdisk.persistence.database.DiskController;

public class Test {
	public static void main(String[] args) throws Exception{
		Disk disco = new Disk("disco1",123,"picchio",23);
		DiskController.insert(disco);
		System.out.println("Inserimento avvenuto con successo!!!");
		DiskController.delete(disco);
		System.out.println("Cancellazione avvenuta con successo!!!");
		
	}
}