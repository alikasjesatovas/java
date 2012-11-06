/*This program lets the user move a knight to any starting square and click the
Solve button to animate a knight moving along the path*/
package mypk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class KnightTour extends JApplet {
	private static final long serialVersionUID = 1L;
	Chessboard board = new Chessboard();

	public KnightTour() {
		JPanel jpSouth = new JPanel();
		JButton jbtSearch = new JButton("Solve");
		jpSouth.add(jbtSearch);

		this.getContentPane().add(board);
		this.getContentPane().add(jpSouth, BorderLayout.SOUTH);

		jbtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.timer.stop();
				board.xy = null;
				board.getPath();
			}
		});

	}

	class Chessboard extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int[] start;
		private int[] finish;
		private Image image;
		private List<int[]> list = new ArrayList<int[]>();
		private List<int[]> list2 = new ArrayList<int[]>();
		private Cell[][] cell = new Cell[8][8];
		private int list2Count = 0;
		int[] xy;
		Timer timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list2Count == list2.size()) {
					timer.stop();
					xy = null;
					return;
				}
				xy =  list2.get(list2Count);
				list2Count++;
				repaint();
			}
		});

		public Chessboard() {
			image = new ImageIcon("Knight.gif").getImage();
			for (int i = 0; i < cell.length; i++)
				for (int j = 0; j < cell[i].length; j++)
					cell[i][j] = new Cell();
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					timer.stop();
					xy = null;
					int x = e.getX();
					int y = e.getY();
					int r = y / (getHeight() / 8);
					int c = x / (getWidth() / 8);
					if (start == null) {
						start = new int[2];
						start[0] = r;
						start[1] = c;
					} else if (finish == null) {
						if (!(r == start[0] && c == start[1])) {
							finish = new int[2];
							finish[0] = r;
							finish[1] = c;
						}
					} else {
						start = new int[2];
						start[0] = r;
						start[1] = c;
						finish = null;
					}
					repaint();
				}
			});

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// drawing net
			int cellWidth = getWidth() / 8;
			int cellHeight = getHeight() / 8;
			{
				Color color = Color.WHITE;
				g.setColor(color);
				int x = 0;
				int y = 0;
				for (int i = 1; i <= 8; i++) {
					x = 0;
					for (int j = 1; j <= 8; j++) {
						g.fillRect(x, y, getWidth() / 8, getHeight() / 8);
						if (color.equals(Color.WHITE) && j != 8) {
							color = Color.BLACK;
							g.setColor(color);
						} else if (color.equals(Color.BLACK) && j != 8) {
							color = Color.WHITE;
							g.setColor(color);
						}

						x += getWidth() / 8;
					}
					y += getHeight() / 8;
				}
			}
			// geting cells center positions
			for (int i = 0; i < cell.length; i++)
				for (int j = 0; j < cell[i].length; j++) {
					cell[i][j].x = cellWidth / 2 + cellWidth * j;
					cell[i][j].y = cellHeight / 2 + cellHeight * i;
				}
			// drawing knights
			if (start != null) {
				int x = cell[start[0]][start[1]].x - cellWidth / 2 + 3;
				int y = cell[start[0]][start[1]].y - cellHeight / 2 + 3;
				g.drawImage(image, x, y, cellWidth - 6, cellHeight - 6, this);
			}
			if (finish != null) {
				int x = cell[finish[0]][finish[1]].x - cellWidth / 2 + 3;
				int y = cell[finish[0]][finish[1]].y - cellHeight / 2 + 3;
				g.drawImage(image, x, y, cellWidth - 6, cellHeight - 6, this);
			}
			if (xy != null) {
				int x = xy[0] - cellWidth / 2 + 3;
				int y = xy[1] - cellHeight / 2 + 3;
				g.drawImage(image, x, y, cellWidth - 6, cellHeight - 6, this);
			}
		}

		public void getPath() {
			if (start != null && finish != null) {
				list.clear();
				list2.clear();
				list2Count = 0;
				for (int i = 0; i < cell.length; i++)
					for (int j = 0; j < cell[i].length; j++)
						cell[i][j].visited = false;
				if (search(start[0], start[1], finish)) {
					start = null;
					decompose();
					timer.start();
				}
			} else {
				repaint();
			}
		}

		private boolean search(int r, int c, int[] endCoordinates) {

			cell[r][c].visited = true;
			int[] temp = { r, c };
			list.add(temp);

			if (r == endCoordinates[0] && c == endCoordinates[1])
				return true;

			int[][] val = getNext(r, c);

			if (val == null) {
				cell[r][c].visited = false;
				list.remove(list.size() - 1);
				return false;
			}

			for (int i = 0; i < val.length; i++) {
				if (search(val[i][0], val[i][1], endCoordinates))
					return true;
			}
			cell[r][c].visited = false;
			list.remove(list.size() - 1);
			return false;

		}

		private void decompose() {
			for (int i = 1; i < list.size(); i++) {
				int x1 = cell[ list.get(i - 1)[0]][ list
						.get(i - 1)[1]].x;
				int y1 = cell[ list.get(i - 1)[0]][ list
						.get(i - 1)[1]].y;
				int x2 = cell[ list.get(i)[0]][ list.get(i)[1]].x;
				int y2 = cell[ list.get(i)[0]][ list.get(i)[1]].y;

				double xStep = (x2 - x1) / 20.0;
				double yStep = (y2 - y1) / 20.0;

				for (int j = 1; j <= 20; j++) {
					int[] temp = new int[2];
					if ((j != 20)) {
						temp[0] = (int) (x1 + xStep * j);
						temp[1] = (int) (y1 + yStep * j);
					} else {
						temp[0] = x2;
						temp[1] = y2;
					}
					list2.add(temp);
				}
			}
		}

		public int[][] getNext(int r, int c) {
			int count = 0;
			int[][] val = null;
			if (r - 1 >= 0 && c - 2 >= 0 && !cell[r - 1][c - 2].visited) {
				count++;
				val = appendInt(val, r - 1, c - 2);
			}
			if (r + 1 < 8 && c - 2 >= 0 && !cell[r + 1][c - 2].visited) {
				count++;
				val = appendInt(val, r + 1, c - 2);
			}
			if (r + 2 < 8 && c - 1 >= 0 && !cell[r + 2][c - 1].visited) {
				count++;
				val = appendInt(val, r + 2, c - 1);
			}
			if (r + 2 < 8 && c + 1 < 8 && !cell[r + 2][c + 1].visited) {
				count++;
				val = appendInt(val, r + 2, c + 1);
			}
			if (r + 1 < 8 && c + 2 < 8 && !cell[r + 1][c + 2].visited) {
				count++;
				val = appendInt(val, r + 1, c + 2);
			}
			if (r - 1 >= 0 && c + 2 < 8 && !cell[r - 1][c + 2].visited) {
				count++;
				val = appendInt(val, r - 1, c + 2);
			}
			if (r - 2 >= 0 && c + 1 < 8 && !cell[r - 2][c + 1].visited) {
				count++;
				val = appendInt(val, r - 2, c + 1);
			}
			if (r - 2 >= 0 && c - 1 >= 0 && !cell[r - 2][c - 1].visited) {
				count++;
				val = appendInt(val, r - 2, c - 1);
			}

			if (count == 0)
				return null;

			for (int i = 0; i < val.length; i++)
				for (int j = i + 1; j < val.length; j++)
					if (getCountOfAvailables(val[i][0], val[i][1]) > getCountOfAvailables(
							val[j][0], val[j][1])) {
						int[] temp = val[i];
						val[i] = val[j];
						val[j] = temp;
					}
			return val;
		}

		private int getCountOfAvailables(int r, int c) {
			int count = 0;
			if (r - 1 >= 0 && c - 2 >= 0 && !cell[r - 1][c - 2].visited)
				count++;
			if (r + 1 < 8 && c - 2 >= 0 && !cell[r + 1][c - 2].visited)
				count++;
			if (r + 2 < 8 && c - 1 >= 0 && !cell[r + 2][c - 1].visited)
				count++;
			if (r + 2 < 8 && c + 1 < 8 && !cell[r + 2][c + 1].visited)
				count++;
			if (r + 1 < 8 && c + 2 < 8 && !cell[r + 1][c + 2].visited)
				count++;
			if (r - 1 >= 0 && c + 2 < 8 && !cell[r - 1][c + 2].visited)
				count++;
			if (r - 2 >= 0 && c + 1 < 8 && !cell[r - 2][c + 1].visited)
				count++;
			if (r - 2 >= 0 && c - 1 >= 0 && !cell[r - 2][c - 1].visited)
				count++;
			return count;
		}

		private int[][] appendInt(int[][] value, int i, int j) {
			if (value == null) {
				int[][] newVal = { { i, j } };
				return newVal;
			}

			int[][] temp = value;
			value = new int[value.length + 1][2];
			System.arraycopy(temp, 0, value, 0, temp.length);
			value[value.length - 1][0] = i;
			value[value.length - 1][1] = j;
			return value;
		}
	}

	class Cell {
		public int x;
		public int y;
		public boolean visited;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("KnightTour");
		frame.add(new KnightTour());
		frame.setSize(422, 473);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
