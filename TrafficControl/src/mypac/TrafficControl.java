package mypac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrafficControl extends JApplet{ 
	private static final long serialVersionUID = 1L;
	private boolean appLaunched;
	private TraficPanel traficPanel = new TraficPanel();
	private boolean movingUp;
	private Timer timerLight1 = new Timer(10000, new Listener());
	private Timer timerLight2 = new Timer(2000, new Listener());
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			appLaunched = true;
			if(timerLight1.isRunning()) {
				timerLight1.stop();
				timerLight2.start();
			}
			else {
				timerLight2.stop();
				timerLight1.start();
			}
			
			if(traficPanel.greenOn || traficPanel.redOn) {
				traficPanel.setYellowOn();
			}
			else {
				if(movingUp) {
					movingUp = false;
					traficPanel.setRedOn();
				}
				else {
					movingUp = true;
					traficPanel.setGreenOn();
				}
			}
		}
	}
	
	public TrafficControl() {
		add(traficPanel);
		traficPanel.setYellowOn();
		timerLight2.start();
	}

	class TraficPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private final int ROUND_PARAMETER1 = 20;
		private final int ROUND_PARAMETER2 = 20;
		private boolean greenOn;
		private boolean redOn;
		private boolean yellowOn;
		private Car[] carH = new Car[50];
		private Car[] carV = new Car[50];
		private int hDeadLine;
		private int vDeadLine;
		private boolean firstTime = true;
		private Graphics g;
		private int radius;
		Timer timer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		
		public TraficPanel() {
			for(int i = 0; i < carH.length; i++) {
				carH[i] = new Car(true);
				carV[i] = new Car(false);
			}
			timer.start();
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			this.g = g;
			
			radius = (getHeight() < getWidth())? getHeight() / 30 : getWidth() / 30;
			
			paintTraficLight();
			paintRoad();
			paintCars();
		}
		
		public void paintCars() {
			if(firstTime) {
				firstTime = false;
				carV[0].x = getWidth() / 2 - radius / 2;
				carV[0].y = getHeight() / 2 - (int)(radius * 3.5);
				carV[0].width = radius;
				carV[0].height = radius * 2;
				carV[0].starts = carV[0].y + radius * 2;
				carV[0].ends = carV[0].y;
				carH[0].x = getWidth() / 2 - (int)(radius * 3.5);
				carH[0].y = getHeight() / 2 - radius / 2;
				carH[0].width = radius * 2;
				carH[0].height = radius;
				carH[0].starts = carV[0].x + radius * 2;
				carH[0].ends = carV[0].x;
				
				g.setColor(carV[0].color);
				g.fillRoundRect(carV[0].x, carV[0].y, carV[0].width, carV[0].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
				g.setColor(carH[0].color);
				g.fillRoundRect(carH[0].x, carH[0].y, carH[0].width, carH[0].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
				
				for(int i = 1; i < carH.length; i++) {
					
					carV[i].frontCarIndex = i - 1;
					carV[i].x = carV[i - 1].x;
					carV[i].y = carV[i - 1].y - (int)(radius * 2.5);
					carV[i].width = carV[i - 1].width;
					carV[i].height = carV[i - 1].height;
					carV[i].starts = carV[i].y + radius * 2;
					carV[i].ends =  carV[i].y;

					carH[i].frontCarIndex = i - 1;
					carH[i].x = carH[i - 1].x - (int)(radius * 2.5);
					carH[i].y = carH[i - 1].y;
					carH[i].width = carH[i - 1].width;
					carH[i].height = carH[i - 1].height;
					carH[i].starts = carH[i].x + radius * 2;
					carH[i].ends = carH[i].x;
					
					g.setColor(carV[i].color);
					g.fillRoundRect(carV[i].x, carV[i].y, carV[i].width, carV[i].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
					g.setColor(carH[i].color);
					g.fillRoundRect(carH[i].x, carH[i].y, carH[i].width, carH[i].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
				}

			}
			else {
				for(int i = 0; i < carH.length; i++) {
					carH[i].move();
					carV[i].move();
					g.setColor(carV[i].color);
					g.fillRoundRect(carV[i].x, carV[i].y, carV[i].width, carV[i].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
					g.setColor(carH[i].color);
					g.fillRoundRect(carH[i].x, carH[i].y, carH[i].width, carH[i].height, ROUND_PARAMETER1, ROUND_PARAMETER2);
				}
			}
		}
		public void paintRoad() {
			
			g.setColor(Color.BLACK);
			
			int x1 = getWidth() / 2 - (int)(radius * 1.5);
			int y1 = getHeight() / 2 - (int)(radius * 1.5);
			int x2 = x1;
			int y2 = radius * 2;
			g.drawLine(x1, y1, x2, y2);
			
			// setting Dead lines
			hDeadLine = y1;
			vDeadLine = x1;
			
			int x3 = radius * 2;
			int y3 = y1;
			g.drawLine(x1, y1, x3, y3);
			
			int x4 = getWidth() / 2 - (int)(radius * 1.5);
			int y4 = getHeight() / 2 + (int)(radius * 1.5);
			int x5 = radius * 2;
			int y5 = y4;
			g.drawLine(x4, y4, x5, y5);
			
			int x6 = x4;
			int y6 = getHeight() - radius * 2;
			g.drawLine(x4, y4, x6, y6);
			
			int x7 = getWidth() / 2 + (int)(radius * 1.5);
			int y7 = getHeight() / 2 + (int)(radius * 1.5);
			int x8 = x7;
			int y8 = getHeight() - radius * 2;
			g.drawLine(x7, y7, x8, y8);
			
			int x9 = getWidth() - radius * 2;
			int y9 = y7;
			g.drawLine(x7, y7, x9, y9);
			
			int x10 = getWidth() / 2 + (int)(radius * 1.5);
			int y10 = getHeight() / 2 - (int)(radius * 1.5);
			int x11 = getWidth() - radius * 2;
			int y11 = y10;
			g.drawLine(x10, y10, x11, y11);
			
			int x12 = x10;
			int y12 = radius * 2;
			g.drawLine(x10, y10, x12, y12);
		}
		public void paintTraficLight() {
			// rectangle
			int x1 = getWidth() / 2 - (int)(radius * 5);
			int y1 = getHeight() / 2 + 2 * radius;
			g.fillRect(x1, y1, radius * 3, radius * 8);
			
			// Oval Red
			int x2 = x1 + (int)(radius * 1.5);
			int y2 = y1 + (int)(radius * 1.5);
			if(redOn) {
				g.setColor(Color.RED);
				g.fillOval(x2 - radius, y2 - radius, radius * 2, radius * 2);
			}		
			else {
				g.setColor(Color.BLACK);
				g.fillOval(x2 - radius, y2 - radius, radius * 2, radius * 2);
			}
			
			// Oval Yellow
			int x3 = x2;
			int y3 = y2 + (int)(2.5 * radius);
			if(yellowOn) {
				g.setColor(Color.YELLOW);
				g.fillOval(x3 - radius, y3 - radius, radius * 2, radius * 2);
			}		
			else {
				g.setColor(Color.BLACK);
				g.fillOval(x3 - radius, y3 - radius, radius * 2, radius * 2);
			}

						
			// Oval Green
			int x4 = x2;
			int y4 = y3 + (int)(2.5 * radius);
			if(greenOn) {
				g.setColor(Color.GREEN);
				g.fillOval(x4 - radius, y4 - radius, radius * 2, radius * 2);
			}		
			else {
				g.setColor(Color.BLACK);
				g.fillOval(x4 - radius, y4 - radius, radius * 2, radius * 2);
			}
		}
		
		public void setGreenOn() {
			greenOn = true;
			redOn = false;
			yellowOn = false;
			repaint();
		}
		public void setRedOn() {
			greenOn = false;
			redOn = true;
			yellowOn = false;
			repaint();
		}
		public void setYellowOn() {
			greenOn = false;
			redOn = false;
			yellowOn = true;
			repaint();
		}	
		
		class Car {
			final int STEP = 4;
			int starts;
			int ends;
			int x;
			int y;
			int width;
			int height;
			boolean isHorizontal;
			boolean isStoped = true;
			int frontCarIndex = carH.length - 1;
			Color color;
			
			public Car(boolean isHorizontal) {
				this.isHorizontal = isHorizontal;
				int r = (int)(Math.random() * 256);
				int g = (int)(Math.random() * 256);
				int b = (int)(Math.random() * 256);
				color = new Color(r, g, b);
			}
			private void move() {
				if(appLaunched) {
					if(isHorizontal) {
						if(redOn) {	
							if(getDistance(carH[frontCarIndex].ends, this.starts) > width) {
								x += STEP;
								if(ends > getWidth()) {
									if(carH[frontCarIndex].ends > 0)
										x = -width;
									else
										x = carH[frontCarIndex].ends - width;
								}
							}
						}
						else {
							if(getDistance(carH[frontCarIndex].ends, this.starts) > width / 4 && (starts > hDeadLine || getDistance(starts, hDeadLine) > 2)) {
								x+= STEP;
								if(ends > getWidth()) {
									if(carH[frontCarIndex].ends > 0)
										x = -width;
									else
										x = carH[frontCarIndex].ends - width;
								}
							}
						}
						starts = x + radius * 2;
						ends = x;
					}
					else {
						if(greenOn) {
							if(getDistance(carV[frontCarIndex].ends, this.starts) > height) {
								y += STEP;
								if(ends > getHeight()) {
									if(carV[frontCarIndex].ends > 0)
										y = -height;
									else
										y = carV[frontCarIndex].ends - height;
								}
							}
						}
						else {
							if(getDistance(carV[frontCarIndex].ends, this.starts) > width / 2 && (starts > vDeadLine || getDistance(starts, vDeadLine) > 2)) {
								y += STEP;
								if(ends > getHeight()) {
									if(carV[frontCarIndex].ends > 0)
										y = -height;
									else
										y = carV[frontCarIndex].ends - height;
								}
							}
						}

						starts = y + radius * 2;
						ends = y;
					}
				}
			
			}
			
			private int getDistance(int a, int b) {
				 if(a < 0 ^ b < 0) {
					 return Math.abs(a) + Math.abs(b);
				 }
				 else
					 return Math.abs(a - b);
				 
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Traffic");
		frame.add(new TrafficControl());
		frame.setSize(800, 830);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
