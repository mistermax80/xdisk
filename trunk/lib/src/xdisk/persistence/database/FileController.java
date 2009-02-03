package xdisk.persistence.database;

import java.util.Collection;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;

public class FileController {

	public static void load(File file) throws PersistenceException{FileDAO.load(file);}

	public static void insert(File file) throws PersistenceException{FileDAO.insert(file);}

	public static void delete(File file) throws PersistenceException{FileDAO.delete(file);}

	public static void update(File file) throws PersistenceException{FileDAO.update(file);}

	public static int removeAll() throws PersistenceException{
		return FileDCS.removeAll();
	}

	public static Collection<File> getAll() throws PersistenceException{
		return FileDCS.getAll();
	}

	public static Collection<File> getFile(Folder folder) throws PersistenceException{
		return FileDCS.getFile(folder);
	}

	public static Collection<File> getFile(String path) throws PersistenceException{
		LinkedList<Folder> folders = new LinkedList<Folder>();
		Folder parent = new Folder();
		Folder root = new Folder();
		String[] dirs = null;
		root = FolderController.getRoot();

		if(path.equalsIgnoreCase("/")){
			return FileDCS.getFile(root);
		}
		else{
			path=path.substring(1,path.length());
			dirs = path.split("/");
			parent = root;
			//for(int i=0;i<dirs.length;i++)
				//System.out.println(i+":"+dirs[i]);
			for(int i=0;i<dirs.length;i++){
				String nameDir = dirs[i];
		//		System.out.println(i+"(index)-dir:"+nameDir+"-Analizzo i figli di "+parent.getNome());
				folders.clear();
				folders.addAll(FolderController.getFolder(parent));
				int j=0;
				boolean present=false;
				while(!present && j<folders.size()){
					Folder folder = folders.get(j);
			//		System.out.println(folder.getNome()+"=="+nameDir);
					if(folder.getNome().equalsIgnoreCase(nameDir)){
				//		System.out.println("Trovata directory corrispondente al current path:"+nameDir);
						parent=folder;
						present=true;
					}
					j++;
				}
				if(i==dirs.length-1 && present){
					return FileDCS.getFile(parent);
				}
			}
			return null;
		}
	}
}




