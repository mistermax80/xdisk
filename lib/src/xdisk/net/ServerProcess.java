package xdisk.net;

import java.net.Socket;

public interface ServerProcess 
{
	public abstract void request(Socket client); 
}
