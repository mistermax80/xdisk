package xdisk;

/**
 * Interfaccia per il tipo di dato risorsa del disco
 * @author biio
 *
 */
public interface VirtualResource 
{
	/**
	 * Indica se la risorsa è un file
	 * @return true se la risorsa è un file, false altrimenti
	 */
	public abstract boolean isFile();
	
	/**
	 * Indica se la risorsa è una directory
	 * @return true se la risorsa è una directory, false altrimenti
	 */
	public abstract boolean isDirectory();
}
