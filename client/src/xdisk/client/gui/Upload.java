package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;

import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;

public class Upload extends JPanel{

	private static final long serialVersionUID = -4944887504788487152L;

	private JFileChooser fileCh;
	private JPanel panel;

	private JLabel tagsLabel;
	private JLabel pathLabel;
	private JLabel descrLabel;

	private JTextField tags;
	private JTextField path;
	private JTextField descr;

	public Upload() {
		super(new BorderLayout());

		panel = new JPanel(new GridLayout(3,2));

		tagsLabel = new JLabel("Tag:");
		pathLabel = new JLabel("Path:");
		descrLabel = new JLabel("Descrizione:");

		tags = new JTextField();
		path = new JTextField();
		descr = new JTextField();

		fileCh = new JFileChooser();

		panel.add(tagsLabel);
		panel.add(tags);
		panel.add(pathLabel);
		panel.add(path);
		panel.add(descrLabel);
		panel.add(descr);

		this.add(panel,BorderLayout.NORTH);
		this.add(fileCh);

		File file = fileCh.getSelectedFile();

		VirtualDisk disk = new VirtualDisk("xdisk","disco virtuale","localhost",4444,"http://xx", 8080, "ciips", "c");
		if(file!=null){
			VirtualFile vFile = new VirtualFile();
			vFile.setDescription("descrizione");
			vFile.setExtension("extension");
			vFile.setFilename(file.getName());
			vFile.setMime("<mime>");
			vFile.setOwner("ciips");
			vFile.setPath("/");
			vFile.setSize((int) file.getUsableSpace());
			vFile.setTags(tags.getText());
		}
	}
}
