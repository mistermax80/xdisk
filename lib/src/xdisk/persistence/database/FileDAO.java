package xdisk.persistence.database;


import java.sql.*;
/*
 * Data Access Object per l’entita’ file.
 * Incapsula le funzioni ed i tipi dato necessari
 * per manipolare le informazioni
 * della base dati pertinenti a detta entita’.
 * Si tratta di una utility class
 * non istanziabile.
 * Unici possibili metodi presenti, insert(),load(),delete(),update()
 */

import xdisk.exception.PersistenceException;
import xdisk.persistence.File;

class FileDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO file" +
		"(codice,nome,dimensione,cartella,autore,utenteins) " +
		"VALUES (?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_BY_CODICE_SQL = 
		"UPDATE file SET codice = ?, nome = ?, dimensione = ?, cartella = ?, autore = ?, utenteisn = ? " +
		"WHERE codice = ?";

	private static final String SELECT_BY_CODICE_SQL = 
		"SELECT codice,nome,dimensione,cartella,autore,utenteins " +
		"FROM file " +
		"WHERE codice = ?";

	private static final String DELETE_BY_CODICE_SQL = 
		"DELETE FROM file WHERE codice = ?";

	private FileDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param file
	 * @throws PersistenceException
	 */
	static void load (File file) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_BY_CODICE_SQL);
			stm.setString(1, file.getName());
			rst=stm.executeQuery();
			rst.next();
			file.setCode(rst.getInt("code"));
			file.setName(rst.getString("nome"));
			file.setDimension(rst.getInt("dimensione"));
			file.setFolder(rst.getInt("cartella"));
			file.setAuthor(rst.getString("autore"));
			file.setLoaderUserid(rst.getString("utenteinn"));
		} catch (SQLException e) {
			throw new PersistenceException(file.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza file nel database
	 * @param file
	 * @throws PersistenceException
	 */
	static void insert (File file) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setInt(1,file.getCode());
			stm.setString(2,file.getName());
			stm.setInt(3,file.getDimension());
			stm.setInt(4,file.getFolder());
			stm.setString(5,file.getAuthor());
			stm.setString(6,file.getLoaderUserid());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(file.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param file
	 * @throws PersistenceException
	 */
	static void delete (File file) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_BY_CODICE_SQL);
			stm.setString(1,file.getName());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(file.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’aggiornamento nel database dell'istanza
	 * @param file
	 * @throws PersistenceException
	 */
	static void update (File file) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(UPDATE_BY_CODICE_SQL);
			stm.setInt(1,file.getCode());
			stm.setString(2,file.getName());
			stm.setInt(3,file.getDimension());
			stm.setInt(4,file.getFolder());
			stm.setString(5,file.getAuthor());
			stm.setString(6,file.getLoaderUserid());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(file.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}