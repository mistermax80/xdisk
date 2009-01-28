package xdisk.client.gui;

import java.awt.BorderLayout;
import javax.swing.*;

public class Upload extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;
	
	JFileChooser file = null;
	
	public Upload() {
		super(new BorderLayout());
		file = new JFileChooser();
		this.add(file,BorderLayout.NORTH);
	}
}
