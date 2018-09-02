package com.medicine;

import java.lang.invoke.SwitchPoint;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCMedicineImpl implements SQLiteDBDao {
	
	
	final static String fileName = "MedicineDB";
	@Override
	 public void createNewDatabase(String fileName) {
		 
	        String url = "jdbc:sqlite:" + fileName;
	 
	        try (Connection conn = DriverManager.getConnection(url)) {
	            if (conn != null) {
	                DatabaseMetaData meta = conn.getMetaData();
	                System.out.println("The driver name is " + meta.getDriverName());
	                System.out.println("A new database has been created.");
	            }
	 
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	@Override
	 public void createNewTable(String fileName) {
	        // SQLite connection string
	        String url = "jdbc:sqlite:" + fileName;
	        
	        // SQL statement for creating a new table
	        String sql = "CREATE TABLE IF NOT EXISTS t_medicine (\n"
	                + "	id integer PRIMARY KEY,\n"
	                + "	name text,\n"
	                + "	second_name text,\n"
	                + "	price real,\n"
	                + "	price_senior75 real\n"
	                + ");";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            // create a new table
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        closeConnection(fileName);
	 }
	 
	 
	@Override
	  public Connection getConnection(String fileName){
		      	
		      	Connection conn = null;
		          try {
		            
		              String url = "jdbc:sqlite:" + fileName;
		             
		              conn = DriverManager.getConnection(url);
		              
		              System.out.println("Connection to SQLite has been established.");
		              
		          } catch (SQLException e) {
		              System.out.println(e.getMessage());
		          } 
		      	return conn;
		      }
	@Override
	public List<Object> selectAll(String fileName) {
		
		List<Object> medicineList = new ArrayList<Object>();
         try {
        	 
        	 	Connection connection = getConnection(fileName);
                Statement statement = connection.createStatement();
               
                ResultSet resultSet = statement.executeQuery("SELECT * FROM t_medicine;");
                 
               
                while(resultSet.next()){
                	Medicine medicine = new Medicine();
                    medicine.setId(Integer.parseInt(resultSet.getString("id")));
                    medicine.setName(resultSet.getString("name"));
                    medicine.setSecondName(resultSet.getString("second_name"));
                    medicine.setPrice(resultSet.getDouble("price"));
                    medicine.setPriceSenior75(resultSet.getDouble("price_senior75"));
                    medicineList.add(medicine);
                }
                resultSet.close();
                statement.close();
                closeConnection(fileName);
                 
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
		return medicineList;
		
    }
		
	
	@Override
	public void closeConnection(String fileName){
	    try {
	    	Connection connection = getConnection(fileName);
	          if (connection != null) {
	              connection.close();
	          }
	        }
	    
	    catch (Exception e) { 
	        	 e.printStackTrace();
	        }
	    
	}


	

	@Override
	public Object select(String fileName,int id) {
	 	Medicine medicine = new Medicine();
        try {
       	 	Connection connection = getConnection(fileName);
               PreparedStatement stmt=connection.
       				prepareStatement("SELECT * FROM t_medicine where id = ?");
               stmt.setInt(1, id);
               ResultSet resultSet = stmt.executeQuery();
                   medicine.setId(Integer.parseInt(resultSet.getString("id")));
                   medicine.setName(resultSet.getString("name"));
                   medicine.setSecondName(resultSet.getString("second_name"));
                   medicine.setPrice(resultSet.getDouble("price"));
                   medicine.setPriceSenior75(resultSet.getDouble("price_senior75"));
                 
               resultSet.close();
               stmt.close();
               closeConnection(fileName);
                
           } catch (SQLException e) {
               e.printStackTrace();
               
           }
		return medicine;
	}
		     
	 
}
