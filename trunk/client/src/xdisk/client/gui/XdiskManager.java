package xdisk.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import xdisk.client.core.Manifest;
import xdisk.client.core.VirtualDisk;
import xdisk.client.core.VirtualDiskManager;

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
	private Image imageAdd;
	private Image imageRemove;
	private Image imageOpen;

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

		try {
			imageAdd = ImageIO.read(Xdisk.class.getResource("images/list-add.png"));
			imageOpen = ImageIO.read(Xdisk.class.getResource("images/media-playback-start.png"));
			imageRemove= ImageIO.read(Xdisk.class.getResource("images/list-remove.png"));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero delle immagini", "Errore", JOptionPane.ERROR_MESSAGE);
		}

		button = new JButton("Apri Xdisk",new ImageIcon(imageOpen));
		button.addActionListener(new ActionOpen());
		button2 = new JButton("Aggiungi Xdisk",new ImageIcon(imageAdd));
		button2.addActionListener(new ActionAdd());
		button3 = new JButton("Elimina Xdisk",new ImageIcon(imageRemove));
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
		list.addMouseListener(new ActionJList(list));

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
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Errore nel recupero del disco", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	public class ActionDel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(currentXdisk!=null){
				listModel.removeElement(currentXdisk);
				VirtualDiskManager.getInstance().removeByAddress(currentXdisk.getServerAddress());
				VirtualDiskManager.getInstance().saveConfig();
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un disco!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public class ActionOpen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(currentXdisk!=null){
				Xdisk xdisk = new Xdisk(currentXdisk);
				xdisk.execute();
			}
			else{
				JOptionPane.showMessageDialog(null, "Seleziona un disco!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public class ActionJList extends MouseAdapter{
		protected JList list;

		public ActionJList(JList l){
			list = l;
		}

		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				if(currentXdisk!=null){
					Xdisk xdisk = new Xdisk(currentXdisk);
					xdisk.execute();
				}
				else{
					JOptionPane.showMessageDialog(null, "Seleziona un disco!!!", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	public static void main(String[] args){

		XdiskManager xds = new XdiskManager();
		xds.execute();
	}
}