package xdisk;

import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;
import xdisk.persistence.database.FolderController;

public class Nodo {
	public String key;
	public Nodo parent;
	public Nodo nextSibling;
	public Nodo firstChild;
	
	public Nodo(String key, Nodo parent, Nodo sibiling, Nodo firstChild) {
		super();
		this.key = key;
		this.parent = parent;
		this.nextSibling = sibiling;
		this.firstChild = firstChild;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Nodo getParent() {
		return parent;
	}

	public void setParent(Nodo parent) {
		this.parent = parent;
	}

	public Nodo getSibiling() {
		return nextSibling;
	}

	public void setSibiling(Nodo sibiling) {
		this.nextSibling = sibiling;
	}

	public Nodo getFirstChild() {
		return firstChild;
	}

	public void setFirstChild(Nodo firstChild) {
		this.firstChild = firstChild;
	}
	
	public static void inOrdine(Nodo nodo){

		if(Nodo.isLeaf(nodo)) {        //sono foglia?
			System.out.print(nodo.key+"-"); //mi stampo
			return;							//ritorno
		}

		if(Nodo.isLeaf(nodo.firstChild)){ //mio figlio è foglia?
			System.out.print(nodo.firstChild.key+"-"); //stampo mio figlio
		}
		else inOrdine(nodo.firstChild);// ric1   inOrdine(mio figlio)

		System.out.print(nodo.key+"-");		// mi stampo

		while(nodo.firstChild.nextSibling!=null){ //se mio figlio ha fratelli
			inOrdine(nodo.firstChild.nextSibling);//ric2  inOrdine(fratello di mio figlio)
			nodo.firstChild.nextSibling = nodo.firstChild.nextSibling.nextSibling;
		}
	}

	private static boolean isLeaf(Nodo nodo) {
		return nodo.getFirstChild()==null;
	}
	
	public static void main(String args[]) throws PersistenceException{
		Nodo root = null;
		
		LinkedList<Folder> folders = new LinkedList<Folder>();
		folders.addAll(FolderController.getAll());
		
		for(int i=0;i<folders.size();i++){
			System.out.println(folders.get(i));
			if(folders.get(i).getParent()==0){
				System.out.println("___________\ntrovata radice:"+folders.get(i));
			}
		}
	}
}
















