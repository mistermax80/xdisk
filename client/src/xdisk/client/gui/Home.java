package xdisk.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.*;

public class Home extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 383673236662670462L;
	private JLabel urlLabel;
	private JLabel portLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel localPortLabel;
	private JLabel stateLabel;
	
	private JTextField urlText;
	private JTextField portText;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JTextField localPortText;
	private JTextField stateText;
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	
	private String url;
	private String port;
	private String username;
	private String password;
	private String localPort;
	private String state;
	

	
	
	public Home() {
		super(new BorderLayout());
		
		String prova = "picchio";
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		System.out.println(prefs.get("prova","pp"));
		prefs.put("prova","picchietto");
		System.out.println(prefs.get("prova","uhuhu"));
			
		JPanel panel = new JPanel(new GridLayout(7,2));
		JPanel panel3 = new JPanel(new GridLayout(1,3));
		
		ResourceBundle config = ResourceBundle.getBundle("config");
		url=config.getString("url");
		port=config.getString("port");
		username=config.getString("username");
		password=config.getString("password");
		localPort=config.getString("localPort");
		
		urlLabel = new JLabel("Url server:");
		portLabel = new JLabel("Porta server:");
		usernameLabel = new JLabel("Username:");
		passwordLabel = new JLabel("Password:");
		localPortLabel = new JLabel("Porta locale:");
		stateLabel = new JLabel("Stato:");
		
		urlText = new JTextField(url);
		portText = new JTextField(port);
		usernameText = new JTextField(username);
		passwordText = new JPasswordField(password);
		localPortText = new JTextField(localPort);
		stateText = new JTextField(state);
		
		button1 = new JButton("Connetti");
		button2 = new JButton("Disconnetti");
		button3 = new JButton("Salva Modifiche");
		
		stateText.setEditable(false);
		panel.add(urlLabel);
		panel.add(urlText);
		panel.add(portLabel);
		panel.add(portText);
		panel.add(usernameLabel);
		panel.add(usernameText);
		panel.add(passwordLabel);
		panel.add(passwordText);
		panel.add(localPortLabel);
		panel.add(localPortText);

		panel.add(stateLabel);
		panel.add(stateText);
		
		panel3.add(button1);
		panel3.add(button2);
		panel3.add(button3);
		
		this.add(panel,BorderLayout.NORTH);
		this.add(panel3,BorderLayout.SOUTH);
	}
}
