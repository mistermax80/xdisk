package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import xdisk.VirtualFile;
import xdisk.VirtualResource;
import xdisk.client.core.VirtualDisk;
import xdisk.client.core.VirtualDiskManager;
import xdisk.client.data.FileModel;
import xdisk.client.data.TreeModel;

public class Download extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	JPanel panel1 = null;
	JPanel panel2 = null;

	private JButton button;
	private JButton button2;

	private JTree tree; 
	private JScrollPane panel3;

	private VirtualDisk disk;
	private String current_path;
	private VirtualFile current_file;

	private DefaultMutableTreeNode root;
	private DefaultListModel listModel;

	private JList list;

	public Download(int index) {
		super(new BorderLayout());
		
		disk = VirtualDiskManager.getInstance().get(index);
		
		panel1 = new JPanel(new GridLayout(1,2));
		panel2 = new JPanel();
		button = new JButton("Download");
		button.addActionListener(new ActionDownload());
		button2 = new JButton("Aggiorna");
		button2.addActionListener(new ActionUpdate());

		root = new DefaultMutableTreeNode("/");
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ActionSelectFile());
		tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new ActionSelectFolder());

		panel3 = new JScrollPane(list);

		panel1.add(tree);
		panel1.add(panel3);
		panel2.add(button2);
		panel2.add(button);

		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
	}

	public class ActionDownload implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(current_file!=null)
					disk.getFile(current_file.getPath()+current_file.getFilename()+"."+current_file.getExtension());
				else
					JOptionPane.showMessageDialog(null, 
							"Seleziona un file da scaricare!!!", "Seleziona", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						"Errore nel recupero del file dal server!!!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public class ActionUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TreeModel.tree(root,disk);
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
			//TODO controlla
			//else if(tree.getSelectionPath().getPath()[0].equals("/")){
				//System.out.println(root);
			//}
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



