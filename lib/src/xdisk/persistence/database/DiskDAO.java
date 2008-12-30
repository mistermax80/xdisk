package xdisk.persistence.database;


import java.sql.*;
/*
 * Data Access Object per l’entita’ disk.
 * Incapsula le funzioni ed i tipi dato necessari
 * per manipolare le informazioni
 * della base dati pertinenti a detta entita’.
 * Si tratta di una utility class
 * non istanziabile.
 * Unici possibili metodi presenti, insert(),load(),delete(),update()
 */

import xdisk.exception.PersistenceException;
import xdisk.persistence.Disk;

class DiskDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO disk" +
		"(nome,userid,cartellaroot,dimensione) " +
		"VALUES (?, ?, ?, ?)";

	private static final String UPDATE_BY_NAME_SQL = 
		"UPDATE disk SET nome = ?, dimensione = ?, cartellaroot = ? " +
		"WHERE nome = ?";

	private static final String SELECT_BY_NAME_SQL = 
		"SELECT nome,dimensione,userid,cartellaroot " +
		"FROM disk " +
		"WHERE nome = ?";

	private static final String DELETE_BY_NAME_SQL = 
		"DELETE FROM disk WHERE nome = ?";

	private DiskDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param disk
	 * @throws PersistenceException
	 */
	static void load (Disk disk) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_BY_NAME_SQL);
			stm.setString(1, disk.getName());
			rst=stm.executeQuery();
			rst.next();
			disk.setName(rst.getString("nome"));
			disk.setAdmin(rst.getString("userid"));
			disk.setIdRoot(rst.getInt("cartellaroot"));
			disk.setDimension(rst.getInt("dimensione"));
		} catch (SQLException e) {
			throw new PersistenceException(disk.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza disk nel database
	 * @param disk
	 * @throws PersistenceException
	 */
	static void insert (Disk disk) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,disk.getName());
			stm.setString(2,disk.getAdmin());
			stm.setInt(3,disk.getIdRoot());
			stm.setInt(4,disk.getDimension());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(disk.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param disk
	 * @throws PersistenceException
	 */
	static void delete (Disk disk) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_BY_NAME_SQL);
			stm.setString(1,disk.getName());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(disk.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’aggiornamento nel database dell'istanza
	 * @param disk
	 * @throws PersistenceException
	 */
	static void update (Disk disk) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(UPDATE_BY_NAME_SQL);
			stm.setString(1,disk.getName());
			stm.setString(2,disk.getAdmin());
			stm.setInt(3,disk.getIdRoot());
			stm.setInt(4,disk.getDimension());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(disk.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}