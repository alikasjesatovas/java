package mypackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class BarChart extends JPanel {
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