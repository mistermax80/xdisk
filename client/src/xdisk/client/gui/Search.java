package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.FileModel;
import xdisk.downloader.Downloader;

public class Search extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JPanel panel1 = null;
	private JScrollPane panel2 = null;
	private JPanel panel3;

	private JLabel searchLabel = null;
	private JTextField search = null;

	private JButton button;
	private JButton button2;

	private JList list;
	private DefaultListModel listModel;

	private VirtualDisk disk;
	private VirtualFile current_file;


	public Search(VirtualDisk disk) {
		super(new BorderLayout());

		this.disk=disk;

		panel1 = new JPanel(new GridLayout(1,3));
		panel3 = new JPanel();

		searchLabel = new JLabel("Ricerca:");
		search = new JTextField();
		button = new JButton("Cerca");
		button.addActionListener(new ActionSearch());
		button2 = new JButton("Download");
		button2.addActionListener(new ActionSearch());
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ActionSelectFile());

		panel1.add(searchLabel);
		panel1.add(search);
		panel1.add(button);
		panel2 = new JScrollPane(list);
		panel3.add(button2);

		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.CENTER);
		this.add(panel3,BorderLayout.SOUTH);
	}

	public class ActionDownload implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if(current_file!=null){
//					VirtualFile file = 	disk.getVirtualFile(current_file.getPath()+current_file.getFilename()+"."+current_file.getExtension());
					
					String tiketId = disk.getFile(current_file);
					Collection<ClientResource> resources = disk.getSource(current_file);
					
					Downloader downloader = new Downloader(current_file, tiketId, null);
					
					Iterator<ClientResource> i = resources.iterator();
					while (i.hasNext())
					{
						downloader.addSource(i.next());
					}
					downloader.start();
					
					System.out.println("Download file:" + current_file);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						"Errore nel recupero del file dal server!!!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public class ActionSearch implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				LinkedList<VirtualFile> files = new LinkedList<VirtualFile>();
				files.addAll(disk.search(search.getText()));
				System.out.println(files);
				listModel.clear();
				for(int i=0;i<files.size();i++){
					VirtualFile file = (VirtualFile) files.get(i);
					listModel.addElement(new FileModel(file));					
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						"Errore nella ricerca del file dal server!!!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
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
