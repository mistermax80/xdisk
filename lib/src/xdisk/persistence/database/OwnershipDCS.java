package xdisk.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Ownership;

public class OwnershipDCS
{
	private OwnershipDCS(){}

	/**
	 * Rimuove tutte le tuple della tabella ownership
	 * @return il numero di tuple eliminate
	 * @throws PersistenceException
	 */
	private static final String REMOVE_ALL_SQL = "DELETE FROM ownership";
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
	 * @return un collection di oggetti Ownership tutti quelli presenti nel DB
	 * @throws PersistenceException 
	 */
	private static final String SELECT_ALL_SQL = 
		"SELECT file,utente " +
		"FROM ownership";
	public static Collection<Ownership> getAll() throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		Collection<Ownership> all=null;
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
	 * @return una collezione di oggetti Ownership
	 * @throws SQLException
	 */
	private static Collection<Ownership> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Ownership> all = new LinkedList<Ownership>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	/**
	 * 
	 * @param rst result set di una query
	 * @return oggetto Ownership
	 * @throws SQLException
	 */
	private static Ownership objectFromCursor(ResultSet rst) throws SQLException{
		Ownership ownership= new Ownership();
		ownership.setFile(rst.getString("file"));
		ownership.setUser(rst.getString("utente"));
		return ownership;
	}

	private static final String SELECT_SQL_BY_USERNAME = 
		"SELECT file,utente " +
		"FROM ownership " +
		"WHERE file = ?";
	public static Ownership getUserByUsername(String name) throws PersistenceException {
		Ownership ownership = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_USERNAME);
			stm.setString(1, name);
			rst=stm.executeQuery();
			if (rst.next())
				ownership = objectFromCursor(rst);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return ownership;
	}
	
	private static final String SELECT_SQL_BY_CODE = 
		"SELECT * " +
		"FROM ownership " +
		"WHERE FILE = ?";
	public static Collection<Ownership> getUserByCode(String code) throws PersistenceException {
		Collection<Ownership> all=null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_CODE);
			stm.setString(1, code);
			System.out.println(stm);
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

	private static final String SELECT_ONLINE_SQL_BY_CODE = 
		"SELECT * " +
		"FROM ownership own,client cli " +
		"WHERE " +
		"cli.userid=own.utente AND " +
		"own.file = ?";
	public static Collection<Ownership> getUserOnlineByCode(String code) throws PersistenceException {
		Collection<Ownership> all=null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_ONLINE_SQL_BY_CODE);
			stm.setString(1, code);
			System.out.println("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§"+stm);
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
	
	private static final String COUNT_SQL = 
		"SELECT count(*) " +
		"FROM ownership " +
		"WHERE " +
		"utente = ? AND " +
		"file = ?";
	public static boolean isPresent(String codeFile, String userId) throws PersistenceException {
		boolean ret = false;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(COUNT_SQL);
			stm.setString(1, userId);
			stm.setString(2, codeFile);
			rst=stm.executeQuery();
			if(rst.next())
				ret = rst.getInt(1)>0;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return ret;
	}

	private static final String REMOVE_BY_USER_SQL = "DELETE FROM ownership WHERE utente=?";
	public static int remove(String userid) throws PersistenceException {
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		int rowsDeleted;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(REMOVE_BY_USER_SQL);
			stm.setString(1, userid);
			rowsDeleted = stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return rowsDeleted;
	}
}