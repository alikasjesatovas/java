/*This program demonstrates MVC model*/


package mypackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class MVC extends JFrame {
	private static final long serialVersionUID = 1L;
	Object[] columnNames = {"DataName", "Data"};
	Object[][] data = 
		   {{"CS", 200}, 
			{"Math", 40}, 
			{"Chem", 50}, 
			{"Biol", 100}, 
			{"Phys", 40}, 
			{"Buss", 10}};
	MyTableModel tableModel = new MyTableModel(data, columnNames);
	JTable table = new JTable(tableModel);
	public ChartModel chartModel = new ChartModel();
	
	public MVC() {
		updateChartModel();
		
		JButton jbtInsert = new JButton("Insert");
		JButton jbtDelete = new JButton("Delete");
		JButton jbtUpdate = new JButton("Update");
		JButton jbtViewPieChart = new JButton("View Pie Chart");
		JButton jbtViewBarChart = new JButton("View Bar Chart");
		
		JPanel jpNorth = new JPanel();
		jpNorth.add(jbtInsert);
		jpNorth.add(jbtDelete);
		jpNorth.add(jbtUpdate);
		
		JPanel jpSouth = new JPanel();
		jpSouth.add(jbtViewPieChart);
		jpSouth.add(jbtViewBarChart);
		
		add(new JScrollPane(table));
		add(jpNorth, BorderLayout.NORTH);
		add(jpSouth, BorderLayout.SOUTH);
		
		jbtViewBarChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BarChart barChart = new BarChart(chartModel);
				JFrame frame = new JFrame("Bar Chart");
				frame.add(barChart);
				frame.setSize(250, 260);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		
		jbtViewPieChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PieChart pieChart = new PieChart(chartModel);
				JFrame frame = new JFrame("Pie Chart");
				frame.add(pieChart);
				frame.setSize(250, 260);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		
		jbtInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() >= 0)
					tableModel.insertRow(table.getSelectedRow(), new Object[] {"null", 0});
				else
					tableModel.addRow(new Object[] {"null", 0});
			}
		});
		
		jbtDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() >= 0)
					tableModel.removeRow(table.getSelectedRow());
			}
		});
		
		jbtUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateChartModel();
			}
		});
	}
	
	public void updateChartModel() {
		String[] dataNames = new String[table.getRowCount()];
		int[] data = new int[table.getRowCount()];
		for(int i = 0; i < data.length; i++) {
			String nam = (String)tableModel.getValueAt(i, 0);
			Integer dat = (Integer)tableModel.getValueAt(i, 1);
			if(nam == null) {
				nam = "null";
			}
			if(dat == null)
				dat = 0;
			dataNames[i] = nam;
			data[i] = dat;
		}
		chartModel.setChartData(dataNames, data);
	}
	
	public static void main(String[] args) {
		JFrame frame = new MVC();
		frame.setTitle("Controller");
		frame.setSize(280, 269);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}


class MyTableModel extends DefaultTableModel {
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

class ChartModel {
	ArrayList<ActionListener> listeners;
	int[] data = new int[] { 200, 40, 50, 100, 40, 10 };
	String[] dataName = new String[] { "CS", "Math", "Chem", "Biol", "Phys", "Buss" };

	public int[] getData() {
		return data;
	}

	public String[] getDataName() {
		return dataName;
	}

	public void setChartData(String[] newDataName, int[] newData) {
		data = newData;
		dataName = newDataName;
		processEvent();
	}

	public synchronized void addActionListener(ActionListener l) {
		if (listeners == null) {
			listeners = new ArrayList<ActionListener>();
		}
		listeners.add(l);
	}

	public synchronized void removeActionListenter(ActionListener l) {
		if (listeners == null || listeners.size() == 0 || !listeners.contains(l))
			return;
		listeners.remove(l);
	}

	public void processEvent() {
		ArrayList<ActionListener> list;
		synchronized (this) {
			if (listeners == null || listeners.size() == 0)
				return;
			list = (ArrayList<ActionListener>) listeners.clone();
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	}
}

class BarChart extends JPanel {
	private static final long serialVersionUID = 1L;
	private int colorNr = 0;
	private ChartModel model;

	public BarChart(ChartModel model) {
		this.model = model;
		model.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
	}

