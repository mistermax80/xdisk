package xdisk.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Filtro di input per la ricezione di messaggi su un input stream agganciato.
 *  
 * @author Fabrizio Filieri
 * @version 1.0 2/11/2009
 *
 */
public class MessageInputStream extends MessageInput 
{
	/**
	 * L'input stream agganciato al filtro
	 */
	protected InputStream inputStream;
	
	/**
	 * Il filtro utilizzato per la ricezione delle meta informazioni agganciato
	 * all'input stream collegato al filtro
	 */
	protected DataInputStream dataInput;
	
	/**
	 * Il buffer di byte con i dati letti dall'input stream
	 */
	protected byte[] buffer;

	/**
	 * Crea un nuovo filtro per la ricezione di messaggi su un input stream 
	 * collegato.
	 * 
	 * @param in 
	 */
	public MessageInputStream(InputStream in) 
	{
		super(null);
		
		inputStream = in;
		
		dataInput = new DataInputStream(inputStream);
	}

	@Override
	public void receive() throws IOException 
	{
		synchronized (inputStream) 
		{
			
			int size = dataInput.readInt();
		
			buffer = new byte[size];
		
			dataInput.readFully(buffer);
		}
		
		this.in = new ByteArrayInputStream(buffer);
	}

}
