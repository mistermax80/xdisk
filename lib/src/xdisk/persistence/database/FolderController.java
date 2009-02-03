package xdisk.persistence.database;


import java.util.Collection;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;

public class FolderController {

	private FolderController() {
		super();
	}

	public static void load(Folder object) throws PersistenceException{FolderDAO.load(object);}

	public static void insert(Folder object) throws PersistenceException{FolderDAO.insert(object);}

	public static void delete(Folder object) throws PersistenceException{FolderDAO.delete(object);}

	public static void update(Folder object) throws PersistenceException{FolderDAO.update(object);}

	public static int removeAll() throws PersistenceException{
		return FolderDCS.removeAll();
	}

	public static Collection<Folder> getAll() throws PersistenceException{
		return FolderDCS.getAll();
	}
	
	public static Folder getRoot() throws PersistenceException{
		return FolderDCS.getRoot();
	}
	
	public static Collection<Folder> getFolder(Folder folder) throws PersistenceException{
		return FolderDCS.getFolders(folder);
	}

	public static int getLastCode() throws PersistenceException {
		return FolderDCS.getLastCode();
	}
	
	public static Collection<Folder> getFolders(String path) throws PersistenceException{
		LinkedList<Folder> folders = new LinkedList<Folder>();
		Folder parent = new Folder();
		Folder root = new Folder();
		String[] dirs = null;
		root = FolderController.getRoot();

		if(path.equalsIgnoreCase("/")){
			return FolderDCS.getFolders(root);
		}
		else{
			path=path.substring(1,path.length());
			dirs = path.split("/");
			parent = root;
			//for(int i=0;i<dirs.length;i++)
				//System.out.println(i+":"+dirs[i]);
			for(int i=0;i<dirs.length;i++){
				String nameDir = dirs[i];
				//System.out.println(i+"(index)-dir:"+nameDir+"-Analizzo i figli di "+parent.getNome());
				folders.clear();
				folders.addAll(FolderController.getFolder(parent));
				int j=0;
				boolean present=false;
				while(!present && j<folders.size()){
					Folder folder = folders.get(j);
				//	System.out.println(folder.getNome()+"=="+nameDir);
					if(folder.getNome().equalsIgnoreCase(nameDir)){
					//	System.out.println("Trovata directory corrispondente al current path:"+nameDir);
						parent=folder;
						present=true;
					}
					j++;
				}
				if(i==dirs.length-1 && present){
					return FolderDCS.getFolders(parent);
				}
			}
			return null;
		}
	}
	
	public static Folder getFolder(String path) throws PersistenceException{
		LinkedList<Folder> folders = new LinkedList<Folder>();
		Folder parent = new Folder();
		Folder root = new Folder();
		String[] dirs = null;
		root = FolderController.getRoot();

		if(path.equalsIgnoreCase("/")){
			return root;
		}
		else{
			path=path.substring(1,path.length());
			dirs = path.split("/");
			parent = root;
			for(int i=0;i<dirs.length;i++){
				String nameDir = dirs[i];
				folders.clear();
				folders.addAll(FolderController.getFolder(parent));
				int j=0;
				boolean present=false;
				while(!present && j<folders.size()){
					Folder folder = folders.get(j);
					if(folder.getNome().equalsIgnoreCase(nameDir)){
						parent=folder;
						present=true;
					}
					j++;
				}
				if(i==dirs.length-1 && present){
					return parent;
				}
			}
			return null;
		}
	}

	public static String getPath(Folder folder) throws PersistenceException {
		if(folder==null || folder.getParent()==0) return "/";
		String path = folder.getNome();
		Folder parent = new Folder();
		parent.setCodice(folder.getParent());
		FolderController.load(parent);
		return getPath(parent)+path+"/";
	}

}
