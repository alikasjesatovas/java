package mypackage;

import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyTableModel() {
	}

	/** Construct a table model with specified data and columnNames */
	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	public MyTableModel(Object[] data, int rows) {
		super(data, rows);
	}

	public MyTableModel(Vector data, Vector columnName) {
		super(data, columnName);
	}

	/** Override this method to return a class for the column */
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass();
	}

	/** Override this method to return true if cell is editable */
	public boolean isCellEditable(int row, int column) {
		Class columnClass = getColumnClass(column);
		return columnClass != ImageIcon.class && columnClass != Date.class;
	}
}

