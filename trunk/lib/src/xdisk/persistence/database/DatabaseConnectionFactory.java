package xdisk.persistence.database;

import java.sql.*;
public class DatabaseConnectionFactory {
	
	private static DatabaseConnectionFactory instance;
	private Connection connection;

	private static boolean driverLoaded = false;
	
	private static final String MY_DRIVER ="com.mysql.jdbc.Driver";
	private static final String MY_URL ="jdbc:mysql://192.168.1.11/xdisk";
	private static final String LOGIN = "xdisk";
	private static final String PASSWD = "xdisk";

	private DatabaseConnectionFactory ()
	{
		try {
			if(!driverLoaded){
				try {
					Class.forName(MY_DRIVER);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				driverLoaded= true;
			}
			connection = new JDBCConnection(DriverManager.getConnection(MY_URL,LOGIN,PASSWD));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Questo metodo restituisce la connection per effettuare
	 * la connessione al database 
	 */
	public static synchronized Connection getConnection ()
	{
		if (instance == null)
			instance = new DatabaseConnectionFactory();
		return instance.connection;
	}
}