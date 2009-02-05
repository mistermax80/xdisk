package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Dictionary;

import javax.imageio.ImageIO;
import javax.swing.*;

import xdisk.client.core.Prefs;
import xdisk.client.core.VirtualDisk;
import xdisk.exception.PersistenceException;

public class Home extends JPanel{

	private static final long serialVersionUID = 383673236662670462L;
	private JLabel urlLabel;
	private JLabel portLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel stateLabel;
	private JLabel imageLabel;

	private JPanel panelState;
	private JLabel imageStateLabel;

	private JTextField urlText;
	private JTextField portText;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JTextField stateText;

	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;

	private String url;
	private int port;
	private String username;
	private String password;
	private String state;

	private static final String CONNECT="Connesso-Autenticato";
	private static final String DISCONNECT="Disconnesso";

	private Prefs prefs;

	Image imageBackground;
	Image imageConnect;
	Image imageDisconnect;
	Image imageSave;
	Image imageDefault;
	Image imageStateConn;
	Image imageStateDisconn;

	VirtualDisk disk;
	private boolean connect;

	public Home() {
		super(new BorderLayout());

		connect=false;

		JPanel panel = new JPanel(new GridLayout(6,2));
		JPanel panel3 = new JPanel(new GridLayout(1,4));
		panelState = new JPanel(new BorderLayout());

		prefs=new Prefs();

		url = prefs.getUrl();
		port = prefs.getPort();
		username = prefs.getUsername();
		password = prefs.getPassword();

		urlLabel = new JLabel("Url server:");
		portLabel = new JLabel("Porta server:");
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		stateLabel = new JLabel("Stato:");

		urlText = new JTextField(url);
		portText = new JTextField(String.valueOf(port));
		usernameText = new JTextField(username);
		passwordText = new JPasswordField(password);
		stateText = new JTextField(state);

		try {
			imageBackground = ImageIO.read(Xdisk.class.getResource("images/disk.png"));
			imageConnect = ImageIO.read(Xdisk.class.getResource("images/connect.png"));
			imageDisconnect = ImageIO.read(Xdisk.class.getResource("images/disconnect.png"));
			imageSave = ImageIO.read(Xdisk.class.getResource("images/save1.png"));
			imageDefault = ImageIO.read(Xdisk.class.getResource("images/reset.png"));
			imageStateConn = ImageIO.read(Xdisk.class.getResource("images/stateOk.png"));
			imageStateDisconn = ImageIO.read(Xdisk.class.getResource("images/stateNo.png"));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero delle immagini", "Errore", JOptionPane.ERROR_MESSAGE);
		}


		imageLabel = new JLabel(new ImageIcon(imageBackground));
		imageStateLabel = new JLabel();

		button1 = new JButton("Connetti",new ImageIcon(imageConnect));
		button2 = new JButton("Disconnetti",new ImageIcon(imageDisconnect));
		button3 = new JButton("Salva Modifiche",new ImageIcon(imageSave));
		button4 = new JButton("Carica Default",new ImageIcon(imageDefault));

		button1.addActionListener(new ActionConnect());
		button2.addActionListener(new ActionDisconnect());
		button3.addActionListener(new ActionUpdate());
		button4.addActionListener(new ActionDefault());

		stateText.setEditable(false);
		updateState(connect);

		panel.add(urlLabel);
		panel.add(urlText);
		panel.add(portLabel);
		panel.add(portText);
		panel.add(usernameLabel);
		panel.add(usernameText);
		panel.add(passwordLabel);
		panel.add(passwordText);
		panel.add(stateLabel);

		panelState.add(imageStateLabel,BorderLayout.WEST);
		panelState.add(stateText,BorderLayout.CENTER);

		panel.add(panelState);


		panel3.add(button1);
		panel3.add(button2);
		panel3.add(button3);
		panel3.add(button4);

		this.add(panel,BorderLayout.NORTH);
		this.add(imageLabel,BorderLayout.CENTER);
		this.add(panel3,BorderLayout.SOUTH);
	}

	public class ActionConnect implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				disk = new VirtualDisk("xdisk","disco virtuale",url,port,"http://xx", 8080, username, password);
				connect = disk.connect();
				updateState(connect);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Host "+url+" non raggiungibile", "Errore", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore di comunicazione", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class ActionDisconnect implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				disk.disconnect();
				connect=false;
				updateState(connect);

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Host "+url+" non raggiungibile", "Errore", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore di comunicazione", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class ActionUpdate implements ActionListener {

		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			try {
				prefs.put(urlText.getText(), portText.getText(), usernameText.getText(), passwordText.getText());
				prefs.storeData();
				url = urlText.getText();
				port = Integer.parseInt(portText.getText());
				username = usernameText.getText();
				password = passwordText.getText();
				JOptionPane.showMessageDialog(null, "Salvataggio preferenze!");
			} catch (PersistenceException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore nel salvataggio preferenze!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public class ActionDefault implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				prefs.clearData();
				urlText.setText(prefs.getUrl());
				portText.setText(String.valueOf(prefs.getPort()));
				usernameText.setText(prefs.getUsername());
				passwordText.setText(prefs.getPassword());
			} catch (PersistenceException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore nel caricamento preferenze default!", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void updateState(boolean stateConnected){
		if(stateConnected){
			stateText.setText(CONNECT);
			imageStateLabel.setIcon(new ImageIcon(imageStateConn));
		}
		else{
			stateText.setText(DISCONNECT);
			imageStateLabel.setIcon(new ImageIcon(imageStateDisconn));
		}
		button1.setEnabled(!stateConnected);
		button2.setEnabled(stateConnected);
	}

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}
	
	
}

















