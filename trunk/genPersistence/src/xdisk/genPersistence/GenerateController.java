package xdisk.genPersistence;

import it.multiarte.exception.PersistenceException;
import it.multiarte.xstone.persistence.database.DatabaseConnectionFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GenerateController {

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

	public GenerateController(String className, String tableName, String path, String pack) throws SQLException, PersistenceException {
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
				System.out.println("----Trovata chiave:"+key+" tipo:"+typeKey);
			}
			numFields++;
		}
		//String[rows][colums]
		this.arrayFields = new String[numFields][2];
	}

	public String genConstructor() throws SQLException{
		//Costruttore 0 argomenti privato
		String text="\nprivate "+className+"Controller() {\nsuper();\n}";
		return text;
	}

	public String createClass() throws FileNotFoundException, SQLException{
		String text = "package "+pack+";\n\n";
		text+="\nimport java.util.Collection;\nimport xdisk.exception.PersistenceException;" +
		"\nimport xdisk.persistence."+className+";";
		text+= "\n\npublic class "+className+"Controller {\n";
		text+= genConstructor()+genCrud();
		return text+"\n}";
	}

	public String genCrud(){
		String text="\n\npublic static void load("+GenerateDAO.toCamel(className)+" object) throws PersistenceException{"
			+GenerateDAO.toCamel(className)+"DAO.load(object);}";
		text+="\n\npublic static void insert("+GenerateDAO.toCamel(className)+" object) throws PersistenceException{"
			+GenerateDAO.toCamel(className)+"DAO.insert(object);}";
		text+="\n\npublic static void delete("+GenerateDAO.toCamel(className)+" object) throws PersistenceException{"
			+GenerateDAO.toCamel(className)+"DAO.delete(object);}";
		text+="\n\npublic static void update("+GenerateDAO.toCamel(className)+" object) throws PersistenceException{"
			+GenerateDAO.toCamel(className)+"DAO.update(object);}";
		text+="\n\npublic static int removeAll() throws PersistenceException{\nreturn "+GenerateDAO.toCamel(className)+"DCS.removeAll();\n}";
		text+="\n\npublic static Collection<"+GenerateDAO.toCamel(className)+"> getAll() throws PersistenceException{\nreturn "+GenerateDAO.toCamel(className)+"DCS.getAll();\n}\n";
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
			text+="\nstm.set"+GenerateDAO.toCamel(arrayFields[i][TYPE])+"("+(i+1)+",object."+prefix+GenerateDAO.toCamel(arrayFields[i][FIELD])+"());";
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
		text+="\nstm.set"+GenerateDAO.toCamel(typeKey)+"(1,object.get"+GenerateDAO.toCamel(key)+"());\nrst=stm.executeQuery();\nrst.next();";
		for(int i=0;i<arrayFields.length;i++){
			text+="\nobject.set"+GenerateDAO.toCamel(arrayFields[i][FIELD])+"(rst.get"+GenerateDAO.toCamel(arrayFields[i][TYPE])+"(\""+arrayFields[i][FIELD]+"\"));";
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
			type = GenerateDAO.selectType(rst);
			arrayFields[i][FIELD]=field;
			arrayFields[i][TYPE]=type;
			i++;
		}
		return arrayFields;
	}

	public void generate() throws Exception{		
		File f=new File(path+"database/"+className+"Controller.java");
		if(f.exists()){
			f.setWritable(true);
			FileOutputStream fos=new FileOutputStream(f);
			PrintStream ps=new PrintStream(fos);		
			ps.println(createClass());
			ps.close();
		}
		else{
			System.out.println("Il file "+f+"già esiste non lo sovrascrivo!!!");
		}
	}
}
