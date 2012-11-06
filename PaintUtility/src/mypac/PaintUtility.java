package mypac;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class PaintUtility extends JFrame {
	private static final long serialVersionUID = 1L;
	FigureIcon figureOval = new FigureIcon(FigureIcon.OVAL);
	FigureIcon figureRectange = new FigureIcon(FigureIcon.RECTANGLE);
	FigureIcon figureLine = new FigureIcon(FigureIcon.LINE);
	FigureIcon figureLetter = new FigureIcon(FigureIcon.LETTER);

	FigurePanel figurePanel = new FigurePanel();
	Figure current = new Figure(Figure.LINE);
	Point p1 = new Point();
	Point p2 = new Point();

	public PaintUtility() {
		JToolBar jtb = new JToolBar(JToolBar.HORIZONTAL);
		jtb.setBorder(null);
		jtb.setBackground(FigureIcon.unSelectedColor);
		jtb.setBorder(new LineBorder(FigureIcon.unSelectedColor, 5));
		jtb.add(figureLine);
		jtb.add(figureRectange);
		jtb.add(figureOval);
		jtb.add(figureLetter);
		jtb.add(new JPanel());

		figureLine.setSelected(true);
		figurePanel.setFocusable(true);
		figurePanel.setBackground(Color.WHITE);
		add(jtb, BorderLayout.NORTH);
		add(figurePanel);

		figurePanel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (current.type != Figure.WORD) {
					figurePanel.addFigure(current.getClone());
					current = new Figure(current.type);
					figurePanel.repaint();
				} else {

				}
			}

			public void mousePressed(MouseEvent e) {
				if (current.type == Figure.LINE) {
					current.setCoordinated(e.getX(), e.getY(), e.getX(), e.getY());
					figurePanel.repaint();
				} else if (current.type != Figure.WORD) {
					p1.x = e.getX();
					p1.y = e.getY();
					p2.x = e.getX();
					p2.y = e.getY();
					current.setCoordinated(e.getX(), e.getY(), 0, 0);
				} else {
					figurePanel.addFigure(current.getClone());
					current.x = e.getX();
					current.y = e.getY();
					current.s = "";
				}
			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}
		});
		figurePanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (current.type == Figure.LINE) {
					current.width_X = e.getX();
					current.height_Y = e.getY();
					figurePanel.repaint();
				} else if (current.type != Figure.WORD) {
					p2.x = e.getX();
					p2.y = e.getY();

					if (p1.x < p2.x) {
						current.x = p1.x;
						current.width_X = p2.x - p1.x;
					} else {
						current.x = p2.x;
						current.width_X = p1.x - p2.x;
					}

					if (p1.y < p2.y) {
						current.y = p1.y;
						current.height_Y = p2.y - p1.y;
					} else {
						current.y = p2.y;
						current.height_Y = p1.y - p2.y;
					}
					figurePanel.repaint();
				}
			}
		});
		figurePanel.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (current.type == Figure.WORD) {
					if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
						if (!current.s.equals("")) {
							current.s = current.s.substring(0, current.s.length() - 1);
						}
					} else
						current.s += e.getKeyChar();
					figurePanel.repaint();

				}
			}
		});

		figureOval.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (current.type == Figure.WORD)
					figurePanel.addFigure(current.getClone());
				figureOval.setSelected(true);
				figureLetter.setSelected(false);
				figureLine.setSelected(false);
				figureRectange.setSelected(false);
				current = new Figure(Figure.OVAL);
			}
		});
		figureRectange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (current.type == Figure.WORD)
					figurePanel.addFigure(current.getClone());
				figureOval.setSelected(false);
				figureLetter.setSelected(false);
				figureLine.setSelected(false);
				figureRectange.setSelected(true);
				current = new Figure(Figure.RECTANGLE);
			}
		});
		figureLine.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (current.type == Figure.WORD)
					figurePanel.addFigure(current.getClone());
				figureOval.setSelected(false);
				figureLetter.setSelected(false);
				figureLine.setSelected(true);
				figureRectange.setSelected(false);
				current = new Figure(Figure.LINE);
			}
		});
		figureLetter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (current.type == Figure.WORD)
					figurePanel.addFigure(current.getClone());
				figureOval.setSelected(false);
				figureLetter.setSelected(true);
				figureLine.setSelected(false);
				figureRectange.setSelected(false);
				current = new Figure(Figure.WORD);
			}
		});
	}

	class FigurePanel extends JPanel {
		java.util.HashSet<Figure> set = new HashSet<Figure>();

		public void addFigure(Figure figure) {
			set.add(figure);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (current != null) {
				if (current.type == Figure.LINE) {
					g.drawLine(current.x, current.y, current.width_X, current.height_Y);
				} else if (current.type == Figure.OVAL) {
					g.drawOval(current.x, current.y, current.width_X, current.height_Y);
				} else if (current.type == Figure.RECTANGLE) {
					g.drawRect(current.x, current.y, current.width_X, current.height_Y);
				} else if (current.type == Figure.WORD) {
					g.drawString(current.s, current.x, current.y);
				}
			}
			for (Figure figure : set) {
				if (figure.type == Figure.LINE) {
					g.drawLine(figure.x, figure.y, figure.width_X, figure.height_Y);
				} else if (figure.type == Figure.OVAL) {
					g.drawOval(figure.x, figure.y, figure.width_X, figure.height_Y);
				} else if (figure.type == Figure.RECTANGLE) {
					g.drawRect(figure.x, figure.y, figure.width_X, figure.height_Y);
				} else if (figure.type == Figure.WORD) {
					g.drawString(figure.s, figure.x, figure.y);
				}
			}
		}
	}

	class Figure {
		public static final int LINE = 0;
		public static final int RECTANGLE = 1;
		public static final int OVAL = 2;
		public static final int WORD = 3;
		private int type;
		public String s = "";
		public int x;
		public int y;
		public int width_X;
		public int height_Y;

		public Figure(int type) {
			this.type = type;
		}

		public void setCoordinated(int x, int y, int width_X, int height_Y) {
			this.x = x;
			this.y = y;
			this.height_Y = height_Y;
			this.width_X = width_X;
		}

		public Figure getClone() {
			Figure figure = new Figure(type);
			figure.setCoordinated(x, y, width_X, height_Y);
			figure.s = s;
			return figure;
		}
	}

	static class FigureIcon extends JPanel {
		public static final int LINE = 0;
		public static final int RECTANGLE = 1;
		public static final int OVAL = 2;
		public static final int LETTER = 3;
		private int type;
		private boolean selected = false;
		private static Color selectedColor = Color.GRAY;
		private static Color unSelectedColor = new Color(150, 150, 150);

		public FigureIcon(int type) {
			this.type = type;
			unSelectedColor = getBackground();
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			if (selected)
				setBackground(selectedColor);
			else
				setBackground(unSelectedColor);
			repaint();
		}

		public boolean isSelected() {
			return selected;
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			// Get the appropriate size for the figure
			int width = getWidth();
			int height = getHeight();

			switch (type) {
			case LINE: // Display two cross lines
				g.drawLine(width - 10, 10, 10, height - 10);
				break;
			case RECTANGLE: // Display a rectangle
				g.drawRect((int) (0.2 * width), (int) (0.2 * height), (int) (0.6 * width), (int) (0.6 * height));
				break;
			case OVAL: // Display an oval
				g.drawOval((int) (0.2 * width), (int) (0.2 * height), (int) (0.6 * width), (int) (0.6 * height));
				break;
			case LETTER:
				Font font = g.getFont();
				g.setFont(new Font(font.getName(), Font.BOLD, 30));
				FontMetrics fm = g.getFontMetrics();
				g.drawString("A", width / 2 - fm.stringWidth("A") / 2, height / 2 + fm.getAscent() / 2 - 4);
			}
		}

		public Dimension getMaximumSize() {
			return new Dimension(50, 50);
		}

		public Dimension getPreferredSize() {
			return new Dimension(50, 50);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new PaintUtility();
		frame.setTitle("E04_03");
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
