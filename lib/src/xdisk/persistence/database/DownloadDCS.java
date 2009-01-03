package xdisk.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.Download;

public class DownloadDCS
{
	private DownloadDCS(){}

	/**
	 * Rimuove tutte le tuple della tabella download
	 * @return il numero di tuple eliminate
	 * @throws PersistenceException
	 */
	private static final String REMOVE_ALL_SQL = "DELETE FROM download";
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
	 * @return un collection di oggetti Download tutti quelli presenti nel DB
	 * @throws PersistenceException 
	 */
	private static final String SELECT_ALL_SQL = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM download";
	public static Collection<Download> getAll() throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		Collection<Download> all=null;
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
	 * @return una collezione di oggetti Download
	 * @throws SQLException
	 */
	private static Collection<Download> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Download> all = new LinkedList<Download>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	/**
	 * 
	 * @param rst result set di una query
	 * @return oggetto Download
	 * @throws SQLException
	 */
	private static Download objectFromCursor(ResultSet rst) throws SQLException{
		Download download= new Download();
		download.setTicket(rst.getInt("ticket"));
		download.setFile(rst.getInt("file"));
		download.setUser(rst.getString("utente"));
		download.setTimestamp(rst.getTimestamp("timestamp"));
		return download;
	}

	private static final String SELECT_SQL_BY_TICKET = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM download " +
		"WHERE nome = ?";
	public static Download getUserByUsername(String ticket) throws PersistenceException {
		Download download = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_TICKET);
			stm.setString(1, ticket);
			rst=stm.executeQuery();
			if (rst.next())
				download = objectFromCursor(rst);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return download;
	}
}









