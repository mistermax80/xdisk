package xdisk.genDAO;

import java.sql.SQLException;

public class Generate {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws Exception {
		//SELECT table_name FROM `TABLES` T WHERE table_schema="xdisk";
		
		String CLASS_NAME = "Folder";
		String TABLE_NAME = "folder";
		String PATH = "/home/massimo/workspace/lib/src/xdisk/persistence/";
		String PACKAGE = "xdisk.persistence";
		
		GenerateClass genClass = new GenerateClass(CLASS_NAME,TABLE_NAME,PATH,PACKAGE);
		genClass.generate();
		GenerateDao genDao = new GenerateDao(CLASS_NAME,TABLE_NAME,PATH,PACKAGE);
		genDao.generate();
	}

}
