package xdisk.net;

import java.io.IOException;
import java.io.InputStream;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualFolder;

/**
 * MessageInputStream del disco virtuale. Aggiunge le funzionalità di lettura
 * dall'InputStream agganciato, di tipi primitivi specifici del disco virtuale.
 * @author biio
 *
 */
public class XDiskInputStream extends MessageInputStream 
{

	/**
	 * Crea un nuovo {@link MessageInputStream} attaccato ad un 
	 * {@link InputStream} di tipo XDiskInputStream
	 * @param in l'InputStream da agganciare al {@link MessageOutputStream}
	 */
	public XDiskInputStream(InputStream in) 
	{
		super(in);
	}
	
	/**
	 * Legge da un messaggio, un {@link VirtualFile}
	 * @return il {@link VirtualFile} letto dal messaggio
	 * @throws IOException in caso di errore viene generata l'eccezione
	 */
	public VirtualFile readVirtualFile() throws IOException
	{
		VirtualFile file = new VirtualFile();
	
		file.setFilename(readUTF());
		file.setExtension(readUTF());
		file.setDescription(readUTF());
		file.setOwner(readUTF());
		file.setSize(readInt());
		file.setTags(readUTF());
		file.setMime(readUTF());
		file.setPath(readUTF());
		
		return file;
	}
	
	/**
	 * Legge da un messaggio il tipo complesso {@link VirtualFolder}
	 * @return la directory virtuale del disco
	 * @throws IOException in caso di errore genera un'eccezione
	 */
	public VirtualFolder readVirtualFolder() throws IOException
	{
		VirtualFolder folder = new VirtualFolder();
		
		folder.setPath(readUTF());
		
		return folder;
	}
	
	/**
	 * Legge da un messaggio, il tipo complesso {@link ClientResource}
	 * @return il {@link ClientResource}
	 * @throws IOException in caso di errore viene generata un'eccezione
	 */
	public ClientResource readClientResource() throws IOException
	{
		ClientResource client = new ClientResource(readUTF(), readInt());
		return client;
	}

}
