package mypac;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DateFormatSymbols;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Calendar extends JApplet {
	private static final long serialVersionUID = 1L;
	
	String[] months = new DateFormatSymbols().getMonths();
	SpinnerListModel spinnerListModel = new SpinnerListModel(Arrays.asList(months).subList(0, 12));
	SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(2012, 0, 3000, 1);
	JSpinner jSpinnerMonth = new JSpinner(spinnerListModel);
	JSpinner jSpinnerYear = new JSpinner(spinnerNumberModel);
	CalendarPanel calendarPanel = new CalendarPanel();
	
	public Calendar() {
		JPanel jpNorth = new JPanel(new GridLayout(1, 2));
		jpNorth.add(jSpinnerMonth);
		jpNorth.add(jSpinnerYear);
		calendarPanel.setMonth(spinnerListModel.getList().indexOf(spinnerListModel.getValue()));
		calendarPanel.setYear(((Integer)spinnerNumberModel.getValue()).intValue());
		JSpinner.NumberEditor numberEditor = new JSpinner.NumberEditor(jSpinnerYear, "####");
		jSpinnerYear.setEditor(numberEditor);
		
		jSpinnerMonth.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				calendarPanel.setMonth(spinnerListModel.getList().indexOf(spinnerListModel.getValue()));
			}
		});
		jSpinnerYear.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				calendarPanel.setYear(((Integer)spinnerNumberModel.getValue()).intValue());
			}
		});
		
		add(calendarPanel);
		add(jpNorth, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("E35_14");
		frame.setSize(600, 320);
		JApplet applet = new Calendar();
		frame.add(applet);
		applet.init();
		applet.start();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
