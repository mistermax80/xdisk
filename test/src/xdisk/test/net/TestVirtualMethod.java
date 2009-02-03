package xdisk.test.net;

import java.util.LinkedList;
import xdisk.persistence.File;
import xdisk.persistence.Folder;
import xdisk.persistence.database.FileController;
import xdisk.persistence.database.FolderController;

public class TestVirtualMethod {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LinkedList<File> files = new LinkedList<File>();
		files.addAll(FileController.getFile("/prova/pppp"));
		System.out.println("======================================================");
		System.out.println(files);
		System.out.println("======================================================");
		LinkedList<Folder> folders = new LinkedList<Folder>();
		folders.addAll(FolderController.getFolder("/prova/ppppppp"));
		System.out.println("======================================================");
		System.out.println(folders);
		System.out.println("======================================================");
		
	}

}
