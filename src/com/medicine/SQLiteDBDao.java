package com.medicine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface SQLiteDBDao {
		void createNewDatabase(String fileName);
	    void createNewTable(String fileName);
	    Connection getConnection(String fileName);
	    List<Object> selectAll(String fileName);
	    Object select(String fileName, int id);
	    void closeConnection(String fileName);
}