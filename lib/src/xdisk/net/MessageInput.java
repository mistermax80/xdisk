package xdisk.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Filtro input stream base astratto, per la ricezione su stream di messaggi.
 * Estende la classe {@link DataInputStream} aggiungendo le funzionalità
 * di ricezione dei messaggi.
 * 
 * @author Fabrizio Filieri
 * @version 1.0 2/11/2009
 */
public abstract class MessageInput extends DataInputStream
{

	/**
	 * Crea un nuovo {@link MessageInput} attaccato allo stream input.
	 * @param in
	 */
	public MessageInput(InputStream in) 
	{
		super(in);
	}
	
	/**
	 * Riceve un messaggio dall'input stream. La chiamata è bloccante.
	 * @throws IOException
	 */
	public abstract void receive() throws IOException;

}
