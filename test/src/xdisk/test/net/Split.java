package xdisk.test.net;

public class Split {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "file.kkk";
		String[] paths = path.split("\\p{Punct}");
		System.out.println(path);
		
		for(int i=0;i<paths.length;i++){
			System.out.println(paths[i]);
		}

	}

}
