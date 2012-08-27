package mypackage;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.table.DefaultTableModel;

public class ResultSetTableModel extends DefaultTableModel implements RowSetListener {
	private static final long serialVersionUID = 1L;
	// RowSet for the result set
	private ResultSet resultSet;

	/** Return the rowset */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/** Set a new rowset */
	public void setResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			this.resultSet = resultSet;
			fireTableStructureChanged();
		}
	}

	/** Return the number of rows in the row set */
	public int getRowCount() {
		try {
			if (resultSet == null)
				return 0;
			resultSet.last();
			return resultSet.getRow(); // Get the current row number
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	/** Return the number of columns in the row set */
	public int getColumnCount() {
		try {
			if (resultSet != null) {
				return resultSet.getMetaData().getColumnCount();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	/** Return value at the specified row and column */
	public Object getValueAt(int row, int column) {
		try {
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}

		return null;
	}

	/** Return the column name at a specified column */
	public String getColumnName(int column) {
		try {
			return resultSet.getMetaData().getColumnLabel(column + 1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return "";
	}

	/** Implement rowSetChanged */
	public void rowSetChanged(RowSetEvent e) {
		System.out.println("RowSet changed");
		fireTableStructureChanged();
	}
	
	

	/** Implement rowChanged */
	public void rowChanged(RowSetEvent e) {
		System.out.println("Row changed");
		fireTableDataChanged();
	}

	/** Implement cursorMoved */
	public void cursorMoved(RowSetEvent e) {
		System.out.println("Cursor moved");
	}
}
