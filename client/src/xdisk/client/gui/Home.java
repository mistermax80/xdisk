package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.*;

import xdisk.client.core.VirtualDisk;
import xdisk.client.core.VirtualDiskManager;

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

	private Image imageBackground;
	private Image imageConnect;
	private Image imageDisconnect;
	private Image imageSave;
	private Image imageDefault;
	private Image imageStateConn;
	private Image imageStateDisconn;

	private VirtualDisk disk;
	

	public Home(VirtualDisk disk) {
		super(new BorderLayout());
		this.disk=disk;
		
		JPanel panel = new JPanel(new GridLayout(6,2));
		JPanel panel3 = new JPanel(new GridLayout(1,4));
		panelState = new JPanel(new BorderLayout());


		url = disk.getUrl();
		port = disk.getServerPort();
		username = disk.getUserid();
		password = disk.getPassword();

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
		updateState(disk.isConnect());

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
				disk.connect();
				updateState(disk.isConnect());
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
				updateState(disk.isConnect());

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
			//prefs.put(urlText.getText(), portText.getText(), usernameText.getText(), passwordText.getText());
			//prefs.storeData();
			url = urlText.getText();
			port = Integer.parseInt(portText.getText());
			username = usernameText.getText();
			password = passwordText.getText();
			JOptionPane.showMessageDialog(null, "Salvataggio preferenze!");
		}
	}

	public class ActionDefault implements ActionListener {

		public void actionPerformed(ActionEvent e) {
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
}
















