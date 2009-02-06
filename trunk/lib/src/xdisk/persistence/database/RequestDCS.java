package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Request;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class RequestDCS {


	private RequestDCS() {
		super();
	}

	private static final String REMOVE_ALL_SQL = "DELETE FROM request";

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

	private static final String SELECT_ALL_SQL = "SELECT ticketid,file,userid FROM request";

	static Collection<Request> getAll() throws PersistenceException { 
		Collection<Request> all=null;
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

	private static Collection<Request> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Request> all = new LinkedList<Request>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	private static Request objectFromCursor(ResultSet rst) throws SQLException{
		Request object= new Request();
		object.setTicketid(rst.getString("ticketid"));
		object.setFile(rst.getString("file"));
		object.setUserid(rst.getString("userid"));
		return object;
	}

	private static final String CHECK_TIKET_SQL = "SELECT file FROM request WHERE ticketId = ?";
	
	public static String check(String ticket) throws PersistenceException {
		String file = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(CHECK_TIKET_SQL);
			stm.setString(1, ticket);
			rst=stm.executeQuery();
			if(rst.next())
					file=rst.getString("file");
		} catch (SQLException e) { 
			throw new PersistenceException(e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return file;
	}

}
