package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.Folder;

public class FolderDAO {

	private static final String INSERT_SQL = "INSERT INTO folder(codice,nome,dimensione,parent,prova) VALUES (?,?,?,?,?)";
	private static final String SELECT_SQL = "SELECT codice,nome,dimensione,parent,prova FROM folder WHERE codice=?";
	private static final String UPDATE_SQL = "UPDATE folder codice=?, nome=?, dimensione=?, parent=?, prova=? WHERE codice=?";
	private static final String DELETE_SQL = "DELETE FROM folder WHERE codice=?";

	private FolderDAO() {
		super();
	}

	static void load (Folder object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(SELECT_SQL);
			stm.setInt(1,object.getCodice());
			rst=stm.executeQuery();
			if(rst.next()){
				object.setCodice(rst.getInt("codice"));
				object.setNome(rst.getString("nome"));
				object.setDimensione(rst.getInt("dimensione"));
				object.setParent(rst.getInt("parent"));
				object.setProva(rst.getBoolean("prova"));
			}
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void insert (Folder object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setInt(1,object.getCodice());
			stm.setString(2,object.getNome());
			stm.setInt(3,object.getDimensione());
			stm.setInt(4,object.getParent());
			stm.setBoolean(5,object.isProva());
			stm.executeUpdate();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void delete (Folder object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(DELETE_SQL);
			stm.setInt(1, object.getCodice());
			stm.execute();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void update (Folder object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(UPDATE_SQL);
			stm.setInt(1,object.getCodice());
			stm.setString(2,object.getNome());
			stm.setInt(3,object.getDimensione());
			stm.setInt(4,object.getParent());
			stm.setBoolean(5,object.isProva());
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
