package com.medicine;

import java.sql.Connection;
import java.util.List;

class ContextDB{
    
	private SQLiteDBDao db;
	final private String dbName = "medicineDB";
    public SQLiteDBDao getDb() {
        return db;
    }

    public void setDb(SQLiteDBDao db) {
        this.db = db;
    }

    public void createNewTable() {
        db.createNewTable(dbName);
    }

    public Connection getConnection(String fileName) {
        return db.getConnection(fileName);
    }
    public List<Object> selectAll(String fileName)
    {
    	return db.selectAll(fileName);
    }
    public void closeConnection(String fileName)
    {
    	db.closeConnection(fileName);
    }
    Object select(String fileName, int id)
    {
    	return db.select(fileName,id);
    }
}