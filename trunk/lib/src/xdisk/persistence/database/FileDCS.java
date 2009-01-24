package xdisk.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;

public class FileDCS
{
	private FileDCS(){}

	/**
	 * Rimuove tutte le tuple della tabella file
	 * @return il numero di tuple eliminate
	 * @throws PersistenceException
	 */
	private static final String REMOVE_ALL_SQL = "DELETE FROM file";
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
	 * @return un collection di oggetti File tutti quelli presenti nel DB
	 * @throws PersistenceException 
	 */
	private static final String SELECT_ALL_SQL = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM file";
	public static Collection<File> getAll() throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		Collection<File> all=null;
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
	 * @return una collezione di oggetti File
	 * @throws SQLException
	 */
	private static Collection<File> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<File> all = new LinkedList<File>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	/**
	 * 
	 * @param rst result set di una query
	 * @return oggetto File
	 * @throws SQLException
	 */
	private static File objectFromCursor(ResultSet rst) throws SQLException{
		File file= new File();
		file.setCode(rst.getInt("codice"));
		file.setName(rst.getString("nome"));
		file.setDimension(rst.getInt("dimensione"));
		file.setFolder(rst.getInt("cartella"));
		file.setAuthor(rst.getString("autore"));
		file.setLoaderUserid(rst.getString("utenteins"));
		return file;
	}

	private static final String SELECT_SQL_BY_USERNAME = 
		"SELECT nome,userid,cartellaroot,dimensione " +
		"FROM file " +
		"WHERE nome = ?";
	public static File getUserByUsername(String name) throws PersistenceException {
		File file = null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_USERNAME);
			stm.setString(1, name);
			rst=stm.executeQuery();
			if (rst.next())
				file = objectFromCursor(rst);
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

	private static final String SELECT_SQL_BY_FOLDER = 
		"SELECT * " +
		"FROM file " +
		"WHERE cartella = ?";
	public static Collection<File> getFile(Folder folder) throws PersistenceException {
		Collection<File> files = new LinkedList<File>();
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_FOLDER);
			stm.setInt(1, folder.getCodice());
			System.out.println(stm);
			rst=stm.executeQuery();
			files = processCollectionResultSet(rst);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return files;
	}
}









