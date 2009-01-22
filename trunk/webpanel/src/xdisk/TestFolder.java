package xdisk;

import xdisk.persistence.Folder;
import xdisk.persistence.database.FolderController;

public class TestFolder {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Folder rootFolder = FolderController.getRoot();
		System.out.println(rootFolder);
		
		FolderBean.addFolder();
		//FolderBean.addChilds(parent)
		

	}

}
