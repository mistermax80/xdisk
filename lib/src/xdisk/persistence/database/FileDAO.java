package xdisk.persistence.database;


import java.sql.*;
import xdisk.exception.PersistenceException;
import xdisk.persistence.File;

public class FileDAO {

	private static final String INSERT_SQL = "INSERT INTO file(code,name,extension,description,tags,size,owner,mime,parent) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_SQL = "SELECT code,name,extension,description,tags,size,owner,mime,parent FROM file WHERE code=?";
	private static final String UPDATE_SQL = "UPDATE file code=?, name=?, extension=?, description=?, tags=?, size=?, owner=?, mime=?, parent=? WHERE code=?";
	private static final String DELETE_SQL = "DELETE FROM file WHERE code=?";

	private FileDAO() {
		super();
	}

	static void load (File object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(SELECT_SQL);
			stm.setString(1,object.getCode());
			rst=stm.executeQuery();
			rst.next();
			object.setCode(rst.getString("code"));
			object.setName(rst.getString("name"));
			object.setExtension(rst.getString("extension"));
			object.setDescription(rst.getString("description"));
			object.setTags(rst.getString("tags"));
			object.setSize(rst.getInt("size"));
			object.setOwner(rst.getString("owner"));
			object.setMime(rst.getString("mime"));
			object.setParent(rst.getInt("parent"));
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void insert (File object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,object.getCode());
			stm.setString(2,object.getName());
			stm.setString(3,object.getExtension());
			stm.setString(4,object.getDescription());
			stm.setString(5,object.getTags());
			stm.setLong(6,object.getSize());
			stm.setString(7,object.getOwner());
			stm.setString(8,object.getMime());
			stm.setInt(9,object.getParent());
			stm.executeUpdate();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void delete (File object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(DELETE_SQL);
			stm.setString(1,object.getName());
			stm.execute();
		} catch (SQLException e) { 
			throw new PersistenceException(object.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	static void update (File object) throws PersistenceException { 
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection(); 
		try {
			stm = con.prepareStatement(UPDATE_SQL);
			stm.setString(1,object.getCode());
			stm.setString(2,object.getName());
			stm.setString(3,object.getExtension());
			stm.setString(4,object.getDescription());
			stm.setString(5,object.getTags());
			stm.setLong(6,object.getSize());
			stm.setString(7,object.getOwner());
			stm.setString(8,object.getMime());
			stm.setInt(9,object.getParent());
			stm.setString(10,object.getCode());
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
