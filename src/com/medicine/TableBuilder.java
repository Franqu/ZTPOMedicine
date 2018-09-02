package com.medicine;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class TableBuilder implements ITable{

	final static String dbName = "MedicineDB";
	static JTable medicineTable= new JTable();
	ContextDB db = new ContextDB();
	
	
	public JPanel create(Patient patient) {
		loadDataFromDB(patient);
		JScrollPane tableContainer = new JScrollPane(medicineTable);
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(tableContainer,BorderLayout.CENTER);
		
		return tablePanel;
	}


	public DefaultTableModel buildTableModel(List<Object> list, Patient patient) {
		 Vector<String> columnNames = new Vector<>();
		    columnNames.add("ID");
		    columnNames.add("Name");
		    columnNames.add("Second Name");
		    columnNames.add("Price");
		   
		
		    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		    for ( Object medicine : list) {
			   Vector<Object> vector = new Vector<Object>();
		        for (int columnIndex = 0 ; columnIndex < columnNames.size(); columnIndex++) {
		        		
		        	  vector.add(((Medicine) medicine).getId());
		        	  vector.add(((Medicine) medicine).getName());
		        	  vector.add(((Medicine) medicine).getSecondName());
		        	  if(patient.getAge() <= 75){
		        	  vector.add(((Medicine) medicine).getPrice());
		        	  }
		        	  else{
		        		  vector.add(((Medicine) medicine).getPriceSenior75());
		        	  }

		        }
		        data.add(vector);
		    }
		    return new DefaultTableModel(data, columnNames){
		    	
				private static final long serialVersionUID = -6622905133391297170L;

				@Override
		    	    public boolean isCellEditable(int row, int column) {
		    	        return false;
		    	    }
		    };
	}
	
		public void loadDataFromDB(Patient patient) {
			db.setDb(new JDBCMedicineImpl());
			List<Object> medicineList = new ArrayList<>();
			medicineList.addAll(db.selectAll(dbName));
			medicineTable.setModel(buildTableModel(medicineList, patient));
		    }
		    
		    
		   
		    

	
	

}
