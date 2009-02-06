package xdisk.downloader;

import java.util.ArrayList;
import java.util.HashMap;

import xdisk.ClientResource;
import xdisk.VirtualFile;

/**
 * La classe che si occupa dello scaricamento del file dalle varie fonti client.
 * Sarà possibile fermare e far ripartire il download ed aggiungere i client alla
 * lista delle fonti. L'aggiunta delle fonti (ip e address), avviene in maniera
 * dinamica.
 * @author biio
 * @version 6/2/2009
 */
public class Downloader 
{
	private VirtualFile virtualFile;
	
	private static final int TOKEN_SIZE = 1024;
	
	private ArrayList<ClientResource> source;
	private HashMap<Integer, Token> tokens;
	private HashMap<Integer, Token> tokensCompleted;
	private HashMap<Integer, Token> tokensAvaible;
	
	
	/**
	 * Crea un nuovo oggetto downloader per scaricare un {@link VirtualFile}
	 * da diverse fonti.
	 * @param virtualFile
	 */
	public Downloader(VirtualFile virtualFile) 
	{
		this.virtualFile = virtualFile;
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	public void addSource(ClientResource source)
	{
		
	}
	
	protected void savePartFile()
	{
		
	}
	
	protected void loadPartFile()
	{
		
	}
}
