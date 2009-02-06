package xdisk.net;

import java.io.IOException;
import java.io.OutputStream;

import xdisk.ClientResource;
import xdisk.VirtualFile;
import xdisk.VirtualFolder;

/**
 * MessageOutputStream del disco virtuale. Aggiunge le funzionalità di invio 
 * di tipi complessi specifici del disco virtuale. 
 * @author Fabrizio Filieri
 * @version 1/2/2009
 */
public class XDiskOutputStream extends MessageOutputStream 
{

	/**
	 * Crea un nuovo XDiskOutputStream attaccato ad un OutputStream
	 * @param out l'OutputStream al quale attaccare il MessageOutputStream
	 */
	public XDiskOutputStream(OutputStream out) 
	{
		super(out);
	}
	
	/**
	 * Aggiunge al messaggio il tipo complesso {@link VirtualFile} 
	 * @param file il file virtuale da aggiungere al messaggio
	 * @throws IOException in caso di errore di input
	 */
	public void writeVirtualFile(VirtualFile file) throws IOException
	{
		writeUTF(file.getFilename());
		writeUTF(file.getExtension());
		writeUTF(file.getDescription());
		writeUTF(file.getOwner());
		writeLong(file.getSize());
		writeUTF(file.getTags());
		writeUTF(file.getMime());
		writeUTF(file.getPath());
	}
	
	/**
	 * Aggiunge al messaggio il tipo complesso {@link VirtualFolder}
	 * @param folder la directory virtuale da aggiungere al messaggio
	 * @throws IOException in caso di errore genera un'eccezione
	 */
	public void writeVirtualFolder(VirtualFolder folder) throws IOException
	{
		writeUTF(folder.getPath());
	}
	
	
	/**
	 * Aggiunge al messaggio il tipo complesso {@link ClientResource} 
	 * @param client la risorsa client da aggiungere al messaggio
	 * @throws IOException in caso di errore viene generata un'eccezione
	 */
	public void writeClientResource(ClientResource client) throws IOException
	{
		writeUTF(client.getIp());
		writeInt(client.getPort());
	}
	

}
