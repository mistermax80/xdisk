package xdisk.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Filtro stream base astratto per la gestione di messaggi sugli stream.
 * Estende il filtro {@link DataOutputStream} per aggiungere funzionalità
 * di creazione dei messaggi.
 * 
 * @author Fabrizio Filieri
 * @version 1.0 2/1/2009
 */
public abstract class MessageOutput extends DataOutputStream
{

	/**
	 * Crea un nuovo {@link MessageOutput} attaccato allo stream out
	 * @param out Lo stream a cui è attaccato il filtro
	 */
	public MessageOutput(OutputStream out) 
	{
		super(out);
	}
	
	/**
	 * Invia il contenuto del messaggio presente nel buffer.
	 * 
	 * @throws IOException
	 */
	public abstract void send() throws IOException;

}
