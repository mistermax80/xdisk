package xdisk;

import javax.faces.event.ActionEvent;
import javax.swing.JOptionPane;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;
import xdisk.persistence.database.FileController;
import xdisk.persistence.database.FolderController;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.javaee.blueprints.components.ui.components.GraphComponent;
import com.sun.javaee.blueprints.components.ui.model.Graph;
import com.sun.javaee.blueprints.components.ui.model.Node;

public class FolderBean {

	private static Logger LOGGER = Logger.getLogger("demo.model");    

	Graph treeGraph = null;

	private static String link = "addFolder.jsf";
	private static String icon = "folder_16_pad.gif";
	private static String icon_file = "folder_16_pad.gif";

	public FolderBean() {
	}

	public Graph getTreeGraph() throws PersistenceException {
		if (treeGraph == null) {
			
			Folder rootFolder = FolderController.getRoot();
			String name = String.valueOf(rootFolder.getCodice());
			String label = rootFolder.getNome();
			Node root = new Node(name, label+"-"+name, link,  icon, false, true);
			treeGraph = new Graph(root);			
			addChilds(root);
		}
		return treeGraph;
	}
	
	public Graph getTreeGraphFile() throws PersistenceException {
		if (treeGraph == null) {
			
			Folder rootFolder = FolderController.getRoot();
			String name = String.valueOf(rootFolder.getCodice());
			String label = rootFolder.getNome();
			Node root = new Node(name, label+"-"+name, link,  icon, false, true);
			treeGraph = new Graph(root);			
			addChildsFile(root);
		}
		return treeGraph;
	}
	
	public static void addChildsFile(Node parent) throws PersistenceException {
		Node child = null;
		Node file = null;
		
		Folder folder = new Folder();
		folder.setCodice(Integer.parseInt(parent.getName()));
		FolderController.load(folder);
		LinkedList<Folder> childs = new LinkedList<Folder>();
		childs.addAll(FolderController.getFolder(folder));
		LinkedList<File> files = new LinkedList<File>();
		files.addAll(FileController.getFile(folder));
		System.out.println("files:"+files);

		for(int i=0;i<childs.size();i++){
			String name = String.valueOf(childs.get(i).getCodice());
			String label = childs.get(i).getNome();
			child = new Node(name, label+"-"+name, link, icon , false, true);
			parent.addChild(child);
			addChildsFile(child);
		}

		for(int i=0;i<files.size();i++){
			files.addAll(FileController.getFile(childs.get(0)));
			String name = String.valueOf(files.get(i).getCode());
			String label = files.get(i).getName();
			file = new Node("f_"+name, "file:"+label+"-"+name, link, icon_file , false, true);
			System.out.println("file:"+file);
			parent.addChild(file);
		}
	}

	public static void addChilds(Node parent) throws PersistenceException {
		Node child = null;
		
		Folder folder = new Folder();
		folder.setCodice(Integer.parseInt(parent.getName()));
		LinkedList<Folder> childs = new LinkedList<Folder>();
		childs.addAll(FolderController.getFolder(folder));
		
		for(int i=0;i<childs.size();i++){
			String name = String.valueOf(childs.get(i).getCodice());
			String label = childs.get(i).getNome();
			child = new Node(name, label+"-"+name, link, icon , false, true);
			parent.addChild(child);
			addChilds(child);
		}
	}
	
	public static void addFolder(){
		JOptionPane.showInputDialog("Aggiungi Cartella", "Nome cartella");
	}

	public void setTreeGraph(Graph newTreeGraph) {
		this.treeGraph = newTreeGraph;
	}


	/*
	 * Processes the event queued on the graph component when a particular
	 * node in the tree control is to be expanded or collapsed.
	 */
	public void processGraphEvent(ActionEvent event) {
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("TRACE: GraphBean.processGraphEvent ");
		}
		Graph graph = null;
		GraphComponent component = (GraphComponent) event.getSource();
		String path = (String) component.getAttributes().get("path");
		System.out.println(component.getAttributes().values());
		// Acquire the root node of the graph representing the menu
		graph = (Graph) component.getValue();
		if (graph == null) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("ERROR: Graph could not located in scope ");
			}
		}
		// Toggle the expanded state of this node
		Node node = graph.findNode(path);
		if (node == null) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.severe("ERROR: Node " + path + "could not be located. ");
			}
			return;
		}
	}

}
