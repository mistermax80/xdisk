package xdisk.persistence.database;


import java.sql.*;

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;
import xdisk.persistence.Folder;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class FileDCS {


	private FileDCS() {
		super();
	}

	private static final String REMOVE_ALL_SQL = "DELETE FROM file";

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

	private static final String SELECT_ALL_SQL = "SELECT code,name,extension,description,tags,size,owner,mime,parent FROM file";

	static Collection<File> getAll() throws PersistenceException { 
		Collection<File> all=null;
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

	private static Collection<File> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<File> all = new LinkedList<File>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	private static File objectFromCursor(ResultSet rst) throws SQLException{
		File object= new File();
		object.setCode(rst.getString("code"));
		object.setName(rst.getString("name"));
		object.setExtension(rst.getString("extension"));
		object.setDescription(rst.getString("description"));
		object.setTags(rst.getString("tags"));
		object.setSize(rst.getInt("size"));
		object.setOwner(rst.getString("owner"));
		object.setMime(rst.getString("mime"));
		object.setParent(rst.getInt("parent"));
		return object;
	}

	private static final String SELECT_SQL_BY_USERNAME = 
		"SELECT * " +
		"FROM file " +
		"WHERE name = ?";
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
		"WHERE parent = ?";
	public static Collection<File> getFile(Folder folder) throws PersistenceException {
		Collection<File> files = new LinkedList<File>();
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL_BY_FOLDER);
			stm.setInt(1, folder.getCodice());
			//System.out.println(stm);
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

	private static final String SEARCH_SQL = 
		"SELECT * " +
		"FROM file " +
		"WHERE  " +
		"description LIKE ? OR " +
		"name LIKE ? OR " +
		"tags LIKE ?";
	public static Collection<File> search(String query) throws PersistenceException {
		Collection<File> files = new LinkedList<File>();
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SEARCH_SQL);
			stm.setString(1, "%"+query+"%");
			stm.setString(2, "%"+query+"%");
			stm.setString(3, "%"+query+"%");
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
	
	private static final String SELECT_CODE_SQL = 
		"SELECT * " +
		"FROM file " +
		"WHERE " +
		"name = ? AND " +
		"parent = ?";
	public static String getCode(String nameFile, int parent) throws PersistenceException {
		String code="";
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;

		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_CODE_SQL);
			stm.setString(1, nameFile);
			stm.setInt(2, parent);
			rst=stm.executeQuery();
			if(rst.next()){
				code=rst.getString("code");
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return code;
	}
}
