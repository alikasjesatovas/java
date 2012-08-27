package mypackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChartModel {
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
