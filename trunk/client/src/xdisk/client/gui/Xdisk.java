package xdisk.client.gui;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import xdisk.client.core.VirtualDisk;

public class Xdisk {
	
	private VirtualDisk disk;
	
	public Xdisk(VirtualDisk disk) {
		super();
		this.disk=disk;
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

		JFrame frame = new JFrame("XDISK the new file sharing");

		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel home = new Home(disk);
		JPanel upload = new Upload(disk);
		JPanel download = new Download(disk);
		JPanel search = new Search(disk);
		JPanel share = new Share(disk);
		JPanel transfert = new Transfert(disk);

		JTabbedPane tab = new JTabbedPane(SwingConstants.TOP);
		
		try {
			Image imageHome = ImageIO.read(Xdisk.class.getResource("images/kfm_home.png"));
			Image imageUpload = ImageIO.read(Xdisk.class.getResource("images/upload.png"));
			Image imageDownload = ImageIO.read(Xdisk.class.getResource("images/download1.png"));
			Image imageSearch = ImageIO.read(Xdisk.class.getResource("images/search.png"));
			Image imageShare = ImageIO.read(Xdisk.class.getResource("images/share.png"));
			Image imageTransfert = ImageIO.read(Xdisk.class.getResource("images/transfert.png"));
			
			tab.addTab("Home",new ImageIcon(imageHome),home);
			tab.addTab("Upload",new ImageIcon(imageUpload),upload);
			tab.addTab("Download",new ImageIcon(imageDownload),download);
			tab.addTab("Search",new ImageIcon(imageSearch),search);
			tab.addTab("Share",new ImageIcon(imageShare),share);
			tab.addTab("Transfert",new ImageIcon(imageTransfert),transfert);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero delle immagini", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		frame.getContentPane().add(tab, BorderLayout.CENTER);
		//Display the window.
		frame.pack();
		frame.setSize(670, 500);
		frame.setLocation(300, 200);
		frame.setVisible(true);
	}
}