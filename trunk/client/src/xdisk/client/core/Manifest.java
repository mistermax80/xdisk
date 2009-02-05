package xdisk.client.core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xdisk.utils.XMLFile;

/**
 * Classe di utilità per la lettura del file manifest del disco e la creazione
 * del {@link VirtualDisk} relativo.
 * 
 * @author Fabrizio Filieri
 * @version 3/2/2009
 *
 */
public class Manifest
{
	private Document doc;
	
	public VirtualDisk getVirtualDisk(String filename)
	{
		VirtualDisk virtualDisk = new VirtualDisk();

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

		// scansione del file manifest
		virtualDisk.setName(getValue("name"));
		virtualDisk.setDescription(getValue("description"));
		virtualDisk.setServerAddress(getValue("serveraddress"));
		virtualDisk.setServerPort(new Integer(getValue("serverport")));
		virtualDisk.setWebPanelAddress(getValue("webpaneladdress"));
		virtualDisk.setWebPanelPort(new Integer(getValue("webpanelport")));
		virtualDisk.setUserid(getValue("userid"));
		virtualDisk.setPassword(getValue("password"));
		//virtualDisk.setUserid(getValue("localport"));
		// creazine dell'oggetto del disco virtuale
		return virtualDisk;
	}
		
	public String getValue(String value)
	{
		Element elem = (Element)doc.getElementsByTagName(value).item(0);
	    NodeList fstNm = elem.getChildNodes();
	    return ((Node) fstNm.item(0)).getNodeValue();
	}
}
