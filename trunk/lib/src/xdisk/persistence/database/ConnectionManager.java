package xdisk.persistence.database;

import java.sql.*;
public class ConnectionManager {
	
	private static final String MY_DRIVER ="com.mysql.jdbc.Driver";
	private static final String MY_URL ="jdbc:mysql://localhost/xdisk";
	private static final String LOGIN = "xdisk";
	private static final String PASSWD = "xdisk";
	private static Connection conn = null;

	private ConnectionManager(){}

	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(conn == null) {
			Class.forName(MY_DRIVER);
			conn = DriverManager.getConnection(MY_URL,LOGIN, PASSWD);
		} else {
			if(conn.isClosed())
				conn = DriverManager.getConnection(MY_URL,LOGIN, PASSWD);
			else {
				// In questo caso si e’ verificato un comportamento
				// non atteso. Inserisco comandi che corrispondono
				// ad una politica da seguire in caso di errore
				conn.rollback(); //si e’ scelto di disfare tutto il
				//lavoro eseguito dall’ultimo commit.
			}
		}
		return (Connection)conn;
	}
}