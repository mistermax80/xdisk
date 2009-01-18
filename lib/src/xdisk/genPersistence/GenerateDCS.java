package xdisk.genPersistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import xdisk.persistence.database.DatabaseConnectionFactory;

public class GenerateDCS {

	private String[][] arrayFields;
	private String className;
	private String tableName;
	private Connection con;
	private int numFields;
	private String pack;
	private String key;
	private String typeKey;
	private String path;

	private static final int TYPE=1;
	private static final int FIELD=0;

	public GenerateDCS(String className, String tableName, String path, String pack) throws SQLException {
		super();
		this.className = className;
		this.tableName = tableName;
		this.pack = pack+".database";
		this.path = path;

		con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		ResultSet rst=null;
		stm = con.prepareStatement("DESCRIBE "+tableName);
		rst=stm.executeQuery();
		while(rst.next()){
			if(rst.getString("Key").equalsIgnoreCase("PRI")){
				key=rst.getString("FIELD").toLowerCase();
				typeKey=GenerateDAO.selectType(rst);
				System.out.println("Trovata chiave"+key+" "+typeKey);
			}
			numFields++;
		}
		//String[rows][colums]
		this.arrayFields = new String[numFields][2];
	}

	public String genConstructor() throws SQLException{
		//Costruttore 0 argomenti privato
		String text="\n\nprivate "+className+"DCS() {\nsuper();\n}\n";
		return text;
	}

	public String createClass() throws FileNotFoundException, SQLException{
		String text = "package "+pack+";\n\n";
		text+="\nimport java.sql.*;\nimport xdisk.exception.PersistenceException;" +
		"\nimport xdisk.persistence."+className+";\nimport java.util.Collection;\nimport java.util.Collections;\nimport java.util.LinkedList;";
		text+= "\n\npublic class "+className+"DCS {\n";
		text+= genConstructor()+genQueryRemoveAll()+genRemoveAll()+genQuerySelectAll()
			+genGetAll()+genProcessor()+genObjectToCursor();//+genQuerySelectByKey();
		return text+"\n}";
	}

	public String genQueryRemoveAll() throws SQLException{
		getArrayFields();		
		//REMOVE ALL
		String text= "\nprivate static final String REMOVE_ALL_SQL = \"DELETE FROM "+tableName+"\";\n";
		return text;
	}
	
	public String genQuerySelectAll() throws SQLException{
		getArrayFields();		
		//SELECT ALL
		String text= "\n\nprivate static final String SELECT_ALL_SQL = \"SELECT ";
		for(int i=0;i<arrayFields.length;i++){
			text+=arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				text=text+",";
		}
		text+=" FROM "+tableName+"\";\n";
		return text;
	}
	
	public String genQuerySelectByKey() throws SQLException{
		getArrayFields();		
		//REMOVE ALL
		String text= "\nprivate static final String SELECT_SQL_BY_"+key.toUpperCase()+" = \"SELECT ";
		for(int i=0;i<arrayFields.length;i++){
			text+=arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				text=text+",";
		}		
		text+=" FROM "+tableName+" WHERE "+key+" = ?\";";
		return text;
	}
	
	public String genGetAll(){
		String text="\nstatic Collection<"+GenerateDAO.toCamel(className)+"> getAll() throws PersistenceException { " +
		"\nCollection<"+GenerateDAO.toCamel(className)+"> all=null;\nConnection con=null;\nPreparedStatement stm=null;\nResultSet rst=null;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(SELECT_ALL_SQL);";
		text+="\nrst=stm.executeQuery();all = processCollectionResultSet(rst);";
		text+="\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(e);\n}\nfinally {" +
		"\nif (rst != null) try {rst.close();} catch (Exception e) {}" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\nreturn all;\n}";
		return text;
	}
	
	public String genRemoveAll(){
		String text="\nstatic int removeAll() throws PersistenceException { " +
		"\nConnection con=null;\nPreparedStatement stm=null;\nint rowsDeleted;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(REMOVE_ALL_SQL);";
		text+="\nrowsDeleted = stm.executeUpdate();" +
		"\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(e);\n}\nfinally {" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\nreturn rowsDeleted;\n}";
		return text;
	}
	
	public String genProcessor(){
		String text="\n\nprivate static Collection<"+GenerateDAO.toCamel(className)+"> processCollectionResultSet(ResultSet rst) throws SQLException{\n" +
				"LinkedList<"+GenerateDAO.toCamel(className)+"> all = new LinkedList<"+GenerateDAO.toCamel(className)+">();\n" +
				"while (rst.next()) {\n" +
				"all.add(objectFromCursor(rst));\n}\nreturn Collections.unmodifiableList(all);\n}";
		return text;
	}
	
	public String genObjectToCursor(){
		String text="\n\nprivate static "+GenerateDAO.toCamel(className)+" objectFromCursor(ResultSet rst) throws SQLException{\n" +
		GenerateDAO.toCamel(className)+" object= new "+GenerateDAO.toCamel(className)+"();";
		for(int i=0;i<arrayFields.length;i++){
			text+="\nobject.set"+GenerateDAO.toCamel(arrayFields[i][FIELD])+"(rst.get"+GenerateDAO.toCamel(arrayFields[i][TYPE])+"(\""+arrayFields[i][FIELD]+"\"));";
		}
		text+="\nreturn object;\n}\n";
		return text;
	}

	public String[][] getArrayFields() throws SQLException {
		PreparedStatement stm=null;
		ResultSet rst=null;
		String field="";
		String type="";		
		stm = con.prepareStatement("DESCRIBE "+tableName);
		rst=stm.executeQuery();
		int i=0;
		while(rst.next()){
			field = rst.getString("FIELD").toLowerCase();
			type = GenerateDAO.selectType(rst);
			arrayFields[i][FIELD]=field;
			arrayFields[i][TYPE]=type;
			i++;
		}
		return arrayFields;
	}
	
	public void generate() throws Exception{		
		File f=new File(path+"database/"+className+"DCS.java");
		f.setWritable(true);
		FileOutputStream fos=new FileOutputStream(f);
		PrintStream ps=new PrintStream(fos);
		ps.println(createClass());
		ps.close();
	}
}
