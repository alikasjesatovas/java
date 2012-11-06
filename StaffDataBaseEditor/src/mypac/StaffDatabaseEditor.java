/*Java applet that views, inserts,
and updates staff information stored in a database.
The View button displays a record with a specified ID*/

package mypac;

import java.sql.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StaffDatabaseEditor extends JApplet {
	private static final long serialVersionUID = 1L;
	JTextField jtfId = new JTextField("100000002", 15);
	JTextField jtfLastName = new JTextField("Jesatovas", 15);
	JTextField jtfFirstName = new JTextField("Alikas", 15);
	JTextField jtfMi = new JTextField("M", 2);
	JTextField jtfAddress = new JTextField("44 Cheviot Close", 20);
	JTextField jtfCity = new JTextField("Enfield", 20);
	JTextField jtfTelephone = new JTextField("0123456789", 15);
	JTextField jtfState = new JTextField("UK", 3);
	JLabel jlblStatus = new JLabel("Connecting to database.....");
	JButton jbtView = new JButton("View");
	JButton jbtInsert = new JButton("Insert");
	JButton jbtUpdate = new JButton("Update");
	JButton jbtClear = new JButton("Clear");

	Statement statement;

	public StaffDatabaseEditor() {
		jtfId.setOpaque(true);
		jtfId.setBackground(new Color(170, 170, 170));
		Font f = jtfId.getFont();
		jtfId.setFont(new Font(f.getName(), Font.BOLD, f.getSize()));

		JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp1.add(new JLabel(" ID "));
		jp1.add(jtfId);

		JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp2.add(new JLabel("Last Name"));
		jp2.add(jtfLastName);
		jp2.add(new JLabel(" First Name"));
		jp2.add(jtfFirstName);
		jp2.add(new JLabel(" mi"));
		jp2.add(jtfMi);

		JPanel jp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp3.add(new JLabel("Address "));
		jp3.add(jtfAddress);

		JPanel jp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp4.add(new JLabel("City "));
		jp4.add(jtfCity);
		jp4.add(new JLabel(" State "));
		jp4.add(jtfState);

		JPanel jp5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp5.add(new JLabel("Telephone "));
		jp5.add(jtfTelephone);

		JPanel jpCenter = new JPanel(new BorderLayout());
		Box boxCenterCenter = Box.createVerticalBox();
		boxCenterCenter.setBorder(new TitledBorder("Staff Information"));
		boxCenterCenter.add(jp1);
		boxCenterCenter.add(jp2);
		boxCenterCenter.add(jp3);
		boxCenterCenter.add(jp4);
		boxCenterCenter.add(jp5);

		JPanel jpCenterSouth = new JPanel();
		jpCenterSouth.add(jbtView);
		jpCenterSouth.add(jbtInsert);
		jpCenterSouth.add(jbtUpdate);
		jpCenterSouth.add(jbtClear);

		jpCenter.add(boxCenterCenter);
		jpCenter.add(jpCenterSouth, BorderLayout.SOUTH);

		add(jpCenter);
		add(jlblStatus, BorderLayout.SOUTH);

		enableButtons(false);

		connect();

		jbtView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtfId.getText();
				if (!isIdValid(id)) {
					jlblStatus.setText("ID is not valid");
					return;
				}

				try {
					ResultSet res = statement
							.executeQuery("SELECT id, lastName, firstName, mi, address, city, state, telephone FROM staff WHERE id = " + id);
					boolean found = false;
					while (res.next()) {
						found = true;
						jtfId.setText(res.getString(1));
						jtfLastName.setText(res.getString(2));
						jtfFirstName.setText(res.getString(3));
						jtfMi.setText(res.getString(4));
						jtfAddress.setText(res.getString(5));
						jtfCity.setText(res.getString(6));
						jtfState.setText(res.getString(7));
						jtfTelephone.setText(formatTelephone(res.getString(8)));
					}
					if (found)
						jlblStatus.setText("Record found");
					else
						jlblStatus.setText("Record not found");
				} catch (SQLException ex) {
					jlblStatus.setText("SQL Exception");
				}
			}
		});

		jbtInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> columns = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();

				String id = jtfId.getText();
				String lastName = jtfLastName.getText();
				String firstName = jtfFirstName.getText();
				String mi = jtfMi.getText();
				String address = jtfAddress.getText();
				String city = jtfCity.getText();
				String state = jtfState.getText();
				String telephone = jtfTelephone.getText();

				if (!isIdValid(id)) {
					jlblStatus.setText("id is not valid");
					return;
				}
				columns.add("id");
				values.add(id);

				if (lastName.length() != 0) {
					if(!isLastNameValid(lastName)) {
						jlblStatus.setText("last Name is not Valid");
						return;
					}
					columns.add("lastName");
					values.add(lastName);
				}

				if (firstName.length() != 0) {
					if(!isFirstNameValid(firstName)) {
						jlblStatus.setText("first name is not Valid");
						return;
					}
					columns.add("firstName");
					values.add(firstName);
				}

				if (mi.length() != 0) {
					if(!isMiValid(mi)) {
						jlblStatus.setText("Mi is not valid");
						return;
					}
					columns.add("mi");
					values.add(mi);
				}

				if (address.length() != 0) {
					if(!isAddressValid(address)) {
						jlblStatus.setText("address is not valid");
						return;
					}
					columns.add("address");
					values.add(address);
				}

				if (city.length() != 0) {
					if(!isCityValid(city)) {
						jlblStatus.setText("city is not valid");
						return;
					}
					columns.add("city");
					values.add(city);
				}

				if (state.length() != 0) {
					if(!isStateValid(state)) {
						jlblStatus.setText("state is not valid");
						return;
					}
					columns.add("state");
					values.add(state);
				}

				if (telephone.length() != 0) {
					if(!isTelephoneValid(telephone)) {
						jlblStatus.setText("telephone is not valid");
						return;
					}
					if(telephone.length() == 10) {
						columns.add("telephone");
						values.add(telephone);
					}
					else {
						columns.add("telephone");
						values.add(deFormatTelephone(telephone));
					}
				}
				
				String command = "INSERT INTO Staff (";
				for(int i = 0; i < columns.size(); i++) {
					command += columns.get(i);
					if(i + 1 < columns.size())
						command += ", ";
				}
				command += ") VALUES (";
				for(int i = 0; i < values.size(); i++) {
					command += "'" + values.get(i) + "'";
					if(i + 1 < values.size())
						command += ", ";
				}
				command += ");";
				System.out.println(command);
				
				try {
					int i = statement.executeUpdate(command);
					jlblStatus.setText("Insert was successful: rows effected " + i);
				} catch (SQLException ex) {
					jlblStatus.setText("SQL Exeption");
					System.out.print(ex.toString());
				}
			}
		});

		jbtUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> columns = new ArrayList<String>();
				ArrayList<String> values = new ArrayList<String>();

				String id = jtfId.getText();
				String lastName = jtfLastName.getText();
				String firstName = jtfFirstName.getText();
				String mi = jtfMi.getText();
				String address = jtfAddress.getText();
				String city = jtfCity.getText();
				String state = jtfState.getText();
				String telephone = jtfTelephone.getText();

				if (!isIdValid(id)) {
					jlblStatus.setText("id is not valid");
					return;
				}

				if (lastName.length() != 0) {
					if(!isLastNameValid(lastName)) {
						jlblStatus.setText("last Name is not Valid");
						return;
					}
					columns.add("lastName");
					values.add(lastName);
				}

				if (firstName.length() != 0) {
					if(!isFirstNameValid(firstName)) {
						jlblStatus.setText("first name is not Valid");
						return;
					}
					columns.add("firstName");
					values.add(firstName);
				}

				if (mi.length() != 0) {
					if(!isMiValid(mi)) {
						jlblStatus.setText("Mi is not valid");
						return;
					}
					columns.add("mi");
					values.add(mi);
				}

				if (address.length() != 0) {
					if(!isAddressValid(address)) {
						jlblStatus.setText("address is not valid");
						return;
					}
					columns.add("address");
					values.add(address);
				}

				if (city.length() != 0) {
					if(!isCityValid(city)) {
						jlblStatus.setText("city is not valid");
						return;
					}
					columns.add("city");
					values.add(city);
				}

				if (state.length() != 0) {
					if(!isStateValid(state)) {
						jlblStatus.setText("state is not valid");
						return;
					}
					columns.add("state");
					values.add(state);
				}

				if (telephone.length() != 0) {
					if(!isTelephoneValid(telephone)) {
						jlblStatus.setText("telephone is not valid");
						return;
					}
					if(telephone.length() == 10) {
						columns.add("telephone");
						values.add(telephone);
					}
					else {
						columns.add("telephone");
						values.add(deFormatTelephone(telephone));
					}
				}
				
				String command = "UPDATE Staff SET \n";
				for(int i = 0; i < columns.size(); i++) {
					command += columns.get(i) + " = '" + values.get(i);
					if(i + 1 < columns.size())
						command += "',\n";
					else 
						command += "'\n";
				}
				command += "WHERE id = '" + id + "';";
				
				System.out.println(command);
				
				try {
					int i = statement.executeUpdate(command);
					jlblStatus.setText("Update was successful: rows effected: " + i);
				} catch (SQLException ex) {
					jlblStatus.setText("SQL Exeption");
					System.out.print(ex.toString());
				}
			}
		});

		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtfId.getText();

				if (!isIdValid(id)) {
					jlblStatus.setText("id is not valid");
					return;
				}
			}
		});
	}

	private boolean isIdValid(String id) {
		if (id == null || id.length() != 9)
			return false;
		for (int i = 0; i < id.length(); i++) {
			if (!Character.isDigit(id.charAt(i)))
				return false;
		}
		return true;
	}

	private boolean isLastNameValid(String s) {
		if (s.length() > 15)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && s.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	private boolean isFirstNameValid(String s) {
		if (s.length() > 15)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && s.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	private boolean isMiValid(String s) {
		if (s.length() != 1)
			return false;
		char c = s.charAt(0);
		if (c == 'M' || c == 'F')
			return true;
		return false;
	}

	private boolean isAddressValid(String s) {
		if (s.length() > 20)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ')' || s.charAt(i) == '(' || s.charAt(i) == ',')
				return false;
		}
		return true;
	}

	private boolean isCityValid(String s) {
		if (s.length() > 20)
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && s.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	private boolean isStateValid(String s) {
		if (s.length() != 2 || !Character.isLetter(s.charAt(0)) || !Character.isLetter(s.charAt(1)))
			return false;
		return true;
	}

	private boolean isTelephoneValid(String s) {
		if (s.length() == 10) {
			for (int i = 0; i < s.length(); i++)
				if (!Character.isDigit(s.charAt(i)))
					return false;
			return true;
		}
		if (s.length() == 12) {
			for (int i = 0; i < s.length(); i++) {
				if (i == 3 || i == 7) {
					if (s.charAt(i) != '-')
						return false;
					continue;
				}
				if (!Character.isDigit(s.charAt(i)))
					return false;
			}
			return true;
		}
		return false;
	}

	private String formatTelephone(String s) {
		String sub1 = s.substring(0, 3);
		String sub2 = s.substring(3, 6);
		String sub3 = s.substring(6);
		return sub1 + "-" + sub2 + "-" + sub3;
	}

	private String deFormatTelephone(String s) {
		return s.substring(0, 3) + s.substring(4, 7) + s.substring(8, 12);
	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "scott", "tiger");
			jlblStatus.setText("Database Connected");
			statement = connection.createStatement();
			enableButtons(true);
		} catch (ClassNotFoundException e) {
			jlblStatus.setText("Class not found Exception");
		} catch (SQLException e) {
			jlblStatus.setText("SQL Exception");
		}
	}

	private void enableButtons(boolean b) {
		jbtView.setEnabled(b);
		jbtClear.setEnabled(b);
		jbtInsert.setEnabled(b);
		jbtUpdate.setEnabled(b);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Staff");
		JApplet applet = new StaffDatabaseEditor();
		frame.add(applet);
		applet.init();
		applet.start();
		frame.setSize(570, 270);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
