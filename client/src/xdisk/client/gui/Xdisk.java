package xdisk.client.gui;

import java.awt.*;

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

		tab.addTab("Home",new ImageIcon("../client/src/xdisk/client/images/kfm_home.png"),home);
		tab.addTab("Upload",new ImageIcon("../client/src/xdisk/client/images/upload.png"),upload);
		tab.addTab("Download",new ImageIcon("../client/src/xdisk/client/images/download1.png"),download);
		tab.addTab("Search",new ImageIcon("../client/src/xdisk/client/images/search.png"),search);

		frame.getContentPane().add(tab, BorderLayout.CENTER);
		//Display the window.
		frame.pack();
		frame.setSize(640, 500);
		frame.setVisible(true);
	}
}