	private Color getNextColor(boolean isLast) {
		Color color;
		if (colorNr == 0)
			color = Color.RED;
		else if (colorNr == 1)
			color = Color.YELLOW;
		else if (colorNr == 2)
			color = Color.GREEN;
		else if (colorNr == 3)
			color = Color.BLUE;
		else if (colorNr == 4)
			color = Color.CYAN;
		else if (colorNr == 5)
			color = Color.ORANGE;
		else if (colorNr == 6)
			color = Color.PINK;
		else
			color = Color.DARK_GRAY;

		if (colorNr >= 7)
			colorNr = 0;
		else
			colorNr++;
		if (isLast) {
			colorNr = 0;
		}

		return color;
	}

	/** Override getPreferredSize */
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	/** Paint the histogram */
	protected void paintComponent(Graphics g) {
		if (model == null)
			return;

		super.paintComponent(g);

		// Find the panel size and bar width and interval dynamically
		int width = getWidth();
		int height = getHeight();
		int interval = (width - 40) / model.data.length;
		int individualWidth = (int) (((width - 40) / model.data.length) * 0.60);

		// Find the maximum count. The maximum count has the highest bar
		int maxCount = 0;
		for (int i = 0; i < model.data.length; i++) {
			if (maxCount < model.data[i])
				maxCount = model.data[i];
		}

		// x is the start position for the first bar in the histogram
		int x = 30;

		// Draw a horizontal base line
		g.drawLine(10, height - 45, width - 10, height - 45);
		for (int i = 0; i < model.data.length; i++) {
			// Find the bar height
			int barHeight = (int) (((double) model.data[i] / (double) maxCount) * (height - 80));

			// Display a bar (i.e. rectangle)
			g.setColor(getNextColor(!(i + 1 < model.data.length)));
			g.fillRect(x, height - 45 - barHeight, individualWidth, barHeight);
			g.setColor(Color.BLACK);

			// Display a letter under the base line
			g.drawString(model.dataName[i], x, height - 45 - barHeight - 3);

			// Move x for displaying the next character
			x += interval;
		}
	}
}


class PieChart extends JPanel {
	private static final long serialVersionUID = 1L;
	private int colorNr = 0;
	private ChartModel model;

	public PieChart(ChartModel model) {
		this.model = model;
		model.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (model == null)
			return;

		int sum = 0;
		for (int i = 0; i < model.data.length; i++) {
			sum += model.data[i];
		}

		int xCenter = getWidth() / 2;
		int yCenter = getHeight() / 2;
		int radius = (int) (((getWidth() < getHeight()) ? getWidth() : getHeight()) * 0.4);
		double val = 360.0 / sum;
		int start = 0;

		for (int i = 0; i < model.data.length; i++) {
			g.setColor(getNextColor(!(i + 1 < model.data.length)));
			int degree = (int) Math.round(model.data[i] * val);
			g.fillArc(xCenter - radius, yCenter - radius, radius * 2, radius * 2, start, (int) (degree));
			int stringX = xCenter + (int) (radius * Math.sin(convert(start + degree / 2) * 2 * Math.PI / 360));
			int stringY = yCenter - (int) (radius * Math.cos(convert(start + degree / 2) * 2 * Math.PI / 360));
			g.setColor(Color.BLACK);
			g.drawString(model.dataName[i], stringX, stringY);
			start += degree;
		}
	}

	private double convert(double d) {
		double val = 90 - d;
		if (val < 0)
			return 360 + val;
		return val;
	}

	private Color getNextColor(boolean isLast) {
		if (isLast && colorNr == 0) {
			colorNr = 0;
			return Color.YELLOW;
		}

		Color color;
		if (colorNr == 0)
			color = Color.RED;
		else if (colorNr == 1)
			color = Color.YELLOW;
		else if (colorNr == 2)
			color = Color.GREEN;
		else if (colorNr == 3)
			color = Color.BLUE;
		else if (colorNr == 4)
			color = Color.CYAN;
		else if (colorNr == 5)
			color = Color.ORANGE;
		else if (colorNr == 6)
			color = Color.PINK;
		else
			color = Color.DARK_GRAY;

		if (colorNr >= 7)
			colorNr = 0;
		else
			colorNr++;

		if (isLast)
			colorNr = 0;
		return color;
	}
}
