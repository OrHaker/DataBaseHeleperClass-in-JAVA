package main;
import java.sql.ResultSet;
import database.SqlDbConnection;

public class MainApp {

	//fields
	public static final String DRIVER_STRING ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//represent the DB file location - include opening string of specific DB type 
	public static final String URL_STRING ="place here";

	public static void main(String[] args) {
		SqlDbConnection dbConnector=null;
		try 
		{
			dbConnector= new SqlDbConnection(DRIVER_STRING,URL_STRING,"DESKTOP-CEQ4I7M\\OR","");
			
			//simple SELECT SENTENCE
			ResultSet rs=dbConnector.simpleSelectQuery("Select * from [Students]");
			dbConnector.printRS(rs);
			System.out.println("\n================================\n");

			//simple INSERT SENTENCE
			//dbConnector.simpleInsertUpdateDeleteQuery("insert into MyTable(ID,NAME,LASTNAME,SALARY) values('2','FIRST_NAME','LAST_NAME',1510)");
			rs=dbConnector.simpleSelectQuery("SELECT * FROM MyTable");
			dbConnector.printRS(rs);
			System.out.println("\n================================\n");

			//simple UPDATE  SENTENCE
			dbConnector.simpleInsertUpdateDeleteQuery("insert into MyTable(ID,NAME,LASTNAME,SALARY) values('4','ADI','ADI',20000)");
			dbConnector.simpleInsertUpdateDeleteQuery("UPDATE MyTable SET NAME='Berry' WHERE ID=3");
			rs=dbConnector.simpleSelectQuery("SELECT * FROM MyTable");
			dbConnector.printRS(rs);
			System.out.println("\n================================\n");
			
			//simple DELETE SENTENCE
			dbConnector.simpleInsertUpdateDeleteQuery("DELETE FROM MyTable WHERE ID=3;");
			rs=dbConnector.simpleSelectQuery("SELECT * FROM MyTable");
			dbConnector.printRS(rs);
			
			//PROC
			System.out.println("\n================================\n");
			System.out.println("PROC VALUES\n");
			dbConnector.printRS(dbConnector.storedProcExec("MYPROC"));

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		finally {
			//CLOSE DB Connection
			dbConnector.closeDB();
		}
	}

}
