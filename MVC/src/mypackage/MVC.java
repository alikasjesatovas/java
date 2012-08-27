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


