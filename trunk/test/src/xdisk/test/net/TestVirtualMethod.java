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
		String path = "/prova/picchio/ciao/";
		System.out.println(path);
		LinkedList<File> files = new LinkedList<File>();
		files.addAll(FileController.getFile(path));
		System.out.println("======================================================");
		System.out.println(files);
		System.out.println("======================================================");
		LinkedList<Folder> folders = new LinkedList<Folder>();
		folders.addAll(FolderController.getFolders(path));
		System.out.println("======================================================");
		System.out.println(folders);
		System.out.println("======================================================");
		//path = "/";
		System.out.println(path);
		System.out.println(FolderController.getFolder(path));
		System.out.println("======================================================");
		String query = "tag";
		System.out.println(FileController.search(query));
		System.out.println("======================================================");
		System.out.println(folders.get(0));
		String p = FolderController.getPath(folders.get(0));
		System.out.println("path:"+p+"|");
	}
}
