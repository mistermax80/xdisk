package xdisk.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import xdisk.persistence.database.DatabaseConnectionFactory;

public class Test {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseConnectionFactory.getConnection();
		Statement stmt=null;
		
		stmt = con.createStatement();
		String query="CREATE TABLE example (id INT,data VARCHAR(100));";
		stmt.executeUpdate(query);
		
		
		
	}
}