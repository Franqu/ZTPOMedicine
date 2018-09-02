package com.medicine;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow {
	final static String fileName = "MedicineDB";
	static JFrame frame;
	ContextDB db = new ContextDB();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					MainWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		TableBuilder tableImpl = new TableBuilder();
		db.setDb(new JDBCMedicineImpl());
		db.createNewTable();
		db.setDb(new JDBCPatientImpl());
		db.createNewTable();
		
		JPanel patientTableContainer = new JPanel();
		JPanel cbPanel = new JPanel();
		JComboBox<Patient> cbPatient = new JComboBox<Patient>();
		Patient patient = new Patient();
		List<Object> patientList = new ArrayList<>();
		patientList.addAll(db.selectAll(fileName));
		DefaultComboBoxModel model = new DefaultComboBoxModel( patientList.toArray() );
		
		cbPatient.setModel(model);
		patient = (Patient) db.select(fileName, 1);
		patientTableContainer = tableImpl.create(patient);
		cbPatient.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Patient patient = new Patient();
				patient = (Patient) e.getItem();
				tableImpl.loadDataFromDB(patient);
				
			}
		});;
		cbPanel.add(cbPatient);
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(patientTableContainer, BorderLayout.NORTH);
		frame.getContentPane().add(cbPanel, BorderLayout.SOUTH);
		

}
}
