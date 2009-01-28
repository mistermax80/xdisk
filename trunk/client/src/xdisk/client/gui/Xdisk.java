package xdisk.client.gui;

import java.awt.*;

import javax.swing.*;

public class Xdisk {

	public static void main(String[] args){
		JFrame frame = new JFrame("XDISK the new file sharing");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel home = new Home();
		JPanel upload = new Upload();
		JPanel download = new Download();
		JPanel search = new Search();
		
		
		JTabbedPane tab = new JTabbedPane(SwingConstants.BOTTOM);

		tab.addTab("Home",home);
		tab.addTab("Upload",upload);
		tab.addTab("Download",download);
		tab.addTab("Search",search);

		frame.getContentPane().add(tab, BorderLayout.CENTER);
		//Display the window.
		frame.pack();
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
