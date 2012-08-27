package mypackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PieChart extends JPanel {
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