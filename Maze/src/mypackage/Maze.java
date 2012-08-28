/*This Aapplet can find a path in a maze. 
 * The maze is represented by an board. The path must
 *meet the following conditions:
 *The path is between the upper-left corner cell and the lower-right corner cell
 *in the maze.
 *The Applet enables the user to place or remove a mark on a cell. A path consists
 *of adjacent unmarked cells. Two cells are said to be adjacent if they are
 *horizontal or vertical neighbors, but not if they are diagonal neighbors.
 *The path does not contain cells that form a square.*/

package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Maze extends JApplet {
	private static final long serialVersionUID = 1L;
	Cell[][] cell = new Cell[9][9];
	java.util.ArrayList<int[][]> list = new java.util.ArrayList<int[][]>();

	public Maze() {
		JPanel jpCenter = new JPanel(new GridLayout(9, 9));
		for (int i = 0; i < cell.length; i++)
			for (int j = 0; j < cell[i].length; j++) {
				cell[i][j] = new Cell();
				jpCenter.add(cell[i][j]);
			}
		JPanel jpSouth = new JPanel();
		JButton jbtFind = new JButton("Find Path");
		JButton jbtClear = new JButton("Clear Path");
		jpSouth.add(jbtFind);
		jpSouth.add(jbtClear);

		this.getContentPane().add(jpCenter);
		this.getContentPane().add(jpSouth, BorderLayout.SOUTH);

		cell[1][1].markIt(true);
		cell[1][2].markIt(true);
		cell[1][3].markIt(true);
		cell[1][4].markIt(true);
		cell[1][5].markIt(true);
		cell[1][7].markIt(true);
		cell[1][8].markIt(true);
		cell[2][5].markIt(true);
		cell[2][6].markIt(true);
		cell[2][8].markIt(true);
		cell[4][7].markIt(true);
		cell[4][6].markIt(true);
		cell[7][1].markIt(true);
		cell[7][2].markIt(true);
		cell[6][2].markIt(true);
		cell[6][3].markIt(true);
		cell[5][2].markIt(true);
		cell[5][3].markIt(true);
		cell[4][2].markIt(true);
		cell[4][3].markIt(true);
		cell[3][2].markIt(true);
		cell[3][3].markIt(true);
		cell[8][1].markIt(true);
		cell[8][2].markIt(true);
		cell[8][2].markIt(true);
		cell[8][3].markIt(true);
		cell[8][4].markIt(true);
		cell[8][5].markIt(true);
		cell[7][4].markIt(true);
		cell[7][5].markIt(true);
		cell[5][8].markIt(true);
		cell[5][7].markIt(true);
		cell[5][6].markIt(true);
		cell[5][5].markIt(true);

		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < cell.length; i++)
					for (int j = 0; j < cell[i].length; j++)
						cell[i][j].pathIt(false);
			}
		});
		jbtFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clear();
				cell[0][0].pathIt(true);
				if (!move(0, 0))
					cell[0][0].pathIt(false);
				if (list.size() > 0) {
					int shortestIndex = 0;
					int shortestLength = ((int[][]) list.get(0)).length;
					for (int i = 1; i < list.size(); i++) {
						if (((int[][]) list.get(i)).length < shortestLength) {
							shortestIndex = i;
							shortestLength = ((int[][]) list.get(i)).length;
						}
					}
					int[][] path = (int[][]) list.get(shortestIndex);
					for (int i = 0; i < path.length; i++) {
						cell[path[i][0]][path[i][1]].pathIt(true);
					}

				}
			}

			private boolean move(int r, int c) {
				if (r == cell.length - 1
						&& c == cell[cell.length - 1].length - 1) {
					savePath();
					return false;
				}

				if (r - 1 >= 0 && !cell[r - 1][c].isMarked
						&& !cell[r - 1][c].isPath && !isSurrounding(r - 1, c)) {
					cell[r - 1][c].pathIt(true);
					if (move(r - 1, c)) {
						cell[r - 1][c].repaint();
						return true;
					} else
						cell[r - 1][c].pathIt(false);
				}

				if (r + 1 < cell.length && !cell[r + 1][c].isMarked
						&& !cell[r + 1][c].isPath && !isSurrounding(r + 1, c)) {
					cell[r + 1][c].pathIt(true);
					if (move(r + 1, c)) {
						cell[r + 1][c].repaint();
						return true;
					} else
						cell[r + 1][c].pathIt(false);
				}

				if (c + 1 < cell[0].length && !cell[r][c + 1].isMarked
						&& !cell[r][c + 1].isPath && !isSurrounding(r, c + 1)) {
					cell[r][c + 1].pathIt(true);
					if (move(r, c + 1)) {
						cell[r][c + 1].repaint();
						return true;
					} else
						cell[r][c + 1].pathIt(false);
				}

				if (c - 1 >= 0 && !cell[r][c - 1].isMarked
						&& !cell[r][c - 1].isPath && !isSurrounding(r, c - 1)) {
					cell[r][c - 1].pathIt(true);
					if (move(r, c - 1)) {
						cell[r][c - 1].repaint();
						return true;
					} else
						cell[r][c - 1].pathIt(false);
				}
				return false;
			}

			private boolean isSurrounding(int r, int c) {
				int count = 0;

				if (r - 1 >= 0 && cell[r - 1][c].isPath) {
					count++;
				}

				if (r + 1 < cell.length && cell[r + 1][c].isPath) {
					count++;
				}

				if (c + 1 < cell[0].length && cell[r][c + 1].isPath) {
					count++;
				}

				if (c - 1 >= 0 && cell[r][c - 1].isPath) {
					count++;
				}
				if (count > 1)
					return true;
				else
					return false;
			}

			private void savePath() {
				int[][] path = new int[1][2];
				for (int i = 0; i < cell.length; i++)
					for (int j = 0; j < cell[i].length; j++)
						if (cell[i][j].isPath) {
							if (i == cell.length - 1
									&& j == cell[cell.length - 1].length - 1) {
								path[path.length - 1][0] = i;
								path[path.length - 1][1] = j;
							} else {
								int[][] temp = path;
								path = new int[path.length + 1][2];
								System.arraycopy(temp, 0, path, 0, temp.length);
								path[path.length - 2][0] = i;
								path[path.length - 2][1] = j;
							}
						}
				list.add(path);
			}
		});
	}

	class Cell extends JPanel {
		private static final long serialVersionUID = 1L;
		boolean isMarked;
		boolean isPath;
		boolean ClickEnable = true;

		public Cell() {
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(50, 50));

			this.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (ClickEnable) {
						markIt(isMarked ? false : true);
						for (int i = 0; i < cell.length; i++)
							for (int j = 0; j < cell[i].length; j++)
								cell[i][j].pathIt(false);
					}
				}

			});
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (isMarked) {
				g.setColor(new Color(176, 138, 74));
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.black);
				g.drawRect(0, 0, getWidth(), getHeight());
				g.drawRect(1, 1, getWidth() - 1, getHeight() - 1);
				g.drawLine(0, 0, getWidth(), getHeight());
				g.drawLine(0, getHeight(), getWidth(), 0);
			} else if (isPath) {
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}

		public void markIt(boolean b) {
			isMarked = b;
			if (isPath == true)
				isPath = false;
			repaint();
		}

		public void pathIt(boolean b) {
			isPath = b;
			if (b == true) {
				isMarked = false;
			}
			repaint();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Exercise 26");
		frame.add(new Maze());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
