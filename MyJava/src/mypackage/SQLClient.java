/*This GUI program presents a universal SQL client that enables you to connect to any relational database and
execute SQL commands interactively. The client can connect to any
JDBC data source and can submit SQL SELECT commands and non-SELECT commands for
execution. The execution result is displayed in table for the SELECT queries, and the execution status
is displayed for the non-SELECT commands.
The user selects or enters the JDBC driver, database URL, user name, and password, and clicks
the Connect to Database button to connect to the specified database.
This program can work as stand alone or as JApplet.
Important: this program must be used with appropriate JDBC drivers.*/


package mypackage;
import java.awt.*;
import java.awt.event.*;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class SQLClient extends JApplet {
	private static final long serialVersionUID = 1L;

	// Connection to the database
	private Connection connection;

	// Statement to execute SQL commands
	private Statement statement;

	// Text area to enter SQL commands
	private JTextArea jtasqlCommand = new JTextArea();

	// Text area to display results from SQL commands
	private JTextArea jtaSQLResult = new JTextArea();

	// JDBC info for a database connection
	JTextField jtfUsername = new JTextField();
	JPasswordField jpfPassword = new JPasswordField();
	JComboBox<String> jcboURL = new JComboBox<String>(new String[] { "jdbc:mysql://localhost/javabook", "jdbc:odbc:exampleMDBDataSource",
			"jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl" });
	JComboBox<String> jcboDriver = new JComboBox<String>(new String[] { "com.mysql.jdbc.Driver", "sun.jdbc.odbc.JdbcOdbcDriver", "oracle.jdbc.driver.OracleDriver" });

	JButton jbtExecuteSQL = new JButton("Execute SQL Command");
	JButton jbtClearSQLCommand = new JButton("Clear");
	JButton jbtConnectDB1 = new JButton("Connect to Database");
	JButton jbtClearSQLResult = new JButton("Clear Result");

	// Create titled borders
	Border titledBorder1 = new TitledBorder("Enter an SQL Command");
	Border titledBorder2 = new TitledBorder("SQL Execution Result");
	Border titledBorder3 = new TitledBorder("Enter Database Information");

	CardLayout cardLayout = new CardLayout();
	JPanel jpCards = new JPanel(cardLayout);
	ResultSetTableModel model = new ResultSetTableModel();

	JLabel jlblConnectionStatus = new JLabel("No connection now");

	/** Initialize the applet */
	public void init() {
		JScrollPane jScrollPane1 = new JScrollPane(jtasqlCommand);
		jScrollPane1.setBorder(titledBorder1);
		JScrollPane jScrollPane2 = new JScrollPane(jtaSQLResult);
		JTable table = new JTable(model);
		jpCards.setBorder(titledBorder2);
		jpCards.add(jScrollPane2, "textField");
		jpCards.add(new JScrollPane(table), "table");
		cardLayout.show(jpCards, "textField");

		JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jPanel1.add(jbtClearSQLCommand);
		jPanel1.add(jbtExecuteSQL);

		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(jScrollPane1, BorderLayout.CENTER);
		jPanel2.add(jPanel1, BorderLayout.SOUTH);
		jPanel2.setPreferredSize(new Dimension(100, 100));

		JPanel jPanel3 = new JPanel();
		jPanel3.setLayout(new BorderLayout());
		jPanel3.add(jlblConnectionStatus, BorderLayout.CENTER);
		jPanel3.add(jbtConnectDB1, BorderLayout.EAST);

		JPanel jPanel4 = new JPanel();
		jPanel4.setLayout(new GridLayout(4, 1, 10, 5));
		jPanel4.add(jcboDriver);
		jPanel4.add(jcboURL);
		jPanel4.add(jtfUsername);
		jPanel4.add(jpfPassword);

		JPanel jPanel5 = new JPanel();
		jPanel5.setLayout(new GridLayout(4, 1));
		jPanel5.add(new JLabel("JDBC Driver"));
		jPanel5.add(new JLabel("Database URL"));
		jPanel5.add(new JLabel("Username"));
		jPanel5.add(new JLabel("Password"));

		JPanel jPanel6 = new JPanel();
		jPanel6.setLayout(new BorderLayout());
		jPanel6.setBorder(titledBorder3);
		jPanel6.add(jPanel4, BorderLayout.CENTER);
		jPanel6.add(jPanel5, BorderLayout.WEST);

		JPanel jPanel7 = new JPanel();
		jPanel7.setLayout(new BorderLayout());
		jPanel7.add(jPanel3, BorderLayout.SOUTH);
		jPanel7.add(jPanel6, BorderLayout.CENTER);

		JPanel jPanel8 = new JPanel();
		jPanel8.setLayout(new BorderLayout());
		jPanel8.add(jPanel2, BorderLayout.CENTER);
		jPanel8.add(jPanel7, BorderLayout.WEST);

		JPanel jPanel9 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanel9.add(jbtClearSQLResult);

		jcboURL.setEditable(true);
		jcboDriver.setEditable(true);

		add(jPanel8, BorderLayout.NORTH);
		add(jpCards, BorderLayout.CENTER);
		add(jPanel9, BorderLayout.SOUTH);

		jbtExecuteSQL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeSQL();
			}
		});
		jbtConnectDB1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToDB();
			}
		});
		jbtClearSQLCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtasqlCommand.setText(null);
			}
		});
		jbtClearSQLResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtaSQLResult.setText(null);
				cardLayout.show(jpCards, "textField");
			}
		});
	}

	/** Connect to DB */
	private void connectToDB() {
		// Get database information from the user input
		String driver = (String) jcboDriver.getSelectedItem();
		String url = (String) jcboURL.getSelectedItem();
		String username = jtfUsername.getText().trim();
		String password = new String(jpfPassword.getPassword());

		// Connection to the database
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
			jlblConnectionStatus.setText("Connected to " + url);
		} catch (java.lang.Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Execute SQL commands */
	private void executeSQL() {
		if (connection == null) {
			jtaSQLResult.setText("Please connect to a database first");
			return;
		} else {
			String sqlCommands = jtasqlCommand.getText().trim();
			String[] commands = sqlCommands.replace('\n', ' ').split(";");

			for (String aCommand : commands) {
				if (aCommand.trim().toUpperCase().startsWith("SELECT")) {
					processSQLSelect(aCommand);
				} else {
					processSQLNonSelect(aCommand);
				}
			}
		}
	}

	/** Execute SQL SELECT commands */
	private void processSQLSelect(String sqlCommand) {
		try {
			// Get a new statement for the current connection
			statement = connection.createStatement();

			// Execute a SELECT SQL command
			ResultSet resultSet = statement.executeQuery(sqlCommand);

			model.setResultSet(resultSet);
			cardLayout.show(jpCards, "table");
		} catch (SQLException ex) {
			jtaSQLResult.setText(ex.toString());
		}
	}

	/** Execute SQL DDL, and modification commands */
	private void processSQLNonSelect(String sqlCommand) {
		try {
			// Get a new statement for the current connection
			statement = connection.createStatement();

			// Execute a non-SELECT SQL command
			int r = statement.executeUpdate(sqlCommand);

			jtaSQLResult.setText("SQL command executed. Row effected: " + r);
			cardLayout.show(jpCards, "textField");
		} catch (SQLException ex) {
			jtaSQLResult.setText(ex.toString());
		}
	}

	/** Main method */
	public static void main(String[] args) {
		SQLClient applet = new SQLClient();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Interactive SQL Client");
		frame.getContentPane().add(applet, BorderLayout.CENTER);
		applet.init();
		applet.start();
		frame.setSize(700, 320);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
	}
}


class ResultSetTableModel extends DefaultTableModel implements RowSetListener {
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
