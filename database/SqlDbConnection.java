package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDbConnection {
	//fields
	//DB connection instance
	private Connection conn=null;
	//result set instance
	private ResultSet rs;
	//Statement - represent the SQL Query
	private Statement stat;

	//Constructor
	public SqlDbConnection(String driver,String url,String userName,String userPassword) {
		try 
		{
			//for name upload the selected driver
			Class.forName(driver);
			//start DB connection
			this.conn = DriverManager.getConnection(url,userName,userPassword);
			//create statement  instance
			this.stat = conn.createStatement();

		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found: " + e.getMessage());
			System.exit(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	//methods
	public ResultSet simpleSelectQuery (String selectSentence)  {
		ResultSet rsToReturn=null;
		try 
		{
			rsToReturn= this.stat.executeQuery(selectSentence);
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}

		return rsToReturn;
	}

	//handle INSERT ,UPDATE and DELETE Query  
	public int simpleInsertUpdateDeleteQuery (String selectSentence) {
		int rowsAffected=0;
		try 
		{
			rowsAffected=this.stat.executeUpdate(selectSentence);
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
		return rowsAffected;
	}

	public ResultSet storedProcExec(String procName)
	{
		CallableStatement stmt=null;
		try 
		{
			stmt = conn.prepareCall("EXEC "+procName);
			return stmt.executeQuery();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	//gets an Result set and print the values by rows
	public void printRS(ResultSet rs) {
		try 
		{
			var rsmd=rs.getMetaData();
			var size=rsmd.getColumnCount();
			for (int i = 1; i <= size; i++) {
				var columnName=rsmd.getColumnName(i);
				System.out.print(columnName+"  ");
			}
			System.out.println();
			while(rs.next())
			{
				for (int i = 1; i <= size; i++) {
					var columnName=rsmd.getColumnName(i);
					System.out.print(rs.getString(columnName)+"\t");
				}
				System.out.println();
			}
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
	}

	//close DB connection
	public void closeDB() {
		if(this.conn!=null)
		{
			try 
			{
				this.conn.close();
			} 
			catch (SQLException e) 
			{
				System.out.println(e.getMessage());
			}
		}
	}

	//getters and setters can endanger our DB for dangers from out side!!!
	//if you don't must use getters and setters don't create them!!!

}
