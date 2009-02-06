package xdisk.client.data;
import xdisk.VirtualFile;

public class FileModel{

		private String name;
		private String ext;
		private long size;
		private String path;
		private String owner;
		private String tags;
		private String mime;
		private String descr;
		private VirtualFile file;

		public FileModel(VirtualFile file){
			this.file=file;
			name=file.getFilename();
			ext=file.getExtension();
			size=file.getSize();
			path=file.getPath();
			owner=file.getOwner();
			tags=file.getTags();
			mime=file.getMime();
			descr=file.getDescription();
		}

		public VirtualFile getFile() {
			return file;
		}

		public void setFile(VirtualFile file) {
			this.file = file;
		}

		public String toString(){
			return path+name+"."+ext+"  [(d:"+descr+")(s:"+size+")(t:"+tags+")(own:"+owner+")(m:"+mime+")]";
		}
	}