package xdisk.client.gui;

import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Xdisk {

	public static void main(String[] args){
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

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel home = new Home();
		JPanel upload = new Upload();
		JPanel download = new Download();
		JPanel search = new Search();

		JTabbedPane tab = new JTabbedPane(SwingConstants.TOP);
		
		try {
			Image imageHome = ImageIO.read(Xdisk.class.getResource("images/kfm_home.png"));
			Image imageUpload = ImageIO.read(Xdisk.class.getResource("images/upload.png"));
			Image imageDownload = ImageIO.read(Xdisk.class.getResource("images/download1.png"));
			Image imageSearch = ImageIO.read(Xdisk.class.getResource("images/search.png"));
			
			tab.addTab("Home",new ImageIcon(imageHome),home);
			tab.addTab("Upload",new ImageIcon(imageUpload),upload);
			tab.addTab("Download",new ImageIcon(imageDownload),download);
			tab.addTab("Search",new ImageIcon(imageSearch),search);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero delle immagini", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		frame.getContentPane().add(tab, BorderLayout.CENTER);
		//Display the window.
		frame.pack();
		frame.setSize(640, 500);
		frame.setVisible(true);
	}
}