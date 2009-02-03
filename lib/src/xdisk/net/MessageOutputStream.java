package xdisk.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Filtro che attaccato ad un {@link OutputStream}, permette l'invio di messaggi
 * composti da tipi primitivi (derivati da {@link DataOutputStream}), corredati
 * dalla metainformazione sull lunghezza del messaggio.
 * 
 * @author Fabrizio Filieri
 * @version 1.0 2/11/2009
 *
 */
public class MessageOutputStream extends MessageOutput 
{
	/**
	 * l'output stream attaccato al filtro.
	 */
	protected OutputStream outputStream;
	
	/**
	 * Il filtro data output stream utilizzato per l'invio delle informazioni 
	 * del messaggio.
	 */
	protected DataOutputStream dataOutput;
	
	/**
	 * Il buffer utilizzato per la creazione del messaggio.
	 */
	protected ByteArrayOutputStream buffer;
	
	/**
	 * Crea un nuovo filtro per l'invio di messaggi su un output stream.
	 * @param out l'output stream al quale è agganciato il filtro.
	 */
	public MessageOutputStream(OutputStream out) 
	{
		super(new ByteArrayOutputStream());
		buffer = (ByteArrayOutputStream)this.out;
		
		outputStream = out;
		
		dataOutput = new DataOutputStream(outputStream);
	}

	@Override
	public void send() throws IOException 
	{
		synchronized (outputStream) 
		{
			dataOutput.writeInt(buffer.size());
			
			buffer.writeTo(outputStream);
		}
		buffer.reset();
		
		outputStream.flush();		
	}
	
	public void reset() throws IOException 
	{
		buffer.reset();		
		outputStream.flush();		
	}
	

}
