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

public class GenerateClass {

	private String[][] arrayFields;
	private String className;
	private String tableName;
	private Connection con;
	FileOutputStream fos;
	private int numFields;
	private String pack;
	private String path;

	private static final int TYPE=1;
	private static final int FIELD=0;

	public GenerateClass(String className, String tableName, String path, String pack) throws SQLException, PersistenceException {
		super();
		this.className = className;
		this.tableName = tableName;
		this.path = path;
		this.pack = pack;

		con = DatabaseConnectionFactory.getConnection();
		PreparedStatement stm=null;
		ResultSet rst=null;
		stm = con.prepareStatement("DESCRIBE "+tableName);
		rst=stm.executeQuery();
		while(rst.next()){
			numFields++;
		}
		//String[rows][colums]
		this.arrayFields = new String[numFields][2];
	}

	public String genFields() throws SQLException{
		PreparedStatement stm=null;
		ResultSet rst=null;
		String fields="\n";
		String field="";
		String type="";		
		stm = con.prepareStatement("DESCRIBE "+tableName);
		rst=stm.executeQuery();
		int i=0;
		while(rst.next()){
			field = rst.getString("FIELD").toLowerCase();
			type = GenerateDAO.selectType(rst);
			fields= fields+ "\nprivate "+type+" "+field+";";
			arrayFields[i][FIELD]=field;
			arrayFields[i][TYPE]=type;
			i++;
		}		
		return fields;
	}

	public String genConstructor() throws SQLException{
		//Costruttore 0 argomenti
		String text="\n\npublic "+className+"() {\nsuper();\n}";
		//Costruttore a più argomenti
		text= text+"\n\npublic "+className+"(";
		String param="";
		String initial="";
		for(int i=0;i<arrayFields.length;i++){
			param= param+arrayFields[i][TYPE]+" "+arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				param=param+", ";
		}
		text= text+param+") {\nsuper();";
		for(int i=0;i<arrayFields.length;i++){
			initial= initial+"\nthis."+arrayFields[i][FIELD]+"="+arrayFields[i][FIELD]+";";
		}
		text=text+initial;
		return text+"\n}";
	}

	public String genToString(){
		String text= "\n\npublic String toString(){" +
		"\nString ret=\"*****\" +this.getClass()+\"*****\"+";
		for(int i=0;i<arrayFields.length;i++){
			text+="\n\"\\n"+arrayFields[i][FIELD]+": \"+this."+arrayFields[i][FIELD];
			if(i<arrayFields.length-1)
				text=text+"+";
		}
		return text+";\nreturn ret+\"******************\";\n}";
	}

	public String genGetSet() throws SQLException{
		PreparedStatement stm=null;
		ResultSet rst=null;
		String text="\n";
		String prefix = "get";
		String field="";
		String type="";		
		stm = con.prepareStatement("DESCRIBE "+tableName);
		rst=stm.executeQuery();
		while(rst.next()){
			field = rst.getString("FIELD").toLowerCase();
			type = GenerateDAO.selectType(rst);
			if(type.equalsIgnoreCase("boolean"))
				prefix = "is";
			text+="\n\npublic "+type+" "+prefix+GenerateDAO.toCamel(field)+"() {\nreturn "+field+";\n}";
			text+="\n\npublic void set"+GenerateDAO.toCamel(field)+"("+type+" "+field+") {\nthis."+field+"="+field+";\n}";
		}		
		return text;
	}


	public String createClass() throws FileNotFoundException, SQLException{
		String text = "package "+pack+";\n\n";
		text+= "public class "+className+" {";
		text = text+genFields()+genConstructor()+genGetSet()+genToString();
		return text+"\n}";
	}

	public void generate() throws Exception{
		File f=new File(path+className+".java");
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
