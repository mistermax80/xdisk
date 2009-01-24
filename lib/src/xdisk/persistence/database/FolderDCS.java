package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class FolderDCS {


	private FolderDCS() {
		super();
	}

	private static final String REMOVE_ALL_SQL = "DELETE FROM folder";

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

	private static final String SELECT_ALL_SQL = "SELECT codice,nome,dimensione,parent,prova FROM folder";

	static Collection<Folder> getAll() throws PersistenceException { 
		Collection<Folder> all=null;
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

	private static Collection<Folder> processCollectionResultSet(ResultSet rst) throws SQLException{
		LinkedList<Folder> all = new LinkedList<Folder>();
		while (rst.next()) {
			all.add(objectFromCursor(rst));
		}
		return Collections.unmodifiableList(all);
	}

	private static Folder objectFromCursor(ResultSet rst) throws SQLException{
		Folder object= new Folder();
		object.setCodice(rst.getInt("codice"));
		object.setNome(rst.getString("nome"));
		object.setDimensione(rst.getInt("dimensione"));
		object.setParent(rst.getInt("parent"));
		object.setProva(rst.getBoolean("prova"));
		return object;
	}

	private static final String SELECT_CHILDS_SQL = "SELECT * FROM folder WHERE parent=?";
	
	public static Collection<Folder> getChilds(Folder folder) throws PersistenceException {
		Collection<Folder> all=null;
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_CHILDS_SQL);
			stm.setInt(1, folder.getCodice());
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

	private static final String SELECT_ROOT_SQL = "SELECT * FROM folder WHERE parent IS NULL";
	
	public static Folder getRoot() throws PersistenceException {
		Folder folder = null;
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_ROOT_SQL);
			//System.out.println(stm);
			rst=stm.executeQuery();
			rst.next();
			folder = objectFromCursor(rst);
		} catch (SQLException e) { 
			throw new PersistenceException(e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
		return folder;
	}

	private static final String SELECT_LAST_CODE_SQL = "SELECT max(codice) as lastCode FROM folder";
	public static int getLastCode() throws PersistenceException {
		int code = 0;
		Connection con = null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_LAST_CODE_SQL);
			System.out.println(stm);
			rst=stm.executeQuery();
			rst.next();
			code = rst.getInt("lastCode");
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
