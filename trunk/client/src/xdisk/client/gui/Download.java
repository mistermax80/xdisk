package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.database.FileController;

public class Download extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;
	
	JPanel panel1 = null;
	JPanel panel2 = null;

	private JButton button;

	private JTable table;

	private JScrollPane panel3;
	
	public Download() {
		super(new BorderLayout());
		panel1 = new JPanel(new GridLayout(1,2));
		panel2 = new JPanel();
		button = new JButton("Download");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("RADICE");
		DefaultMutableTreeNode cartella1 = new DefaultMutableTreeNode("Cartella1");
		root.add(cartella1);
		DefaultMutableTreeNode cartella2 = new DefaultMutableTreeNode("Cartella2");
		root.add(cartella2);
		DefaultMutableTreeNode cartella3 = new DefaultMutableTreeNode("Cartella3");
		cartella2.add(cartella3);
		
		String[] colunmName = {"Codice","Nome","Dimensione","Cartella","Autore","Uploader"};
		LinkedList<File> files = new LinkedList<File>();
		try {
			files.addAll(FileController.getAll());
		} catch (PersistenceException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.toString(),"Errore accesso Dati",JOptionPane.ERROR_MESSAGE);
		}
		int rows = files.size();
		int cols = colunmName.length;
		String[][] data = new String[rows][cols];
		for(int r=0;r<rows;r++){
				data[r][0]=String.valueOf(files.get(r).getCode());
				data[r][1]=files.get(r).getName();
				data[r][2]=String.valueOf(files.get(r).getDimension());
				data[r][3]=String.valueOf(files.get(r).getFolder());
				data[r][4]=files.get(r).getAuthor();
				data[r][5]=files.get(r).getLoaderUserid();
		}
		
		table = new JTable(data,colunmName);
		table.setToolTipText("Elenco File");
		
		JTree tree = new JTree(root);
		panel1.add(tree);
		panel3 = new JScrollPane(table);
		panel1.add(panel3);
		panel2.add(button);
		
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);
	}
}
