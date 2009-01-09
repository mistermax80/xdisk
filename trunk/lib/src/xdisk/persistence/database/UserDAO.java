package xdisk.persistence.database;


import java.sql.*;

import xdisk.exception.PersistenceException;
import xdisk.persistence.User;

/*
 * Data Access Object per l’entita’ user.
 * Incapsula le funzioni ed i tipi dato necessari
 * per manipolare le informazioni
 * della base dati pertinenti a detta entita’.
 * Si tratta di una utility class
 * non istanziabile.
 * Unici possibili metodi presenti, insert(),load(),delete(),update()
 */


class UserDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO user" +
		"(userid,nome,password,email,admin) " +
		"VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE_BY_USERID_SQL = 
		"UPDATE user SET password = ?, nome = ?,  email = ? , admin = ? " +
		"WHERE userid = ?";

	private static final String SELECT_BY_USERID_SQL = 
		"SELECT userid,nome,password,email,admin " +
		"FROM user " +
		"WHERE userid = ?";

	private static final String DELETE_BY_USERID_SQL = 
		"DELETE FROM user WHERE nome = ?";

	private UserDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param user
	 * @throws PersistenceException
	 */
	static void load (User user) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_BY_USERID_SQL);
			stm.setString(1, user.getUsername());
			System.out.println("********"+stm);
			rst=stm.executeQuery();
			rst.next();
			user.setUsername(rst.getString("userid"));
			user.setName(rst.getString("nome"));
			user.setPassword(rst.getString("password"));
			user.setEmail(rst.getString("email"));
			user.setAdmin(rst.getBoolean("admin"));
		} catch (SQLException e) {
			throw new PersistenceException(user.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza user nel database
	 * @param user
	 * @throws PersistenceException
	 */
	static void insert (User user) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,user.getUsername());
			stm.setString(2,user.getName());
			stm.setString(3,user.getPassword());
			stm.setString(4,user.getEmail());
			stm.setBoolean(5,user.getAdmin());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(user.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param user
	 * @throws PersistenceException
	 */
	static void delete (User user) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_BY_USERID_SQL);
			stm.setString(1,user.getUsername());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(user.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’aggiornamento nel database dell'istanza
	 * @param user
	 * @throws PersistenceException
	 */
	static void update (User user) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(UPDATE_BY_USERID_SQL);
			stm.setString(1,user.getUsername());
			stm.setString(2,user.getName());
			stm.setString(3,user.getPassword());
			stm.setString(4,user.getEmail());
			stm.setBoolean(5,user.getAdmin());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(user.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}