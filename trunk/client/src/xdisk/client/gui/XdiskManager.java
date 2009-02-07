package xdisk.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import xdisk.VirtualFile;
import xdisk.client.core.Library;
import xdisk.client.core.Manifest;
import xdisk.client.core.VirtualDisk;
import xdisk.client.core.VirtualDiskManager;
import xdisk.client.data.FileModel;
import xdisk.client.data.TreeModel;
import xdisk.client.gui.Search.ActionSelectFile;
import xdisk.client.gui.Upload.ActionAddPath;
import xdisk.client.gui.Upload.ActionSelectFolder;

public class XdiskManager{

	private JFrame frame;
	private JPanel panel1 = null;
	private JScrollPane panel2 = null;

	private JButton button;
	private JButton button2;
	private JButton button3;

	private JList list;

	private DefaultListModel listModel;

	private VirtualDisk currentXdisk;

	public XdiskManager() {
		super();
	}

	public void execute(){
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}
		frame = new JFrame("XDISK MANAGER the new file sharing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		panel1 = new JPanel();

		button = new JButton("Apri Xdisk");
		button.addActionListener(new ActionOpen());
		button2 = new JButton("Aggiungi Xdisk");
		button2.addActionListener(new ActionAdd());
		button3 = new JButton("Elimina Xdisk");
		button3.addActionListener(new ActionDel());

		listModel = new DefaultListModel();

		int numXdisks = VirtualDiskManager.getInstance().getNum();
		System.out.println("Numero dischi virtuali trovati:"+numXdisks);
		for(int i=0;i<numXdisks;i++){			
			VirtualDisk xdisk = VirtualDiskManager.getInstance().get(i);
			listModel.addElement(xdisk);
		}

		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ActionSelectDisk());
		panel2 = new JScrollPane(list);

		panel1.add(button);
		panel1.add(button2);
		panel1.add(button3);

		frame.getContentPane().add(panel1, BorderLayout.NORTH);
		frame.getContentPane().add(panel2);
		//Display the window.
		frame.pack();
		frame.setSize(670, 500);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}

	public class ActionSelectDisk implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			if(list.getSelectedIndex()>-1){
				currentXdisk = (VirtualDisk)list.getSelectedValue();
			}
		}
	}

	public class ActionAdd implements ActionListener {
		private JFileChooser fileCh = new JFileChooser();
		private File file;

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button2) {
				int returnVal = fileCh.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileCh.getSelectedFile();
					try {
						String nameFile = file.getCanonicalPath();
						Manifest manifest = new Manifest();
						VirtualDisk newDisk = manifest.getVirtualDisk(nameFile);
						VirtualDiskManager.getInstance().add(newDisk);
						VirtualDiskManager.getInstance().saveConfig();
						listModel.addElement(newDisk);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public class ActionDel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			listModel.removeElement(currentXdisk);
			VirtualDiskManager.getInstance().removeByAddress(currentXdisk.getServerAddress());
			VirtualDiskManager.getInstance().saveConfig();
		}
	}

	public class ActionOpen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Xdisk xdisk = new Xdisk(currentXdisk);
			xdisk.execute();
		}
	}
	public static void main(String[] args){

		XdiskManager xds = new XdiskManager();
		xds.execute();
	}
}