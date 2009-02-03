package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.*;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File2;
import xdisk.persistence.database.FileController2;

public class Search extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;
	
	private JPanel panel1 = null;
	private JScrollPane panel2 = null;
	
	private JLabel searchLabel = null;
	private JTextField search = null;
	
	private JTable table =  null;

	private JButton button;
	
	public Search() {
		super(new BorderLayout());
		
		panel1 = new JPanel(new GridLayout(1,3));
		
		searchLabel = new JLabel("Nome File:");
		search = new JTextField();
		button = new JButton("Cerca");
		
		String[] colunmName = {"Codice","Nome","Dimensione","Cartella","Autore","Uploader"};
		LinkedList<File2> files = new LinkedList<File2>();
		try {
			files.addAll(FileController2.getAll());
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
		
		panel1.add(searchLabel);
		panel1.add(search);
		panel1.add(button);
		panel2 = new JScrollPane(table);		
		
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.CENTER);
	}
}
