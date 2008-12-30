package xdisk.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import xdisk.persistence.Disk;

public class DiskDCS
{
	private DiskDCS(){}

	/**
	 * Rimuove tutte le tuple della tabella disk
	 * @return il numero di tuple eliminate
	 * @throws PersistenceException
	 */
	private static final String REMOVE_ALL_SQL = "DELETE FROM disk";
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
		"FROM disk";
	public static Collection<Disk> getAll() throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		Collection<Disk> all=null;
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
	 * @param rs result set di una query
	 * @return una collezione di oggetti Disk
	 * @throws SQLException
	 */
	private static Collection<Disk> processCollectionResultSet(ResultSet rs) throws SQLException{
		LinkedList<Disk> all = new LinkedList<Disk>();
		while (rs.next()) {
			all.add(objectFromCursor(rs));
		}
		return Collections.unmodifiableList(all);
	}

	/**
	 * 
	 * @param rs result set di una query
	 * @return oggetto Disk
	 * @throws SQLException
	 */
	private static Disk objectFromCursor(ResultSet rs) throws SQLException{
		Disk disk= new Disk();
		disk.setName(rs.getString("nome"));
		disk.setAdmin(rs.getString("userid"));
		disk.setIdRoot(rs.getInt("cartellaroot"));
		disk.setDimension(rs.getInt("dimensione"));
		return disk;
	}

	private static final String SELECT_SQL_BY_USERNAME = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM disk " +
		"WHERE nome = ?";
	public static Disk getUserByUsername(String name) throws PersistenceException {
		Disk disk = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_USERNAME);
			stm.setString(1, name);
			rst=stm.executeQuery();
			if (rst.next())
				disk = objectFromCursor(rst);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			  if (rst != null) try {rst.close();} catch (Exception e) {}
			  if (stm != null) try {stm.close();} catch (Exception e) {}
			  if (con != null) try {con.close();} catch (Exception e) {}
		}
		return disk;
	}
}









