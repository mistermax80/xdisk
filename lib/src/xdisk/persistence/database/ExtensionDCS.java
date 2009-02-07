package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Extension;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ExtensionDCS {


	private ExtensionDCS() {
		super();
	}

	private static final String REMOVE_ALL_SQL = "DELETE FROM extension";

	static int removeAll() throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		int rowsDeleted;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(REMOVE_ALL_SQL);
			rowsDeleted = stm.executeUpdate();
		} catch (SQLException e) { 
			throw new PersistenceException(e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return rowsDeleted;
	}

	private static final String SELECT_ALL_SQL = "SELECT allow,name FROM extension";

	static Collection<Extension> getAll() throws PersistenceException { 
		Collection<Extension> all=null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(SELECT_ALL_SQL);
			rst=stm.executeQuery();all = processCollectionResultSet(rst);
		} catch (SQLException e) { 
			throw new PersistenceException(e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return all;
	}

	private static Collection<Extension> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Extension> all = new LinkedList<Extension>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	private static Extension objectFromCursor(ResultSet rst) throws SQLException{
		Extension object= new Extension();
		object.setAllow(rst.getBoolean("allow"));
		object.setName(rst.getString("name"));
		return object;
	}

	private static final String CHECK_SQL = "SELECT count(*) FROM extension WHERE name = ? AND allow = true";
	
	public static boolean checkAllow(String extension) throws PersistenceException {
		Connection con=null;
		PreparedStatement stm=null;
		boolean allow=false;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(CHECK_SQL);
			stm.setString(1, extension);
			rst = stm.executeQuery();
			if(rst.next())
				if(rst.getInt(1)>0)
					allow = true;
		} catch (SQLException e) { 
			throw new PersistenceException(e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return allow;
	}

}
