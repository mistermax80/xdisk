package xdisk.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Client;

public class ClientDCS
{
	private ClientDCS(){}

	/**
	 * Rimuove tutte le tuple della tabella client
	 * @return il numero di tuple eliminate
	 * @throws PersistenceException
	 */
	private static final String REMOVE_ALL_SQL = "DELETE FROM client";
	public static int removeAll() throws PersistenceException
	{
		Connection con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		int rowsDeleted;
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

	/**
	 * 
	 * @return un collection di oggetti Disk tutti quelli presenti nel DB
	 * @throws PersistenceException 
	 */
	private static final String SELECT_ALL_SQL = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM client";
	public static Collection<Client> getAll() throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		Collection<Client> all=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_ALL_SQL);
			rst=stm.executeQuery();
			all = processCollectionResultSet(rst);
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

	/**
	 * 
	 * @param rst result set di una query
	 * @return una collezione di oggetti Client
	 * @throws SQLException
	 */
	private static Collection<Client> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Client> all = new LinkedList<Client>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	/**
	 * 
	 * @param rst result set di una query
	 * @return oggetto Client
	 * @throws SQLException
	 */
	private static Client objectFromCursor(ResultSet rst) throws SQLException{
		Client client= new Client();
		client.setUserid(rst.getString("userid"));
		client.setIpAddress(rst.getString("indip"));
		client.setConnType(rst.getString("porta"));
		client.setPortNumber(rst.getInt("tipoconn"));
		return client;
	}

	private static final String SELECT_SQL_BY_USERID = 
		"SELECT userid,indip,porta,tipoconn" +
		"FROM client " +
		"WHERE userid = ?";
	public static Client getUserByUsername(String userid) throws PersistenceException {
		Client client = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_USERID);
			stm.setString(1, userid);
			rst=stm.executeQuery();
			if (rst.next())
				client = objectFromCursor(rst);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return client;
	}
	
	private static final String SELECT_SQL_PRESENT_USERID = 
		"SELECT count(userid) as num " +
		"FROM client " +
		"WHERE userid = ?";
	public static boolean isPresent(Client client) throws PersistenceException {
		boolean present=false;		
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_PRESENT_USERID);
			stm.setString(1, client.getUserid());
			rst=stm.executeQuery();
			rst.next();
			if(rst.getInt("num")>0)
				present=true;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return present;
	}
	
	private static final String SELECT_SQL_CHECK_SESSION = 
		"SELECT count(userid) as num " +
		"FROM client " +
		"WHERE " +
		"session_id = ? AND " +
		"userid = ?";

	public static boolean checkSession(String id, String userid) throws PersistenceException {
		boolean validate=false;		
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_CHECK_SESSION);
			stm.setString(1, id);
			stm.setString(2, userid);
			rst=stm.executeQuery();
			rst.next();
			if(rst.getInt("num")>0)
				validate=true;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return validate;
	}

	private static final String REMOVE_BY_USERID_SQL = "DELETE FROM client WHERE userid=?";
	public static int remove(String userid) throws PersistenceException {
		Connection con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		int rowsDeleted;
		try {
			stm = con.prepareStatement(REMOVE_BY_USERID_SQL);
			stm.setString(1, userid);
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

	private static final String CLEAN_SQL = "DELETE FROM client WHERE SESSION_STARTED < ?";
	public static int cleanSession() throws PersistenceException {
		int rowsDeleted=0;
		Connection con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		long TIME_EXPIRED = 1000*60*5;// default 5 Minuti
		Timestamp time = new Timestamp(System.currentTimeMillis()-TIME_EXPIRED);
		try {
			stm = con.prepareStatement(CLEAN_SQL);
			stm.setTimestamp(1, time);
			rowsDeleted = stm.executeUpdate();
			System.out.println("Sessioni da eliminate num:"+rowsDeleted+" sono prima del: "+time);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return rowsDeleted;
	}
}









