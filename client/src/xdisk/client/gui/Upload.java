package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import xdisk.VirtualFile;
import xdisk.VirtualFolder;
import xdisk.VirtualResource;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.FileModel;
import xdisk.client.data.TreeModel;
import xdisk.client.gui.Download.ActionSelectFolder;

public class Upload extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JFileChooser fileCh;
	private JPanel panel;
	private JPanel panelButt;

	private JLabel tagsLabel;
	private JLabel descrLabel;

	private JButton pathButton;

	private JTextField tags;
	private JTextField descr;
	private JTextField path;

	private VirtualDisk disk;
	private String current_path;

	JTree tree;

	public Upload() {
		super(new BorderLayout());

		disk = new VirtualDisk("xdisk","disco virtuale","localhost",4444,"http://xx", 8080, "ciips", "c");
		panel = new JPanel(new GridLayout(3,2));
		panelButt = new JPanel(new BorderLayout());

		tagsLabel = new JLabel("Tag:");
		pathButton = new JButton("Path:");
		descrLabel = new JLabel("Descrizione:");

		tags = new JTextField();
		path = new JTextField();
		descr = new JTextField();

		fileCh = new JFileChooser();

		path.setEditable(false);
		pathButton.addActionListener(new ActionPath());
		panel.add(tagsLabel);
		panel.add(tags);
		panel.add(descrLabel);
		panel.add(descr);
		panelButt.add(pathButton,BorderLayout.WEST);
		panel.add(panelButt);
		panel.add(path);

		this.add(panel,BorderLayout.NORTH);
		this.add(fileCh);

		File file = fileCh.getSelectedFile();

		String userid = "ciips";
		VirtualDisk disk = new VirtualDisk("xdisk","disco virtuale","localhost",4444,"http://xx", 8080, userid, "c");

		if(file!=null){
			VirtualFile vFile = new VirtualFile();
			vFile.setDescription(descr.getText());
			vFile.setExtension("extension");
			vFile.setFilename(file.getName());
			vFile.setMime("<mime>");
			vFile.setOwner(userid);
			vFile.setPath("/");
			vFile.setSize((int) file.getUsableSpace());
			vFile.setTags(tags.getText());
		}
	}

	public class ActionPath implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			dialog.setLayout(new BorderLayout());
			JButton button = new JButton("OK");
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("/");
			tree = new JTree(root);
			TreeModel.tree(root, disk);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(new ActionSelectFolder());

			button.addActionListener(new AddPath());
			JScrollPane pane = new JScrollPane(tree);
			JPanel panelButton = new JPanel();
			panelButton.add(button);
			dialog.add(pane);
			dialog.add(panelButton,BorderLayout.SOUTH);
			dialog.setTitle("Dove vuoi caricare il file?");
			dialog.setSize(200, 300);
			dialog.setVisible(true);
		}
	}
	
	public class AddPath implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			path.setText(current_path);
		}
	}

	public class ActionSelectFolder implements TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent e) {
			if(tree.getSelectionPath()!=null){
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
			}
			else{
			}
		}
	}
}
