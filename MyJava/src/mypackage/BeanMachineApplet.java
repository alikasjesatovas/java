package mypackage;

/*The bean machine is a device for statistic experiments named after English scientist Sir
Francis Galton. It consists of an upright board with evenly spaced nails (or pegs)
in a triangular form. Balls are dropped from the opening of the board. Every time a ball hits a nail, it
has a 50% chance of falling to the left or to the right. The piles of balls are accumulated
in the slots at the bottom of the board.
This program animates simulation of bean machine.
The GUI program lets you set the number of slots. 
Click Start to start or restart the animation.
This program can work as stand alone or as JApplet*/

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class BeanMachineApplet extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtfslots = new JTextField("8", 5);
	BeanMachine beanMachine = new BeanMachine();
	
	
	public BeanMachineApplet() {
		JPanel jpSouth = new JPanel();
		jpSouth.add(new JLabel("Number of slots"));
		JButton jbtStart = new JButton("Start");
		jpSouth.add(jtfslots);
		jpSouth.add(jbtStart);
		
		add(beanMachine);
		add(jpSouth, BorderLayout.SOUTH);
		
		jbtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int slots = Integer.parseInt(jtfslots.getText());
				beanMachine.setNumerOfslots(slots);
			}
		});
	}
	class BeanMachine extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int slots = 7;
		private int[][] balls;
		private final int BALLS_TO_THROW = 10;
		private int ballsPotedCount = 0;
		private int count1 = 0;
		private int count2 = 0;
		private int count2Bound;
		private int count3 = 0;
		private int count3Bound;
		private int currentHole;
		private double currentHoleDouble = slots / 2;
		private int[] holes = new int[slots + 1];
		private int radius;
		private boolean startover = false;
		private int tempX;
		private int tempY;
		private int finalY;
		private int x;
		private int xStart;
		private int y;
		private int yStart;
		Timer timer1 = new Timer(7, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count1++;
				y++;
				repaint();
				if(count1 >= radius * 4) {
					count1 = 0;
					timer1.stop();
					timer2.start();
				}
			}
		});
		Timer timer2 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count1++;
				if(count1 <= slots) {
					if((int)(Math.random() * 10) % 2 == 0) {
						timer2.stop();
						currentHoleDouble += 0.5;
						timerToRight.start();
					}
					else {
						timer2.stop();
						currentHoleDouble -= 0.5;
						timerToLeft.start();
					}
				}
				else {
					timer2.stop();
					currentHole = (int)currentHoleDouble;
					currentHoleDouble = slots / 2.0;
					holes[currentHole]++;
					count1 = 0;
					timer3.start();
				}
			}
		});
		Timer timer3 = new Timer(3 , new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count3++;
				if(count3 == 1) {
					count3Bound = 12 * radius - holes[currentHole] * radius * 2;
				}
				if(count3 <= count3Bound) {
					y++;
					repaint();
				}
				else {
					timer3.stop();
					count3 = 0;
					addBall(x, y);
					repaint();
				}
			}
		});
		Timer timerToLeft = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count2++;
				if(count2 == 1) {
					count2Bound = 19 + 10;
					finalY = y + radius * 4;
				}
				if(count2 > count2Bound) {
					timerToLeft.stop();
					count2 = 0;
					timer2.start();
					return;
				}
				if(count2 == 1) {
					tempX = x;
					tempY = y;
				}
				
				
				switch(count2) {
				case 0  : x =  (int)(tempX - 0.6 * radius);    y =  (int)(tempY + 0.1 * radius); repaint(); break;
				case 1  : x =  (int)(tempX - 0.8 * radius);    y =  (int)(tempY + 0.2 * radius); repaint(); break;
				case 2  : x =        tempX -       radius;     y =  (int)(tempY + 0.3 * radius); repaint(); break;
				case 3  : x =  (int)(tempX - 1.2 * radius);    y =  (int)(tempY + 0.4 * radius); repaint(); break;
				case 4  : x =  (int)(tempX - 1.3 * radius);    y =  (int)(tempY + 0.5 * radius); repaint(); break;
				case 5  : x =  (int)(tempX - 1.4 * radius);    y =  (int)(tempY + 0.6 * radius); repaint(); break;
				case 6  : x =  (int)(tempX - 1.5 * radius);    y =  (int)(tempY + 0.7 * radius); repaint(); break;
				case 7  : x =  (int)(tempX - 1.6 * radius);    y =  (int)(tempY + 0.8 * radius); repaint(); break;
				case 8  : 								       y =  (int)(tempY + 0.9 * radius); repaint(); break;
				case 9  : x =  (int)(tempX - 1.7 * radius);    y =        tempY +       radius ; repaint(); break;
				case 10 : 									   y =  (int)(tempY + 1.1 * radius); repaint(); break;
				case 11 : x =  (int)(tempX - 1.8 * radius);    y =  (int)(tempY + 1.2 * radius); repaint(); break;
				case 12 :    								   y =  (int)(tempY + 1.3 * radius); repaint(); break;
				case 13 : x =  (int)(tempX - 1.9 * radius);    y =  (int)(tempY + 1.4 * radius); repaint(); break;
				case 14 :                                      y =  (int)(tempY + 1.5 * radius); repaint(); break;
				case 15 :                                      y =  (int)(tempY + 1.6 * radius); repaint(); break;
				case 16 :                                      y =  (int)(tempY + 1.7 * radius); repaint(); break;
				case 17 : x =        tempX - 2   * radius;     y =  (int)(tempY + 1.8 * radius); repaint(); break;
				case 18 :                                      y =  (int)(tempY + 1.9 * radius); repaint(); break;
				case 19 :                                      y =        tempY + 2   * radius;  repaint(); break;
				default : if(count1 < slots){y = (count2 == count2Bound)? finalY : y + radius / 5; repaint();}  else{count2 += count2Bound;}
					
				}
			}
		});
		Timer timerToRight = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count2++;
				if(count2 == 1) {
					count2Bound = 19 + 10;
					finalY = y + radius * 4;
					tempX = x;
					tempY = y;
				}
				
				if(count2 > count2Bound) {
					timerToRight.stop();
					count2 = 0;
					timer2.start();
					return;
				}
				
				switch(count2) {
				case 0  : x =  (int)(tempX + 0.6 * radius);    y =  (int)(tempY + 0.1 * radius); repaint(); break;
				case 1  : x =  (int)(tempX + 0.8 * radius);    y =  (int)(tempY + 0.2 * radius); repaint(); break;
				case 2  : x =        tempX +       radius ;    y =  (int)(tempY + 0.3 * radius); repaint(); break;
				case 3  : x =  (int)(tempX + 1.2 * radius);    y =  (int)(tempY + 0.4 * radius); repaint(); break;
				case 4  : x =  (int)(tempX + 1.3 * radius);    y =  (int)(tempY + 0.5 * radius); repaint(); break;
				case 5  : x =  (int)(tempX + 1.4 * radius);    y =  (int)(tempY + 0.6 * radius); repaint(); break;
				case 6  : x =  (int)(tempX + 1.5 * radius);    y =  (int)(tempY + 0.7 * radius); repaint(); break;
				case 7  : x =  (int)(tempX + 1.6 * radius);    y =  (int)(tempY + 0.8 * radius); repaint(); break;
				case 8  : 								       y =  (int)(tempY + 0.9 * radius); repaint(); break;
				case 9  : x =  (int)(tempX + 1.7 * radius);    y =        tempY +       radius ; repaint(); break;
				case 10 : 									   y =  (int)(tempY + 1.1 * radius); repaint(); break;
				case 11 : x =  (int)(tempX + 1.8 * radius);    y =  (int)(tempY + 1.2 * radius); repaint(); break;
				case 12 :   								   y =  (int)(tempY + 1.3 * radius); repaint(); break;
				case 13 : x =  (int)(tempX + 1.9 * radius);    y =  (int)(tempY + 1.4 * radius); repaint(); break;
				case 14 :                                      y =  (int)(tempY + 1.5 * radius); repaint(); break;
				case 15 :                                      y =  (int)(tempY + 1.6 * radius); repaint(); break;
				case 16 :                                      y =  (int)(tempY + 1.7 * radius); repaint(); break;
				case 17 : x =        tempX + 2 *   radius;     y =  (int)(tempY + 1.8 * radius); repaint(); break;
				case 18 :                                      y =  (int)(tempY + 1.9 * radius); repaint(); break;
				case 19 :                                      y =        tempY + 2   * radius;  repaint(); break;
				default : if(count1 < slots){y = (count2 == count2Bound)? finalY : y + radius / 5; repaint();}  else{count2 += count2Bound;}
				}
			}
		});
		
		public void setNumerOfslots(int slots) {
			if(slots > 1 && slots < 206)  {
				this.slots = slots;
				timer1.stop();
				timer2.stop();
				timer3.stop();
				timerToLeft.stop();
				timerToRight.stop();
				count1 = 0;
				count2 = 0;
				count3 = 0;
				currentHoleDouble = this.slots / 2.0;
				this.slots = slots - 1;
				holes = new int[slots + 1];
				balls = null;
				ballsPotedCount = 0;
				for(int i = 0; i < holes.length; i++) {
					holes[i] = 0;
				}
				startover = true;
				repaint();
			}
		}
		
		public BeanMachine() {
		}
		
		private void addBall(int x , int y) {
			ballsPotedCount++;
			if(ballsPotedCount < BALLS_TO_THROW)
				startover = true;
			if(balls == null) {
				balls = new int[1][2];
			}
			else {
				int [][] tempBalls = balls;
				balls = new int[balls.length + 1][2];
				for(int i = 0; i < tempBalls.length; i++)
					System.arraycopy(tempBalls[i], 0, balls[i], 0, tempBalls[i].length);
			}
			balls[balls.length - 1][0] = x;
			balls[balls.length - 1][1] = y;
		}	
		public int getDivider() {
			if(slots == 1)
				return 30 * slots;
			else if(slots == 2)
				return 20 * slots;
			else if(slots == 3)
				return 15 * slots;
			else if(slots == 4)
				return 12 * slots;
			else if(slots == 5)
				return 11 * slots;
			else if(slots == 6) 
				return 10 * slots;
			else if(slots == 7)
				return 9 * slots;
			else if(slots == 8 || slots == 9) 
				return 8 * slots;
			else if(slots == 10 || slots == 11)
				return 7 * slots;
			else if(slots > 11 && slots < 30)
				return 5 * slots;
			else
				return 4 * slots;
		}
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			
			// getting start points and radius
			
			int n = getDivider();
			radius = (getWidth() < getHeight())? getWidth() / n : getHeight() / n;
			xStart = getWidth() / 2;
			yStart = radius * 10;
			
			// circles
			g.setColor(Color.RED);
			// CircleCordinates is start point of lines which start from bottom circles
			int[][] circlesCordinates = new int[slots][2];
			for(int i = 0; i < slots; i++){
				int x = xStart - 2 * radius * i;
				int y = yStart + 4 * radius * i;
				for(int j = 0; j <= i; j++){
					int temX = x + 4 * radius * j;
					g.fillOval(temX - radius, y - radius, radius * 2, radius * 2);
					if(i == slots - 1){
						circlesCordinates[j][0] = temX;
						circlesCordinates[j][1] = y + radius;
					}
				}
			}
			if(startover) {
				x = xStart;
				y = yStart - 6 * radius;
				startover = false;
				timer1.start();
			}
			
			// lines from circle
			g.setColor(Color.BLACK);
			for(int i = 0; i < circlesCordinates.length; i++){
				g.drawLine(circlesCordinates[i][0], circlesCordinates[i][1], circlesCordinates[i][0], circlesCordinates[i][1] + 10 * radius);
			}
			// drawing top lines
			int x1 = xStart - 2 * radius - (int)(0.4 * radius);
			int y1 = yStart - 6 * radius;
			int x2 = xStart - 2 * radius - (int)(0.4 * radius);
			int y2 = yStart - 2 * radius;
			g.drawLine(x1, y1, x2, y2);
			int x3 = xStart + 2 * radius + (int)(0.4 * radius);
			int y3 = yStart - 6 * radius;
			int x4 = xStart + 2 * radius + (int)(0.4 * radius);
			int y4 = yStart - 2 * radius;
			g.drawLine(x3, y3, x4, y4);
			// drawing lower 2 lines
			int x5 = circlesCordinates[0][0] - 4 * radius - (int)(0.4 * radius);
			int y5 = circlesCordinates[0][1];
			int x7 = circlesCordinates[slots - 1][0] + 4 * radius + (int)(0.4 * radius);
			int y7 = circlesCordinates[slots - 1][1];
			g.drawLine(x2, y2, x5, y5);
			g.drawLine(x4, y4, x7, y7);
			// drawing lower 3 lines
			int x6 = x5;
			int y6 = circlesCordinates[0][1] + 10 * radius;
			int x8 = x7;
			int y8 = y6;
			g.drawLine(x5, y5, x6, y6);
			g.drawLine(x7, y7, x8, y8);
			g.drawLine(x6, y6, x8, y8);
			
			//drawing balls in holes
			g.setColor(Color.BLUE);
			if(balls != null)
				for(int i = 0; i < balls.length; i++)
					g.fillOval(balls[i][0] - radius, balls[i][1] - radius, radius * 2, radius * 2);
			//drawing falling ball
			if(timer1.isRunning() || timer2.isRunning() || timer3.isRunning() || timerToLeft.isRunning() || timerToRight.isRunning())
				g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}
	}
	public static void main(String[] main) {
		JFrame frame = new JFrame("Exercise 22");
		frame.setResizable(false);
		frame.add(new BeanMachineApplet());
		frame.setSize(1000,900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

