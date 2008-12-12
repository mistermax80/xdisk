package xdisk.net;

import java.net.Socket;

public interface ServerProcess 
{
	public void request(Socket client); 
	
	public void requestError(Socket client);
	
}
