package xdisk;

/**
 * I client risorsa di uno specifico file del disco virtuale.
 * @author biio
 * @version 31/1/2009
 */
public class ClientResource 
{
	private String ip;
	private int port;
	
	public ClientResource(String ip, int port) 
	{
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Ritorna l'ip del client
	 * @return l'ip del client
	 */
	public String getIp() 
	{
		return ip;
	}

	/**
	 * Ritorna la porta del client
	 * @return la porta del client
	 */
	public int getPort() 
	{
		return port;
	}

}
