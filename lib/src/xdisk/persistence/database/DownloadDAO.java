package xdisk.persistence.database;


import java.sql.*;
/*
 * Data Access Object per l’entita’ download.
 * Incapsula le funzioni ed i tipi dato necessari
 * per manipolare le informazioni
 * della base dati pertinenti a detta entita’.
 * Si tratta di una utility class
 * non istanziabile.
 * Unici possibili metodi presenti, insert(),load(),delete(),update()
 */

import xdisk.exception.PersistenceException;
import xdisk.persistence.Download;

class DownloadDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO download" +
		"(ticket,file,utente,timestamp) " +
		"VALUES (?, ?, ?, ?)";

	private static final String UPDATE_BY_TICKET_SQL = 
		"UPDATE download SET ticket = ?, file = ?, timestamp = ? " +
		"WHERE nome = ?";

	private static final String SELECT_BY_TICKET_SQL = 
		"SELECT ticket,file,utente,timestamp " +
		"FROM download " +
		"WHERE nome = ?";

	private static final String DELETE_BY_TICKET_SQL = 
		"DELETE FROM download WHERE ticket = ?";

	private DownloadDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param download
	 * @throws PersistenceException
	 */
	static void load (Download download) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_BY_TICKET_SQL);
			stm.setInt(1, download.getTicket());
			rst=stm.executeQuery();
			rst.next();
			download.setTicket(rst.getInt("ticket"));
			download.setFile(rst.getInt("file"));
			download.setUser(rst.getString("utente"));
			download.setTimestamp(rst.getTimestamp("timestamp"));
		} catch (SQLException e) {
			throw new PersistenceException(download.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza download nel database
	 * @param download
	 * @throws PersistenceException
	 */
	static void insert (Download download) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setInt(1,download.getTicket());
			stm.setInt(2,download.getFile());
			stm.setString(3,download.getUser());
			stm.setTimestamp(4,download.getTimestamp());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(download.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param download
	 * @throws PersistenceException
	 */
	static void delete (Download download) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_BY_TICKET_SQL);
			stm.setInt(1,download.getTicket());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(download.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’aggiornamento nel database dell'istanza
	 * @param download
	 * @throws PersistenceException
	 */
	static void update (Download download) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(UPDATE_BY_TICKET_SQL);
			stm.setInt(1,download.getTicket());
			stm.setInt(2,download.getFile());
			stm.setString(3,download.getUser());
			stm.setTimestamp(4,download.getTimestamp());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(download.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}