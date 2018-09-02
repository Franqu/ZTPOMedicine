package com.medicine;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public interface ITable {
	JPanel create(Patient patient);
	DefaultTableModel buildTableModel (List<Object> list, Patient patient);
	void loadDataFromDB(Patient patient);
}
