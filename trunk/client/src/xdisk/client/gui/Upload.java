package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.TreeModel;

public class Upload extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JFileChooser fileCh;
	private JPanel panel;
	private JPanel panelButt;
	private JPanel panelAddFile;
	private JPanel panelOk;

	private JLabel tagsLabel;
	private JLabel descrLabel;

	private JButton pathButton;
	private JButton fileButton;
	private JButton okButton;

	private JTextField tags;
	private JTextField descr;
	private JTextField path;
	private JTextField fileText;

	private VirtualDisk disk;
	private String current_path;

	private JTree tree;
	private JDialog dialog;

	private File file;

	public Upload(VirtualDisk disk) {
		super(new BorderLayout());
		this.disk=disk;
		
		panel = new JPanel(new GridLayout(4,2));
		panelOk = new JPanel(new BorderLayout());
		panelButt = new JPanel(new BorderLayout());
		panelAddFile = new JPanel(new BorderLayout());

		tagsLabel = new JLabel("Tag:");
		descrLabel = new JLabel("Descrizione:");
		pathButton = new JButton("Path:");
		fileButton = new JButton("File:");
		okButton= new JButton("Carica");

		tags = new JTextField();
		path = new JTextField();
		descr = new JTextField();
		fileText = new JTextField();

		fileCh = new JFileChooser();

		path.setEditable(false);
		pathButton.addActionListener(new ActionPath());
		panelButt.add(pathButton,BorderLayout.WEST);

		fileText.setEditable(false);
		fileButton.addActionListener(new ActionFile());
		panelAddFile.add(fileButton,BorderLayout.WEST);

		panelOk.add(okButton);
		okButton.addActionListener(new ActionUpload());

		panel.add(tagsLabel);
		panel.add(tags);
		panel.add(descrLabel);
		panel.add(descr);
		panel.add(panelButt);
		panel.add(path);
		panel.add(panelAddFile);
		panel.add(fileText);

		this.add(panel,BorderLayout.NORTH);
		this.add(panelOk,BorderLayout.SOUTH);
		file = fileCh.getSelectedFile();
	}

	public class ActionPath implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialog = new JDialog();
			dialog.setLayout(new BorderLayout());
			JButton button = new JButton("OK");
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("/");
			tree = new JTree(root);
			TreeModel.tree(root, disk);
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree.addTreeSelectionListener(new ActionSelectFolder());

			button.addActionListener(new ActionAddPath());
			JScrollPane pane = new JScrollPane(tree);
			JPanel panelButton = new JPanel();
			panelButton.add(button);
			dialog.add(pane);
			dialog.add(panelButton,BorderLayout.SOUTH);
			dialog.setTitle("Dove vuoi caricare il file?");
			dialog.setSize(200, 300);
			dialog.setLocation(500, 300);
			dialog.setVisible(true);
		}
	}

	public class ActionAddPath implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			path.setText(current_path);
			dialog.setVisible(false);
		}
	}

	public class ActionFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fileButton) {
				int returnVal = fileCh.showOpenDialog(Upload.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileCh.getSelectedFile();
					try {
						fileText.setText(file.getCanonicalPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
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
	public class ActionUpload implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			file = fileCh.getSelectedFile();
			System.out.println("file selezionato"+file);

			String[] name = file.getName().split("\\.");
			String fileName = "";
			String extension = "";
			for(int i=0;i<name.length;i++){
				if(i<name.length-1)
					fileName+= name[i]+".";
				if(i==name.length-1)
					extension = name[name.length-1];					
			}
			
			fileName=fileName.substring(0,fileName.length()-1);
			if(file!=null){
				VirtualFile vFile = new VirtualFile();
				vFile.setDescription(descr.getText());
				vFile.setExtension(extension);
				vFile.setFilename(fileName);
				vFile.setMime("mime");
				vFile.setOwner(disk.getUserid());
				vFile.setPath(path.getText());
				vFile.setSize(file.length());
				vFile.setTags(tags.getText());
				System.out.println("file virt che carico"+vFile);
				try {
					disk.insertFile(vFile);
					//Salvare lista dei file messi a condividere
					disk.getLibrary().add(file.getAbsolutePath(), vFile);					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
