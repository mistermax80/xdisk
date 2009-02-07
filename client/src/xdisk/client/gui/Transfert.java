package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import xdisk.VirtualFile;
import xdisk.client.core.Library;
import xdisk.client.core.VirtualDisk;
import xdisk.client.data.FileModel;

public class Transfert extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JPanel panel = null;
	private JScrollPane panel1 = null;
	private JScrollPane panel2 = null;
	private JPanel panel3 = null;
	private JPanel panel4 = null;
	private JSplitPane splitPanel = null;

	private JList listDownload;
	private JList listUpload;
	private DefaultListModel listModelDownload;
	private DefaultListModel listModelUpload;

	private VirtualDisk disk;
	private VirtualFile current_file_download;
	private VirtualFile current_file_upload;

	private JButton button;
	private JButton button2;
	private JButton button3;
	private JButton button4;

	public Transfert(VirtualDisk disk) {
		super(new BorderLayout());
		this.disk = disk;

		panel = new JPanel();
		panel3 = new JPanel(new BorderLayout());
		panel4 = new JPanel(new BorderLayout());
		
		button = new JButton("Stop");
		button.addActionListener(new ActionStop());
		button2 = new JButton("Start");
		button2.addActionListener(new ActionStart());
		button3 = new JButton("Resume");
		button3.addActionListener(new ActionResume());
		button4 = new JButton("Ricarica lista");
		button4.addActionListener(new ActionResume());
		
		listModelDownload = new DefaultListModel();
		listDownload = new JList(listModelDownload);
		listDownload.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDownload.addListSelectionListener(new ActionSelectFileDowload());

		listModelUpload = new DefaultListModel();
		listUpload = new JList(listModelUpload);
		listUpload.setEnabled(false);//setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUpload.addListSelectionListener(new ActionSelectFileUpload());
		
		//updateList();
			
		panel.add(button);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel1 = new JScrollPane(listDownload);
		panel2 = new JScrollPane(listUpload);
		
		
		panel3.add(panel1);
		panel4.add(panel2);
		
		//Create a split pane with the two scroll panes in it.
		splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				panel3, panel4);
		splitPanel.setOneTouchExpandable(true);
		splitPanel.setDividerLocation(200);

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(50, 50);
		panel3.setMinimumSize(minimumSize);
		panel4.setMinimumSize(minimumSize);

		this.add(panel,BorderLayout.SOUTH);
		this.add(splitPanel,BorderLayout.CENTER);
		
		for(int i=0;i<20;i++){
			listModelDownload.addElement("download"+i);
			listModelUpload.addElement("upload"+i);
		}
	}

	public class ActionSelectFileDowload implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			if(listDownload.getSelectedIndex()>-1){
				FileModel file = (FileModel)listDownload.getSelectedValue();
				current_file_download = file.getFile();
			}
		}
	}

	public class ActionSelectFileUpload implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			if(listUpload.getSelectedIndex()>-1){
				FileModel file = (FileModel)listUpload.getSelectedValue();
				current_file_upload = file.getFile();
			}
		}
	}
	
	public class ActionStop implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(current_file_download!=null){
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un File!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public class ActionStart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(current_file_download!=null){
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un File!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public class ActionResume implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(current_file_download!=null){
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un File!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public class ActionUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(current_file_download!=null){
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un File!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void updateList(){
	/*	listModel.removeAllElements();
		LinkedList<VirtualFile> files = new LinkedList<VirtualFile>();
		files.addAll(library.getVirtualFile());
		for(int i=0;i<files.size();i++){
			VirtualFile vFile = files.get(i);
			FileModel file = new FileModel(vFile);
			listModel.addElement(file);
		}*/
	}
}
