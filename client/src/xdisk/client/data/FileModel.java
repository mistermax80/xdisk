package xdisk.client.data;
import xdisk.VirtualFile;

public class FileModel{

		private String name;
		private String ext;
		private int size;
		private String path;
		private String owner;
		private String tags;
		private String mime;
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
		}

		public VirtualFile getFile() {
			return file;
		}

		public void setFile(VirtualFile file) {
			this.file = file;
		}

		public String toString(){
			return path+name+"."+ext+"  [(s:"+size+")(t:"+tags+")(own:"+owner+")(m:"+mime+")]";
		}
	}