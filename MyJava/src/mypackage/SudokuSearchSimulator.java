/*This program uses multithreading to simulate process of finding sudoku solution. The simulation displays all the search steps.
 *User can specify delay time by entering delay in milliseconds in JTextField provided.
 *User can enter optional sudoku puzzle and by clicking Solve button can find solution for it.
 *This GUI application can work as stand alone or as JApplet*/

package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;

public class SudokuSearchSimulator extends JApplet {
	private static final long serialVersionUID = 1L;
	private Cell[][] cell = new Cell[9][9];
	JButton jbtSolve = new JButton("Solve");
	JButton jbtClear = new JButton("Clear");
	private int[][] num = new int[9][9];
	private int delay = 1000;
	private int[] tester = new int[2];
	private JTextField jtfDelay = new JTextField("1000", 10);

	public SudokuSearchSimulator() {

		Box[] box = new Box[cell.length];
		for (int i = 0; i < box.length; i++) {
			for (int j = 0; j < cell[i].length; j++)
				cell[i][j] = new Cell();
			box[i] = new Box(cell[i]);
		}

		JPanel jpIner = new JPanel(new GridLayout(3, 3));
		jpIner.setBorder(new EmptyBorder(0, 0, 1, 1));
		for (int i = 0; i < box.length; i++) {
			jpIner.add(box[i]);
		}

		JPanel jpOuter = new JPanel(new BorderLayout());
		jpOuter.setBorder(new LineBorder(Color.BLACK, 2));
		jpOuter.add(jpIner);

		JPanel jpButtons = new JPanel();
		jpButtons.setBorder(new EmptyBorder(3, 3, 3, 3));
		jpButtons.add(jbtSolve);
		jpButtons.add(jbtClear);

		JPanel jpNorth = new JPanel();
		jpNorth.add(new JLabel("Enter Delay in milliseconts and press ENTER"));
		jpNorth.add(jtfDelay);
		
		add(jpOuter);
		add(jpButtons, BorderLayout.SOUTH);
		add(jpNorth, BorderLayout.NORTH);

		jbtSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getNumbers();
				highLight();
				if (!isValid(num))
					JOptionPane.showMessageDialog(null, "Invalid Input");
				else
					try {
						jbtSolve.setEnabled(false);
						jbtClear.setEnabled(false);
						search();
					} catch (Exception e) {}
			}
		});
		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < cell.length; i++)
					for (int j = 0; j < cell[i].length; j++) {
						num[i][j] = 0;
						cell[i][j].setText("");
						cell[i][j].setBackground(Color.WHITE);
					}
			}
		});
		jtfDelay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				delay = Integer.parseInt(jtfDelay.getText());
				
			}
		});
	}

	private void getNumbers() {
		int[] rowA = { 0, 0 };
		int[] rowB = { 1, 0 };
		int[] rowC = { 2, 0 };

		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[i].length; j++) {
				int n = 0;
				if (!cell[i][j].getText().equals(""))
					n = Integer.parseInt(cell[i][j].getText());
				if (j == 0 || j == 1 || j == 2) {
					num[rowA[0]][rowA[1]] = n;
					rowA[1]++;
					if (rowA[1] > 8) {
						rowA[0] = rowA[0] + 3;
						rowA[1] = 0;
					}
				} else if (j == 3 || j == 4 || j == 5) {
					num[rowB[0]][rowB[1]] = n;
					rowB[1]++;
					if (rowB[1] > 8) {
						rowB[0] = rowB[0] + 3;
						rowB[1] = 0;
					}
				} else if (j == 6 || j == 7 || j == 8) {
					num[rowC[0]][rowC[1]] = n;
					rowC[1]++;
					if (rowC[1] > 8) {
						rowC[0] = rowC[0] + 3;
						rowC[1] = 0;
					}
				}
			}
		}
	}

	private void highLight() {
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[i].length; j++) {
				if (!cell[i][j].getText().equals(""))
					cell[i][j].setBackground(Color.GRAY);
				else
					cell[i][j].setBackground(Color.WHITE);
			}
		}
	}

	public static int[][] getFreeCellList(int[][] num) {
		// Determine the number of free cells
		int numberOfFreeCells = 0;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (num[i][j] == 0)
					numberOfFreeCells++;

		// Store free cell positions into freeCellList
		int[][] freeCellList = new int[numberOfFreeCells][2];
		int count = 0;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (num[i][j] == 0) {
					freeCellList[count][0] = i;
					freeCellList[count++][1] = j;
				}

		return freeCellList;
	}

	/** Print the values in the num */
	public void printGrid() {
		setNumbers(num);
	}

	private void setNumbers(int[][] grid) {
		int[] rowA = { 0, 0 };
		int[] rowB = { 1, 0 };
		int[] rowC = { 2, 0 };

		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[i].length; j++) {
				if (j == 0 || j == 1 || j == 2) {
					if (rowA[0] == tester[0] && rowA[1] == tester[1]) {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.PLAIN, cell[i][j].getFont().getSize()));
					} else {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.BOLD, cell[i][j].getFont().getSize()));
					}
					if (grid[rowA[0]][rowA[1]] != 0)
						cell[i][j].setText("" + grid[rowA[0]][rowA[1]]);
					else
						cell[i][j].setText("");
					rowA[1]++;
					if (rowA[1] > 8) {
						rowA[0] = rowA[0] + 3;
						rowA[1] = 0;
					}
				} else if (j == 3 || j == 4 || j == 5) {
					if (rowB[0] == tester[0] && rowB[1] == tester[1]) {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.PLAIN, cell[i][j].getFont().getSize()));
					} else {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.BOLD, cell[i][j].getFont().getSize()));
					}

					if (grid[rowB[0]][rowB[1]] != 0)
						cell[i][j].setText("" + grid[rowB[0]][rowB[1]]);
					else
						cell[i][j].setText("");
					rowB[1]++;
					if (rowB[1] > 8) {
						rowB[0] = rowB[0] + 3;
						rowB[1] = 0;
					}
				} else if (j == 6 || j == 7 || j == 8) {
					if (rowC[0] == tester[0] && rowC[1] == tester[1]) {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.PLAIN, cell[i][j].getFont().getSize()));
					} else {
						cell[i][j].setFont(new Font(cell[i][j].getFont().getName(), Font.BOLD, cell[i][j].getFont().getSize()));
					}

					if (grid[rowC[0]][rowC[1]] != 0)
						cell[i][j].setText("" + grid[rowC[0]][rowC[1]]);
					else
						cell[i][j].setText("");
					rowC[1]++;
					if (rowC[1] > 8) {
						rowC[0] = rowC[0] + 3;
						rowC[1] = 0;
					}
				}
			}
		}
	}

	/** Search for a solution */
	public void search() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int[][] freeCellList = getFreeCellList(num); // Free cells
				if (freeCellList.length == 0) {
					jbtSolve.setEnabled(true);
					jbtClear.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Solution has been found!");
					return; // "No free cells");
				}

				int k = 0; // Start from the first free cell
				while (true) {
					int i = freeCellList[k][0];
					int j = freeCellList[k][1];
					tester = new int[] { i, j };
					if (num[i][j] == 0) {
						num[i][j] = 1; // Fill the free cell with number 1
						printGrid();
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
						}
					}

					if (isValid(i, j)) {
						if (k + 1 == freeCellList.length) { // No more free
															// cells
							printGrid();
							jbtSolve.setEnabled(true);
							jbtClear.setEnabled(true);
							JOptionPane.showMessageDialog(null, "Solution has been found!");
							return; // A solution is found
						} else { // Move to the next free cell
							k++;
						}
					} else if (num[i][j] < 9) {
						// Fill the free cell with the next possible value
						num[i][j] = num[i][j] + 1;
					} else { // free cell num[i][j] is 9, backtrack
						while (num[i][j] == 9) {
							if (k == 0) {
								jbtSolve.setEnabled(true);
								jbtClear.setEnabled(true);
								JOptionPane.showMessageDialog(null, "No Solution has been found!");
								return; // No possible value
							}
							num[i][j] = 0; // Reset to free cell
							k--; // Backtrack to the preceding free cell
							i = freeCellList[k][0];
							j = freeCellList[k][1];
							tester = new int[] { i, j };
							printGrid();
							try {
								Thread.sleep(delay);
							} catch (InterruptedException e) {
							}
						}

						// Fill the free cell with the next possible value,
						// search continues from this free cell at k
						num[i][j] = num[i][j] + 1;
						printGrid();
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		});
		thread.start();
	}

	/** Check whether num[i][j] is valid in the num */
	public boolean isValid(int i, int j) {
		// Check whether num[i][j] is valid at the i's row
		for (int column = 0; column < 9; column++)
			if (column != j && num[i][column] == num[i][j])
				return false;

		// Check whether num[i][j] is valid at the j's column
		for (int row = 0; row < 9; row++)
			if (row != i && num[row][j] == num[i][j])
				return false;

		// Check whether num[i][j] is valid in the 3 by 3 box
		for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++)
			for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++)
				if (row != i && col != j && num[row][col] == num[i][j])
					return false;

		return true; // The current value at num[i][j] is valid
	}

	/** Check whether the fixed cells are valid in the num */
	public boolean isValid(int[][] num) {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (num[i][j] < 0 || num[i][j] > 9 || (num[i][j] != 0 && !isValid(i, j)))
					return false;

		return true; // The fixed cells are valid
	}

	class Listener extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (c < 49 || c > 57 || !((Cell) (e.getSource())).getText().equals(""))
				e.setKeyChar((char) 0);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("E29_16");
		frame.add(new SudokuSearchSimulator());
		frame.setSize(400, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	class Cell extends JTextField {
		private static final long serialVersionUID = 1L;

		public Cell() {
			this.addKeyListener(new Listener());
			this.addMouseListener(new ClickListener());
			this.setBorder(new LineBorder(Color.GRAY, 1));
			this.setHorizontalAlignment(JTextField.CENTER);
		}
	}

	class Box extends JPanel {
		private static final long serialVersionUID = 1L;

		public Box(Cell[] cell) {
			JPanel panel = new JPanel(new GridLayout(3, 3, 1, 1));
			panel.setBorder(new EmptyBorder(0, 0, 1, 1));
			for (int i = 0; i < cell.length; i++) {
				panel.add(cell[i]);
			}
			this.setLayout(new BorderLayout());
			this.setBorder(new LineBorder(Color.BLACK, 2));
			add(panel);
		}
	}
	
	public class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JTextField field = (JTextField)e.getSource();
			field.setText("");
		}
		
	}
}
