/*This program uses Weighted Graphs technology.
 *The program lets the users create a weighted graph dynamically. 
 *The user can create a vertex by entering its name and location and
 *also user can create an edge to connect two vertices.
 *The user may specify two vertices and let the program display their shortest path
 *in blue.
 *
 *
 *More about Graphs
 *Graphs play an important role in modeling real-world problems. For example, the problem to
 *find the shortest distance between two cities can be modeled using a graph, where the vertices
 *represent cities and the edges represent the roads and distances between two adjacent cities. 
 *The problem of finding the shortest distance between two cities is
 *reduced to finding a shortest path between two vertices in a graph.
 *The study of graph problems is known as graph theory. Graph theory was founded by
 *Leonard Euler in 1736, when he introduced graph terminology to solve the famous Seven
 *Bridges of Königsberg problem. The city of Königsberg, Prussia (now Kaliningrad, Russia)
 *was divided by the Pregel River. There were two islands on the river. The city and islands
 *were connected by seven bridges. The question is, can one take a
 *walk, cross each bridge exactly once, and return to the starting point? Euler proved that it is
 *not possible.
 *To establish a proof, Euler first abstracted the Königsberg city map by eliminating all
 *streets, producing the sketch. Second, he replaced each land mass
 *with a dot, called a vertex or a node, and each bridge with a line, called an edge, as shown in
 *This structure with vertices and edges is called a graph.
 *Looking at the graph, we ask whether there is a path starting from any vertex, traversing all
 *edges exactly once, and returning to the starting vertex. Euler proved that for such path to
 *exist, each vertex must have an even number of edges. Therefore, the Seven Bridges of
 *Königsberg problem has no solution.
 *Graph problems are often solved using algorithms. Graph algorithms have many applications
 *in various areas, such as in computer science, mathematics, biology, engineering, economics,
 *genetics, and social sciences.
 */

