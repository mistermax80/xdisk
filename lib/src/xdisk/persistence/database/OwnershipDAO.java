package xdisk.persistence.database;


import java.sql.*;
/*
 * Data Access Object per l’entita’ ownership.
 * Incapsula le funzioni ed i tipi dato necessari
 * per manipolare le informazioni
 * della base dati pertinenti a detta entita’.
 * Si tratta di una utility class
 * non istanziabile.
 * Unici possibili metodi presenti, insert(),load(),delete(),update()
 */

import xdisk.exception.PersistenceException;
import xdisk.persistence.Ownership;

class OwnershipDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO ownership" +
		"(file,utente) " +
		"VALUES (?, ?)";

	private static final String SELECT_SQL = 
		"SELECT file,utente " +
		"FROM ownership " +
		"WHERE file = ?";

	private static final String DELETE_SQL = 
		"DELETE FROM ownership WHERE utente=? AND file=?";

	private OwnershipDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param ownership
	 * @throws PersistenceException
	 */
	static void load (Ownership ownership) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_SQL);
			stm.setString(1, ownership.getFile());
			rst=stm.executeQuery();
			rst.next();
			ownership.setFile(rst.getString("file"));
			ownership.setUser(rst.getString("user"));
		} catch (SQLException e) {
			throw new PersistenceException(ownership.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza ownership nel database
	 * @param ownership
	 * @throws PersistenceException
	 */
	static void insert (Ownership ownership) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,ownership.getFile());
			stm.setString(2,ownership.getUser());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(ownership.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param ownership
	 * @throws PersistenceException
	 */
	static void delete (Ownership ownership) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_SQL);
			stm.setString(1,ownership.getUser());
			stm.setString(2,ownership.getFile());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(ownership.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}