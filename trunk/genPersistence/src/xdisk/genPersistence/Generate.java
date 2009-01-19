package xdisk.genPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import xdisk.persistence.database.DatabaseConnectionFactory;

public class Generate {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args){

		String path = "/home/massimo/workspace/lib/src/xdisk/persistence/";
		String pack = "xdisk.persistence";
		String schema = "xdisk";

		Connection con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		ResultSet rstTable=null;
		ResultSet rstFields=null;
		String[] exclusionTable = {"client","disk","download","user","file","ownership","folder"};
		int numTables=0;
		int numTablesExclusion=0;

		try {
			stm = con.prepareStatement("USE information_schema;");
			stm.execute();
			stm = con.prepareStatement("SELECT table_name FROM tables WHERE table_schema=?;");
			stm.setString(1, schema);
			rstTable=stm.executeQuery();

			boolean exclude=false;

			while(rstTable.next()){
				String tableName = rstTable.getString("table_name");
				exclude=false;
				for(int k=0;k<exclusionTable.length;k++)
					if(tableName.equalsIgnoreCase(exclusionTable[k]))
						exclude=true;
				if(!exclude){
					String className = GenerateDAO.toCamel(tableName);
					stm = con.prepareStatement("USE "+schema+";");
					stm.execute();
					System.out.println("\n\n*****************Analizzo la tabella: "+tableName+" ***********************\n");
					System.out.println("VVVVVVVVVVVV-----CAMPI Tabella------VVVVVVVVVVVVVVV");
					stm = con.prepareStatement("DESCRIBE "+tableName);
					rstFields=stm.executeQuery();
					int numFields=0;
					while(rstFields.next()){
						System.out.println("field:"+rstFields.getString("FIELD")+" type:"+rstFields.getString("TYPE"));
						if(rstFields.getString("Key").equalsIgnoreCase("PRI")){
							String key=rstFields.getString("FIELD").toLowerCase();
							String typeKey=GenerateDAO.selectType(rstFields);
							System.out.println("Trovata chiave '"+key+"':'"+typeKey+"'");
						}
						numFields++;
					}
					System.out.println("^^^^^^^^^^^^^^^-----CAMPI Tabella------^^^^^^^^^^^^^^^^\n");
					System.out.println("1/4 - Inizio generazione Classe:"+className+".java");
					GenerateClass genClass = new GenerateClass(className,tableName,path,pack);
					genClass.generate();
					System.out.println("Generazione terminata per la Classe:"+className+".java");
					System.out.println("2/4 - Inizio generazione Classe:"+className+"DAO.java");
					GenerateDAO genDao = new GenerateDAO(className,tableName,path,pack);
					genDao.generate();
					System.out.println("Generazione terminata per la Classe:"+className+"DAO.java");
					System.out.println("3/4 - Inizio generazione Classe:"+className+".Controllerjava");
					GenerateController genController = new GenerateController(className,tableName,path,pack);
					genController.generate();
					System.out.println("Generazione terminata per la Classe:"+className+"Controller.java");
					System.out.println("4/4 - Inizio generazione Classe:"+className+"DCS.java");
					GenerateDCS genDCS = new GenerateDCS(className,tableName,path,pack);
					genDCS.generate();
					System.out.println("Generazione terminata per la Classe:"+className+"DCS.java");
					System.out.println("*****************Fine analisi tabella:"+tableName+"***********************");
					numTables++;
				}
				else{
					numTablesExclusion++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Numero tabelle analizzate:"+numTables+" tabelle escluse:"+numTablesExclusion);
	}
}
