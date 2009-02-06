package xdisk.downloader;

/**
 * Il token di file.
 * @author biio
 * @version 6/2/2009
 */
public class Token 
{
	private int offset;
	private int size;
	private boolean completed;
	private byte[] data;
	
	/**
	 * Crea un nuovo oggetto token
	 * @param offset l'osset di partenza del token
	 * @param size la dimensione del token
	 */
	public Token(int offset, int size) 
	{
		this.size = size;
		this.offset = offset;
		
		this.completed = false;
		
//		data = new byte[size];
	}
	
	/**
	 * Ritorna l'offset di posizionamento del token
	 * @return l'offset di posizionamento del token
	 */
	public int getOffset()
	{
		return offset;
	}
	
	/**
	 * Ritorna la dimensione del token
	 * @return la dimensione del token
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Ritorna i dati del token
	 * @return i dati del token
	 */
	public byte[] getData()
	{
		return data;
	}
	
	/**
	 * Imposta il buffer dei dati. 
	 * @param buffer il buffer dei dati.
	 */
	public void setData(byte[] buffer)
	{
		this.data = buffer;
	}

	/**
	 * Ritorna true se è il token è stato completamente scaricato.
	 * @return true se è il token è stato completamente scaricato.
	 */
	public boolean isCompleted() 
	{
		return completed;
	}

	/**
	 * Imposta se il token è stato completamente scaricato.
	 * @param completed true se il token è stato completamente scaricato.
	 */
	public void setCompleted(boolean completed) 
	{
		this.completed = completed;
	}
	
	
	
	
}
