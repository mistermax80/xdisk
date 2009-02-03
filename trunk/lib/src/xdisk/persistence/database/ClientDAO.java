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
import xdisk.persistence.Client;

class ClientDAO {

	private static final String INSERT_SQL = 
		"INSERT INTO client" +
		"(userid,indip,porta,tipoconn,session_id) " +
		"VALUES (?, ?, ?, ?, ?)";

	private static final String UPDATE_BY_USERID_SQL = 
		"UPDATE client SET indip = ?, porta = ?, tipoconn = ?, session_id = ? " +
		"WHERE userid = ?";

	private static final String SELECT_BY_USERID_SQL = 
		"SELECT userid,indip,porta,tipoconn,session_id " +
		"FROM client " +
		"WHERE userid = ?";

	private static final String DELETE_BY_USERID_SQL = 
		"DELETE FROM client WHERE userid = ?";

	private ClientDAO(){}

	/**
	 * Comando SQL per l’ottenimento di una nuova istanza
	 * @param client
	 * @throws PersistenceException
	 */
	static void load (Client client) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		ResultSet rst=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(SELECT_BY_USERID_SQL);
			stm.setString(1, client.getUserid());
			rst=stm.executeQuery();
			if(rst.next()){
				client.setUserid(rst.getString("userid"));
				client.setIpAddress(rst.getString("indip"));
				client.setConnType(rst.getString("tipoconn"));
				client.setPortNumber(rst.getInt("porta"));
				client.setIdSession(rst.getString("session_id"));
			}
		} catch (SQLException e) {
			throw new PersistenceException(client.toString(),e);
		}
		finally {
			if (rst != null) try {rst.close();} catch (Exception e) {}
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’inserimento dell'istanza client nel database
	 * @param client
	 * @throws PersistenceException
	 */
	static void insert (Client client) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(INSERT_SQL);
			stm.setString(1,client.getUserid());
			stm.setString(2,client.getIpAddress());
			stm.setInt(3,client.getPortNumber());
			stm.setString(4,client.getConnType());
			stm.setString(5,client.getIdSession());			
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(client.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’eliminazione dell'istanza dal database
	 * @param client
	 * @throws PersistenceException
	 */
	static void delete (Client client) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(DELETE_BY_USERID_SQL);
			stm.setString(1,client.getUserid());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(client.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}

	/**
	 * Comando SQL per l’aggiornamento nel database dell'istanza
	 * @param client
	 * @throws PersistenceException
	 */
	static void update (Client client) throws PersistenceException
	{
		Connection con=null;
		PreparedStatement stm=null;
		con = DatabaseConnectionFactory.getConnection();
		try {
			stm = con.prepareStatement(UPDATE_BY_USERID_SQL);
			stm.setString(1,client.getIpAddress());
			stm.setInt(2,client.getPortNumber());
			stm.setString(3,client.getConnType());
			stm.setString(4,client.getIdSession());
			stm.setString(5,client.getUserid());
			stm.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(client.toString(),e);
		}
		finally {
			if (stm != null) try {stm.close();} catch (Exception e) {}
			if (con != null) try {con.close();} catch (Exception e) {}
		}
	}
}