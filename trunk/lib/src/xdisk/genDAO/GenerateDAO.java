package xdisk.genDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import xdisk.persistence.database.DatabaseConnectionFactory;

public class GenerateDAO {

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

	public GenerateDAO(String className, String tableName, String path, String pack) throws SQLException {
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
				typeKey=selectType(rst);
				System.out.println("Trovata chiave"+key+" "+typeKey);
			}
			numFields++;
		}
		//String[rows][colums]
		this.arrayFields = new String[numFields][2];
	}

	public String genConstructor() throws SQLException{
		//Costruttore 0 argomenti privato
		String text="\n\nprivate "+className+"DAO() {\nsuper();\n}";
		return text;
	}

	public String createClass() throws FileNotFoundException, SQLException{
		String text = "package "+pack+";\n\n";
		text+="\nimport java.sql.*;\nimport xdisk.exception.PersistenceException;" +
		"\nimport xdisk.persistence."+className+";";
		text+= "\n\npublic class "+className+"DAO {\n";
		text+= genQuery()+genConstructor()+genLoad()+genInsert()+genDelete()+genUpdate();
		return text+"\n}";
	}

	public String genQuery() throws SQLException{
		getArrayFields();		
		//INSERT
		String text = "\nprivate static final String INSERT_SQL = \"INSERT INTO "+tableName;
		text+="(";
		for(int i=0;i<arrayFields.length;i++){
			text+=arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				text=text+",";
		}
		text+=") VALUES (";
		for(int i=0;i<arrayFields.length;i++){
			text+="?";
			if(i<arrayFields.length-1)
				text=text+",";
		}
		text+=")\";";
		//SELECT
		text += "\nprivate static final String SELECT_SQL = \"SELECT ";
		for(int i=0;i<arrayFields.length;i++){
			text+=arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				text=text+",";
		}
		text+=" FROM "+tableName+" WHERE "+key+"=?\";";
		//UPDATE
		text+= "\nprivate static final String UPDATE_SQL = \"UPDATE "+tableName+" ";
		for(int i=0;i<arrayFields.length;i++){
			text+=arrayFields[i][FIELD]+"=?";
			if(i<arrayFields.length-1)
				text=text+", ";
		}
		text+=" WHERE "+key+"=?\";";
		//DELETE
		text+= "\nprivate static final String DELETE_SQL = \"DELETE FROM "+tableName+" WHERE "+key+"=?\";";
		return text;
	}

	public String genInsert(){
		String prefix="get";
		String text="\n\nstatic void insert ("+className+" object) throws PersistenceException { " +
		"\nConnection con=null;\nPreparedStatement stm=null;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(INSERT_SQL);";

		for(int i=0;i<arrayFields.length;i++){
			if(arrayFields[i][TYPE].equalsIgnoreCase("boolean"))
				prefix = "is";
			text+="\nstm.set"+toCamel(arrayFields[i][TYPE])+"("+(i+1)+",object."+prefix+toCamel(arrayFields[i][FIELD])+"());";
		}

		text+="\nstm.executeUpdate();" +
		"\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(object.toString(),e);\n}\nfinally {" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\n}";
		return text;
	}

	public String genUpdate(){
		String prefix="get";
		String text="\n\nstatic void update ("+className+" object) throws PersistenceException { " +
		"\nConnection con=null;\nPreparedStatement stm=null;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(UPDATE_SQL);";

		for(int i=0;i<arrayFields.length;i++){
			if(arrayFields[i][TYPE].equalsIgnoreCase("boolean"))
				prefix = "is";
			text+="\nstm.set"+toCamel(arrayFields[i][TYPE])+"("+(i+1)+",object."+prefix+toCamel(arrayFields[i][FIELD])+"());";
		}

		text+="\nstm.executeUpdate();" +
		"\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(object.toString(),e);\n}\nfinally {" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\n}";
		return text;
	}
	
	public String genLoad(){
		String text="\n\nstatic void load ("+className+" object) throws PersistenceException { " +
		"\nConnection con=null;\nPreparedStatement stm=null;\nResultSet rst=null;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(SELECT_SQL);";
		text+="\nstm.set"+toCamel(typeKey)+"(1,object.get"+toCamel(key)+"());\nrst=stm.executeQuery();\nrst.next();";
		for(int i=0;i<arrayFields.length;i++){
			text+="\nobject.set"+toCamel(arrayFields[i][FIELD])+"(rst.get"+toCamel(arrayFields[i][TYPE])+"(\""+arrayFields[i][FIELD]+"\"));";
		}
		text+="\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(object.toString(),e);\n}\nfinally {" +
		"\nif (rst != null) try {rst.close();} catch (Exception e) {}" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\n}";
		return text;
	}
	
	public String genDelete(){
		String text="\n\nstatic void delete ("+className+" object) throws PersistenceException { " +
		"\nConnection con=null;\nPreparedStatement stm=null;" +
		"\ncon = DatabaseConnectionFactory.getConnection(); \ntry {\nstm = con.prepareStatement(DELETE_SQL);";
		text+="\nstm.execute();" +
		"\n} catch (SQLException e) { " +
		"\nthrow new PersistenceException(object.toString(),e);\n}\nfinally {" +
		"\nif (stm != null) try {stm.close();} catch (Exception e) {}" +
		"\nif (con != null) try {con.close();} catch (Exception e) {}\n}\n}";
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
			type = selectType(rst);
			arrayFields[i][FIELD]=field;
			arrayFields[i][TYPE]=type;
			i++;
		}
		return arrayFields;
	}

	public static String selectType(ResultSet rst) throws SQLException {
		String type="";
		System.out.print("field:"+rst.getString("FIELD")+" type:"+rst.getString("TYPE")+"->");
		if(rst.getString("TYPE").contains("varchar")){
			type="String";
			System.out.println("stringa");
		}
		else if(rst.getString("TYPE").contains("tinyint")){
			type="boolean";
			System.out.println("boolean");
		}
		else if(rst.getString("TYPE").contains("int")){
			type="int";
			System.out.println("intero");
		}
		else{
			throw new RuntimeException("Non ho il tipo corrispondente");
		}
		return type;
	}

	public static String toCamel(String word){
		String ret= String.valueOf(word.charAt(0));
		ret = ret.toUpperCase();
		return ret+word.substring(1);
	}
	
	public void generate() throws Exception{		
		File f=new File(path+"database/"+className+"DAO.java");
		f.setWritable(true);
		FileOutputStream fos=new FileOutputStream(f);
		PrintStream ps=new PrintStream(fos);
		ps.println(createClass());
		ps.close();
	}
}