package mypackage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class DynamicGraphs extends JApplet {
	private static final long serialVersionUID = 1L;
	GraphView graphView;
	JTextField jtfName = new JTextField();
	JTextField jtfWeight = new JTextField();
	JTextField jtfX = new JTextField();
	JTextField jtfY = new JTextField();
	JTextField jtfU = new JTextField();
	JTextField jtfV = new JTextField();
	JTextField jtfStarting = new JTextField();
	JTextField jtfEnding = new JTextField();
	private WeightedGraph<City> graph = new WeightedGraph<City>(
			new java.util.ArrayList<WeightedEdge>(), 0);

	public DynamicGraphs() {

		JPanel jpSouth = new JPanel(new GridLayout(1, 3));

		JPanel jp1 = new JPanel(new GridLayout(4, 2));
		jp1.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Add a new vertex"));
		jp1.add(new JLabel("Vertex name: ", JLabel.LEFT));
		jp1.add(jtfName);
		jp1.add(new JLabel("x-coordinates: ", JLabel.LEFT));
		jp1.add(jtfX);
		jp1.add(new JLabel("y-coordinates: ", JLabel.LEFT));
		jp1.add(jtfY);
		jp1.add(new JPanel());
		JButton jbtAddVertex = new JButton("Add Vertex");
		jp1.add(jbtAddVertex);
		jpSouth.add(jp1);

		JPanel jp2 = new JPanel(new GridLayout(4, 2));
		jp2.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Add a new edge"));
		jp2.add(new JLabel("Vertex u (name): ", JLabel.LEFT));
		jp2.add(jtfU);
		jp2.add(new JLabel("Vertex v (name): ", JLabel.LEFT));
		jp2.add(jtfV);
		jp2.add(new JLabel("Weight (int): ", JLabel.LEFT));
		jp2.add(jtfWeight);
		jp2.add(new JPanel());
		JButton jbtAddEdge = new JButton("Add Edge");
		jp2.add(jbtAddEdge);
		jpSouth.add(jp2);

		JPanel jp3 = new JPanel(new GridLayout(3, 2));
		jp3.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1),
				"Find a shortest path"));
		jp3.add(new JLabel("Starting vertex: ", JLabel.LEFT));
		jp3.add(jtfStarting);
		jp3.add(new JLabel("Ending vertex: ", JLabel.LEFT));
		jp3.add(jtfEnding);
		jp3.add(new JPanel());
		JButton jbtShortestPath = new JButton("Shortest Path");
		jp3.add(jbtShortestPath);
		jpSouth.add(jp3);

		add(jpSouth, BorderLayout.SOUTH);
		add(graphView = new GraphView(graph));

		jbtAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = jtfName.getText();
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(name)) {
						JOptionPane.showMessageDialog(null,
								"Vertex already exist");
						return;
					}
				}
				int x = 0;
				int y = 0;
				try {
					x = Integer.parseInt(jtfX.getText());
					y = Integer.parseInt(jtfY.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				City city = new City(name, x, y);
				graph.addVertex(city);
				graphView.repaint();
			}
		});
		jbtAddEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String u = jtfU.getText();
				String v = jtfV.getText();
				int weight;
				try {
					weight = Integer.parseInt(jtfWeight.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				int indexU = -1;
				int indexV = -1;
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(u))
						indexU = i;
					else if (vertices.get(i).name.equals(v))
						indexV = i;
					if (indexU != -1 && indexV != -1)
						break;
				}
				if (indexU == -1 || indexV == -1) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				graph.addEdge(indexU, indexV, weight);
				graph.addEdge(indexV, indexU, weight);
				graphView.repaint();
			}
		});
		jbtShortestPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String startString = jtfStarting.getText();
				String endString = jtfEnding.getText();
				int startIndex = -1;
				int endIndex = -1;
				java.util.List<City> vertices = graph.getVertices();
				for (int i = 0; i < vertices.size(); i++) {
					if (vertices.get(i).name.equals(startString))
						startIndex = i;
					else if (vertices.get(i).name.equals(endString))
						endIndex = i;
					if (startIndex != -1 && endIndex != -1)
						break;
				}
				if (startIndex == -1 || endIndex == -1) {
					JOptionPane.showMessageDialog(null, "Incorrect input!");
					return;
				}
				java.util.List<Integer> path = graph
						.getShortestPath(startIndex).getIndexesPath(endIndex);
				graphView.setPath(path);
				graphView.repaint();
			}
		});
		graphView.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				jtfX.setText(e.getX() + "");
				jtfY.setText(e.getY() + "");
			}
		});
	}

	static class City implements Displayable {
		private int x, y;
		private String name;

		City(String name, int x, int y) {
			this.name = name;
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public String getName() {
			return name;
		}

		public boolean equals(Object o) {
			return name.equals(o);
		}

		public int hashCode() {
			return name.hashCode();
		}
	}

	public class GraphView extends JPanel {
		private static final long serialVersionUID = 1L;
		private java.util.List<Integer> path;
		private WeightedGraph<? extends Displayable> graph;

		public GraphView(WeightedGraph<? extends Displayable> graph) {
			this.graph = graph;
		}

		public void setPath(java.util.List<Integer> path) {
			this.path = path;
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw vertices
			java.util.List<? extends Displayable> vertices = graph
					.getVertices();
			for (int i = 0; i < graph.getSize(); i++) {
				int x = vertices.get(i).getX();
				int y = vertices.get(i).getY();
				String name = vertices.get(i).getName();

				g.fillOval(x - 8, y - 8, 16, 16); // Display a vertex
				g.drawString(name, x - 12, y - 12); // Display the name
			}

			// Draw edges and their weights
			java.util.List<java.util.PriorityQueue<WeightedEdge>> queues = graph
					.getWeightedEdges();
			for (java.util.PriorityQueue<WeightedEdge> queue : queues) {
				for (WeightedEdge edge : queue) {
					int x1 = graph.getVertex(edge.u).getX();
					int y1 = graph.getVertex(edge.u).getY();
					int x2 = graph.getVertex(edge.v).getX();
					int y2 = graph.getVertex(edge.v).getY();
					g.drawLine(x1, y1, x2, y2); // Draw an edge for (i, v)

					int weightX = (x1 + x2) / 2 + 3;
					int weightY = (y1 + y2) / 2 - 5;
					g.drawString("" + edge.weight, weightX, weightY);
				}
			}
			if (path != null) {
				g.setColor(Color.GREEN);
				for (int i = 1; i < path.size(); i++) {
					int u = path.get(i - 1);
					int v = path.get(i);
					int x1 = graph.getVertex(u).getX();
					int y1 = graph.getVertex(u).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Weighted Graph");
		DynamicGraphs applet = new DynamicGraphs();
		frame.add(applet);
		applet.init();
		applet.start();

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 550);
		frame.setVisible(true);
	}
}








