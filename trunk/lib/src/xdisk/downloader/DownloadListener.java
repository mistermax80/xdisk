package xdisk.downloader;

import xdisk.VirtualFile;

public interface DownloadListener 
{
	public void completed(VirtualFile virtualFile);
}
