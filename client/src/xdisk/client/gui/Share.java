package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.FileModel;

public class Share extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JPanel panel1 = null;
	private JScrollPane panel2 = null;

	private JList list;
	private DefaultListModel listModel;

	private VirtualDisk disk;
	private VirtualFile current_file;

	private JButton button;
	private JButton button2;

	public Share(VirtualDisk disk) {
		super(new BorderLayout());
		this.disk = disk;

		panel1 = new JPanel();

		button = new JButton("Elimina File");
		button.addActionListener(new ActionDel());
		button2 = new JButton("Ricarica Lista");
		button2.addActionListener(new ActionUpdate());

		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ActionSelectFile());

		updateList();

		panel1.add(button);
		panel1.add(button2);
		panel2 = new JScrollPane(list);

		this.add(panel1,BorderLayout.SOUTH);
		this.add(panel2,BorderLayout.CENTER);
	}

	public class ActionSelectFile implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			if(list.getSelectedIndex()>-1){
				FileModel file = (FileModel)list.getSelectedValue();
				current_file = file.getFile();
			}
		}

	}

	public class ActionDel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(current_file!=null){
				listModel.removeElement(current_file);
				try {
					disk.notGotFile(current_file);
					disk.getLibrary().remove(current_file);
					updateList();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un disco!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public class ActionUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateList();
		}
	}
	
	private void updateList(){
		listModel.removeAllElements();
		LinkedList<VirtualFile> files = new LinkedList<VirtualFile>();
		try {
			files.addAll(disk.getLibrary().getVirtualFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<files.size();i++){
			VirtualFile vFile = files.get(i);
			FileModel file = new FileModel(vFile);
			listModel.addElement(file);
		}
	}
}
