package xdisk.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Classe astratta per la lettura di file in formato XML
 * @author Fabrizio Filieri
 * @version 3/2/2009
 *
 */
public class XMLFile 
{
	
	public static Document load(File file) throws ParserConfigurationException, 
					SAXException, IOException
	{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		
		return doc;
	}
}
