package xdisk.genPersistence;

import xdisk.exception.PersistenceException;
import xdisk.persistence.database.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Generate {

	/**
	 * @param args
	 * @throws PersistenceException 
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public static void generate(String tableName,String path, String pack) throws Exception{
		ResultSet rstFields=null;
		PreparedStatement stm=null;
		Connection con = DatabaseConnectionFactory.getConnection();

		String className = GenerateDAO.toCamel(tableName);
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
	}

	public static void main(String[] args) throws PersistenceException{

		String path = "/home/fabrizio/workspace/XStonePlugin/src/it/multiarte/xstone/plugin/dvs/persistence/";//"/home/massimo/workspace/lib/src/xdisk/persistence/";
		String pack = "it.multiarte.xstone.plugin.dvs.persistence";//"xdisk.persistence";
		String schema = "dvs_dev";
		String[] exclusionTable = {};//{"client","disk","download","user","file","ownership","folder"};

		String tableName = "dvs_roles";
		boolean onlyTable = true;

		Connection con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		ResultSet rstTable=null;
		if(!onlyTable){

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
					tableName = rstTable.getString("table_name");
					exclude=false;
					for(int k=0;k<exclusionTable.length;k++)
						if(tableName.equalsIgnoreCase(exclusionTable[k]))
							exclude=true;
					if(!exclude){
						stm = con.prepareStatement("USE "+schema+";");
						stm.execute();
						generate(tableName, path, pack);
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
		else{
			try {
				System.out.println("Inizio generazione file per la sola tabella"+tableName);
				generate(tableName, path, pack);
				System.out.println("Fine generazione file"+tableName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
