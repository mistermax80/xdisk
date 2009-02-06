package xdisk.client.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;

import xdisk.VirtualFile;
import xdisk.utils.XMLFile;

/**
 * Bibblioteca dei file locali. Esegue il colegamento tra i file del disco 
 * virtuale e quelli reali del disco locale del client. 
 * Permette di ottenere tutti i file presenti localmente sul disco, e i relativi
 * file virtuali associati
 * 
 * @author biio
 * @version 6/11/2009
 */
public class Library 
{
	private String filename;
	
	private HashMap<VirtualFile, String> localFile;	
	
	/**
	 * Crea un nuovo oggetto biblioteca, a partire dal file di configurazione
	 * che contiene tutte le informazioni. 
	 * @param filename il nome del file di configurazione 
	 */
	public Library(String filename) 
	{
		this.filename = filename;
	
		localFile = new HashMap<VirtualFile, String>();
		
		loadFileConfig();		
	}
	
	/**
	 * Aggiunge alla libreria locale un file.
	 * @param filename il nome del file locale
	 * @param virtualFile il {@link VirtualFile} da aggiungere
	 */
	public void add(String filename, VirtualFile virtualFile)
	{
		localFile.put(virtualFile, filename);
		saveFileConfig();
	}
	
	/**
	 * Rimuove un {@link VirtualFile} dalla libreria locale
	 * @param virtualFile il file virtuale da rimuovere
	 */
	public void remove(VirtualFile virtualFile)
	{
		localFile.remove(virtualFile);
		
		saveFileConfig();
	}
	
	/**
	 * Ritorna la {@link Collection} di {@link VirtualFile} presenti sul disco
	 * @return la collection di {@link VirtualFile}
	 */
	public Collection<VirtualFile> getVirtualFile()
	{
		return localFile.keySet();
	}
	
	/**
	 * Ritorna il nome del file di configurazione con i dati dell'asociazione
	 * tra i file virtuali e quelli locali.
	 * @return il nome del file di configurazione.
	 */
	public String getFileName()
	{
		return filename;
	}
	
	/**
	 * Carica i dati di connessione tra i file virtuali e quelli locali dal 
	 * file di configurazione.
	 */
	private void loadFileConfig()
	{
		Document doc = null;
		
		localFile.clear();
		
		try 
		{
			doc = XMLFile.load(new File(filename));
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		NodeList files = doc.getElementsByTagName("file");
		
		for (int i=0; i<files.getLength(); i++)
		{
			addNode(files.item(i));			
		}
	}
	
	/**
	 * Aggiunge il nodo file alla libreria
	 * @param node il nodo file da aggiungere
	 */
	private void addNode(Node node)
	{
		NodeList child = node.getChildNodes();
		String local_filename = new String();
		VirtualFile virtualFile = new VirtualFile();
			
		for (int i=0; i<child.getLength(); i++)
		{
			if (isTag(child.item(i), "local_filename"))
				local_filename = child.item(i).getTextContent();
			else if (isTag(child.item(i), "filename"))
				virtualFile.setFilename(child.item(i).getTextContent());
			else if (isTag(child.item(i), "extension"))
				virtualFile.setExtension(child.item(i).getTextContent());
			else if (isTag(child.item(i), "description"))
				virtualFile.setDescription(child.item(i).getTextContent());
			else if (isTag(child.item(i), "owner"))
				virtualFile.setOwner(child.item(i).getTextContent());
			else if (isTag(child.item(i), "tags"))
				virtualFile.setTags(child.item(i).getTextContent());
			else if (isTag(child.item(i), "size"))
				virtualFile.setSize(new Integer(child.item(i).getTextContent()));
			else if (isTag(child.item(i), "mime"))
				virtualFile.setMime(child.item(i).getTextContent());
			else if (isTag(child.item(i), "path"))
				virtualFile.setPath(child.item(i).getTextContent());
		}
		
		localFile.put(virtualFile, local_filename);
		
	}

	/**
	 * Controlla se il tag passato è di tipo tagName
	 * @param node il nodo da controllare
	 * @param tagName il nome del tag richiesto 
	 * @return true se il nome del nodo è {@link TagName}, false altrimenti.
	 */
	private boolean isTag(Node node, String tagName)
	{
		return node.getNodeName().toLowerCase().indexOf(tagName.toLowerCase()) != -1;
	}

	
	/**
	 * Salva i dati di connessione tra i file virtuali e quelli locali, sul file
	 * di configurazione.
	 */
	private void saveFileConfig()
	{
		try 
		{
			DataOutputStream fileOut = new DataOutputStream(
					new FileOutputStream(new File(filename)));
			
			fileOut.writeUTF("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			
			Iterator<VirtualFile> keys = getVirtualFile().iterator();
			
			fileOut.writeUTF("<library>\n");
			
			while (keys.hasNext())
			{
				fileOut.writeUTF("\t<file>\n");
				
				VirtualFile virtualFile = keys.next();
				String localFileName = localFile.get(virtualFile);
				
				fileOut.writeUTF("\t\t<local_filename>" + localFileName + "</local_filename>\n");
				
				fileOut.writeUTF("\t\t<filename>" + virtualFile.getFilename() + "</filename>\n");
				fileOut.writeUTF("\t\t<extension>" + virtualFile.getExtension() + "</extension>\n");
				fileOut.writeUTF("\t\t<description>" + virtualFile.getDescription() + "</description>\n");
				fileOut.writeUTF("\t\t<owner>" + virtualFile.getOwner() + "</owner>\n");
				fileOut.writeUTF("\t\t<tags>" + virtualFile.getTags() + "</tags>\n");
				fileOut.writeUTF("\t\t<size>" + virtualFile.getSize() + "</size>\n");
				fileOut.writeUTF("\t\t<mime>" + virtualFile.getMime() + "</mime>\n");
				fileOut.writeUTF("\t\t<path>" + virtualFile.getPath() + "</path>\n");
				
				fileOut.writeUTF("\t</file>\n");				
			}
			
			fileOut.writeUTF("</library>\n");
			
			fileOut.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
