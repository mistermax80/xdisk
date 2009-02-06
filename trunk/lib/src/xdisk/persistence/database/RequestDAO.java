package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Request;

public class RequestDAO {

	private static final String INSERT_SQL = "INSERT INTO request(ticketid,file,userid) VALUES (?,?,?)";
	private static final String SELECT_SQL = "SELECT ticketid,file,userid FROM request WHERE ticketid=?";
	private static final String UPDATE_SQL = "UPDATE request SET ticketid=?, file=?, userid=? WHERE ticketid=?";
	private static final String DELETE_SQL = "DELETE FROM request WHERE ticketid=?";

	private RequestDAO() {
		super();
	}

	static void load (Request object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(SELECT_SQL);
			stm.setString(1,object.getTicketid());
			rst=stm.executeQuery();
			rst.next();
			object.setTicketid(rst.getString("ticketid"));
			object.setFile(rst.getString("file"));
			object.setUserid(rst.getString("userid"));
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void insert (Request object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,object.getTicketid());
			stm.setString(2,object.getFile());
			stm.setString(3,object.getUserid());
			stm.executeUpdate();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void delete (Request object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(DELETE_SQL);
			stm.setString(1, object.getTicketid());
			stm.execute();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void update (Request object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(UPDATE_SQL);
			stm.setString(1,object.getTicketid());
			stm.setString(2,object.getFile());
			stm.setString(3,object.getUserid());
			stm.setString(4,object.getTicketid());
			stm.executeUpdate();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}
