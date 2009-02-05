package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import xdisk.VirtualFile;
import xdisk.VirtualFolder;
import xdisk.VirtualResource;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.FileModel;
import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.database.FileController;

public class Download extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	JPanel panel1 = null;
	JPanel panel2 = null;

	private JButton button;

	private JTree tree; 
	private JScrollPane panel3;

	private VirtualDisk disk;
	private String current_path;
	private VirtualFile current_file;

	private DefaultListModel listModel;

	private JList list;

	public Download() {
		super(new BorderLayout());
		panel1 = new JPanel(new GridLayout(1,2));
		panel2 = new JPanel();
		button = new JButton("Download");
		button.addActionListener(new ActionDownload());

		disk = new VirtualDisk("xdisk","disco virtuale","localhost",4444,"http://xx", 8080, "ciips", "c");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("/");
		tree(root);

		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.addListSelectionListener(new ActionSelectFile());
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new ActionSelectFolder());

		panel3 = new JScrollPane(list);

		panel1.add(tree);
		panel1.add(panel3);
		panel2.add(button);

		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
	}

	public class ActionDownload implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(current_file!=null)
					disk.getFile(current_file.getPath()+current_file.getFilename()+"."+current_file.getExtension());
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						"Errore nel recupero del file dal server!!!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class ActionSelectFolder implements TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent e) {
			if(tree.getSelectionPath()!=null){
				try {
					TreePath treePath = tree.getSelectionPath();
					String path=""; 
					Object[] paths = treePath.getPath();
					for(int i=0;i<paths.length;i++){
						if(i==0)
							path+=paths[i].toString();
						else
							path+=paths[i].toString()+"/";
					}
					current_path=path;
					System.out.println(current_path);
					listModel.clear();
					LinkedList<VirtualResource> resources = new LinkedList<VirtualResource>();
					resources.addAll(disk.getList(current_path));
					for(int i=0;i<resources.size();i++){
						if (((VirtualResource)resources.get(i)).isFile()) {
							VirtualFile file = (VirtualFile) resources.get(i);
							listModel.addElement(new FileModel(file));					
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, 
							"Errore nel recupero della lista dei file della directory selezionata!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void tree(DefaultMutableTreeNode parent){
		try {
			disk.connect();
			//System.out.println(parent.toString());
			LinkedList<VirtualResource> resources = new LinkedList<VirtualResource>();
			TreeNode[] paths = parent.getPath();
			String path="";
			//System.out.println(paths.length);
			if(paths.length==1){
				path="/";
				resources.addAll(disk.getList(path));
			}
			else{
				for(int i=0;i<paths.length;i++){
					//System.out.println("paths "+paths[i]);
					if(i==0)
						path+=paths[i];
					else
						path+=paths[i]+"/";
				}
				resources.addAll(disk.getList(path));
			}
			System.out.println("path"+path);
			for(int i=0;i<resources.size();i++){
				if (((VirtualResource)resources.get(i)).isDirectory()) {
					VirtualFolder folder = (VirtualFolder) resources.get(i);
					String[] dirs = folder.getPath().split("/");
					String nameDir= dirs[dirs.length-1];
					//System.out.println("nameDir "+nameDir);
					DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(nameDir);
					parent.add(nodo);
					tree(nodo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero della struttura del Disco Virtuale", "Errore", JOptionPane.ERROR_MESSAGE);
		}	
	}

	public class ActionSelectFile implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			if(list.getSelectedIndex()>-1){
				FileModel file = (FileModel)list.getSelectedValue();
				current_file = file.getFile();
			}
		}

	}
}



