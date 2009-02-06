package xdisk.test.net;

import java.net.UnknownHostException;

import xdisk.VirtualFile;
import xdisk.client.core.VirtualDisk;

public class TestProtocol {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, Exception {

		VirtualDisk xdisk = new VirtualDisk("DISCO1","DESCR","localhost",4444,	"",8080,"ciips","c", 0);
		xdisk.connect();
		if(true){
			for(int i=0;i<12;i++){

				System.out.println("Ogni 15 secondi, minuti trascorsi:"+i/4);
				Thread.sleep(1000*15);
			}
		}
		xdisk.keepAlive();
	}

}
