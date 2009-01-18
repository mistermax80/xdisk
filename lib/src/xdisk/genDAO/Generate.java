package xdisk.genDAO;

import java.sql.SQLException;

public class Generate {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws Exception {

		String path = "/home/massimo/workspace/lib/src/xdisk/persistence/";
		String pack = "xdisk.persistence";
		
		//SELECT table_name FROM `TABLES` T WHERE table_schema="xdisk";
		

		String className = "Folder";
		String tableName = "folder";
		
		GenerateClass genClass = new GenerateClass(className,tableName,path,pack);
		genClass.generate();
		GenerateDAO genDao = new GenerateDAO(className,tableName,path,pack);
		genDao.generate();
		GenerateController genController = new GenerateController(className,tableName,path,pack);
		genController.generate();
		GenerateDCS genDCS = new GenerateDCS(className,tableName,path,pack);
		genDCS.generate();
	}

}
