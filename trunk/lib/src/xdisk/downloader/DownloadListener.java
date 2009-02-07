package xdisk.downloader;

import xdisk.VirtualFile;

public interface DownloadListener 
{
	public void completed(String filename, VirtualFile virtualFile);
}
