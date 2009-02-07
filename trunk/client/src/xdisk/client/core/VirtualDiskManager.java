package xdisk.client.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;

import xdisk.utils.XMLFile;


/**
 * Classe singleton per la gestione dei dischi del client.
 *  
 * @author biio
 * @version 31/1/2009
 */
public class VirtualDiskManager 
{
	private String filename;
	private static VirtualDiskManager instance = null;
	private ArrayList<VirtualDisk> virtualDisks;
	
	/**
	 * Costruttore privato della classe
	 */
	private VirtualDiskManager() 
	{
		virtualDisks = new ArrayList<VirtualDisk>();
		
		filename = System.getProperty("user.dir") + "/VirtualDiskConfig.xml";
		
		readConfig();
	}
	
	/**
	 * Ritorna l'istanza del {@link VirtualDiskManager}
	 * @return l'istanza del {@link VirtualDiskManager}
	 */
	public static synchronized VirtualDiskManager getInstance()
	{
		if (instance == null)
			instance = new VirtualDiskManager();

		return instance;
	}
	
	/**
	 * Aggiunge un {@link VirtualDisk} al client
	 * @param virtualDisk il {@link VirtualDisk} da aggiungere al client
	 */
	public void add(VirtualDisk virtualDisk)
	{
		virtualDisks.add(virtualDisk);
		saveConfig();
	}
	
	/**
	 * Rimuove un disco virtuale dal client
	 * @param address l'indirizzo del disco virtuale da aggiungere
	 */
	public void removeByAddress(String address)
	{
		for (int i=0; i<virtualDisks.size(); i++)
		{
			if (virtualDisks.get(i).getServerAddress().toLowerCase().equals(address.toLowerCase()))
				virtualDisks.remove(i);
		}
		
		saveConfig();
	}
	
	/**
	 * Ritorna il numero dei dischi virtuali presenti sul client
	 * @return il numero dei dischi virtuali presenti sul client
	 */
	public int getNum()
	{
		return virtualDisks.size();
	}
	
	/**
	 * Ritorna il disco virtuale i-esimo 
	 * @param index l'indice del disco virtuale presente sul client
	 * @return l'i-esimo disco virtuale
	 */
	public VirtualDisk get(int index)
	{
		return virtualDisks.get(index);
	}
	
	/**
	 * Salva la configurazione corrente dei dischi in locale in un file XML
	 * di configurazione.
	 */
	public void saveConfig()
	{
		try 
		{
			PrintStream fileOut = new PrintStream(new FileOutputStream(new File(filename)));
			
			fileOut.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			
			Iterator<VirtualDisk> vd = virtualDisks.iterator();
			
			fileOut.println("<VirtualDisks>");
			
			while (vd.hasNext())
			{
				fileOut.println("\t<VirtualDisk>");
				
				VirtualDisk virtualDisk = vd.next();
				
				fileOut.println("\t\t<name>" + virtualDisk.getName() + "</name>");
				fileOut.println("\t\t<serverAddress>" + virtualDisk.getServerAddress() + "</serverAddress>");
				fileOut.println("\t\t<serverPort>" + virtualDisk.getServerPort() + "</serverPort>");
				fileOut.println("\t\t<webPanelAddress>" + virtualDisk.getWebPanelAddress() + "</webPanelAddress>");
				fileOut.println("\t\t<webPanelPort>" + virtualDisk.getWebPanelPort() + "</webPanelPort>");
				fileOut.println("\t\t<description>" + virtualDisk.getDescription() + "</description>");
				fileOut.println("\t\t<userid>" + virtualDisk.getUserid() + "</userid>");
				fileOut.println("\t\t<password>" + virtualDisk.getPassword() + "</password>");
				fileOut.println("\t\t<localPort>" + virtualDisk.getLocalPort() + "</localPort>\n");
				
				fileOut.println("\t</VirtualDisk>");				
			}
			
			fileOut.println("</VirtualDisks>");
			
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
	
	/**
	 * Legge la configurazione dei dischi virtuali presenti sul client dal 
	 * file XML di configurazione.
	 */
	protected void readConfig()
	{
		Document doc = null;
		
		virtualDisks.clear();
		
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
		
		NodeList files = doc.getElementsByTagName("VirtualDisk");
		
		for (int i=0; i<files.getLength(); i++)
		{
			addNode(files.item(i));			
		}
	}
	
	/**
	 * Aggiunge il nodo VirtualDisk alla libreria
	 * @param node il nodo VirtualDisk da aggiungere
	 */
	private void addNode(Node node)
	{
		NodeList child = node.getChildNodes();
		VirtualDisk virtualDisk = new VirtualDisk();
			
//		System.out.println("Add node: " + node.getNodeName() + " - figli:" + child.getLength());
		for (int i=0; i<child.getLength(); i++)
		{
			System.out.println(child.item(i).getNodeName() + " = " + child.item(i).getTextContent());
			if (isTag(child.item(i), "name"))
				virtualDisk.setName(child.item(i).getTextContent());
			else if (isTag(child.item(i), "serverAddress"))
				virtualDisk.setServerAddress(child.item(i).getTextContent());
			else if (isTag(child.item(i), "serverPort"))
				virtualDisk.setServerPort(new Integer(child.item(i).getTextContent()));
			else if (isTag(child.item(i), "webPanelAddress"))
				virtualDisk.setWebPanelAddress(child.item(i).getTextContent());
			else if (isTag(child.item(i), "webPanelPort"))
				virtualDisk.setWebPanelPort(new Integer(child.item(i).getTextContent()));
			else if (isTag(child.item(i), "description"))
				virtualDisk.setDescription(child.item(i).getTextContent());
			else if (isTag(child.item(i), "userid"))
				virtualDisk.setUserid(child.item(i).getTextContent());
			else if (isTag(child.item(i), "password"))
				virtualDisk.setPassword(child.item(i).getTextContent());
			else if (isTag(child.item(i), "localPort"))
				virtualDisk.setLocalPort(new Integer(child.item(i).getTextContent()));
		}
		
		virtualDisks.add(virtualDisk);
		
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

	
}
