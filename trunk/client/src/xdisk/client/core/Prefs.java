package xdisk.client.core;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import xdisk.exception.PersistenceException;

public class Prefs {
	private static final String URL = "url";
	private static final String PORT = "port";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	
	private static final String url = "<192.168.0.1|www.server.com>";
	private static final int port = 4444;
	private static final String username = "<username>";
	private static final String password = "<password>";
	
	static Preferences prefs;
	
	public Prefs(){
		prefs = Preferences.userNodeForPackage(Prefs.class);
	}
	
	public String getUrl(){
		return prefs.get(URL,url);
	}
	public int getPort(){
		return prefs.getInt(PORT,port);
	}
	public String getUsername(){
		return prefs.get(USERNAME,username);
	}
	public String getPassword(){
		return prefs.get(PASSWORD,password);
	}
	public void putUrl(String url){
		prefs.put(URL,url);
	}
	public void putPort(String port){
		prefs.put(PORT,port);
	}
	public void putUsername(String username){
		prefs.put(USERNAME,username);
	}
	public void putPassword(String password){
		prefs.put(PASSWORD,password);
	}
	
	public void put(String url, String port, String username, String password){
		prefs.put(URL,url );
		prefs.put(PORT,port );
		prefs.put(USERNAME,username);
		prefs.put(PASSWORD,password);
	}
	
	public void storeData() throws PersistenceException{
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
			throw new PersistenceException("Errore nel salvataggio preferences!!!",e);
		}
	}
	
	public void clearData() throws PersistenceException{
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			e.printStackTrace();
			throw new PersistenceException("Errore nel salvataggio preferences!!!",e);
		}
	}
	
}