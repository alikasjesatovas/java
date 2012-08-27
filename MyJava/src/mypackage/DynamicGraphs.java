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

import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

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

interface Displayable {
	public int getX(); // Get x-coordinate of the vertex

	public int getY(); // Get x-coordinate of the vertex

	public String getName(); // Get display name of the vertex
}

class WeightedGraph<V> extends AbstractGraph<V> {
	private static final long serialVersionUID = 1L;
	// Priority adjacency lists
	private List<PriorityQueue<WeightedEdge>> queues;


	/** Construct a WeightedGraph from vertices 0, 1, and edge array */
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		super((List) edges, numberOfVertices);
		createQueues(edges, numberOfVertices);
	}


	/** Create priority adjacency lists from edge lists */
	private void createQueues(List<WeightedEdge> edges, int numberOfVertices) {
		queues = new ArrayList<PriorityQueue<WeightedEdge>>();
		for (int i = 0; i < numberOfVertices; i++) {
			queues.add(new PriorityQueue<WeightedEdge>()); // Create a queue
		}

		for (WeightedEdge edge : edges) {
			queues.get(edge.u).offer(edge); // Insert an edge into the queue
		}
	}

	/** Clone an array of queues */
	private List<PriorityQueue<WeightedEdge>> deepClone(
			List<PriorityQueue<WeightedEdge>> queues) {
		List<PriorityQueue<WeightedEdge>> copiedQueues = new ArrayList<PriorityQueue<WeightedEdge>>();

		for (int i = 0; i < queues.size(); i++) {
			copiedQueues.add(new PriorityQueue<WeightedEdge>());
			for (WeightedEdge e : queues.get(i)) {
				copiedQueues.get(i).add(e);
			}
		}

		return copiedQueues;
	}

	/** MST is an inner class in WeightedGraph */
	public class MST extends Tree {
		private static final long serialVersionUID = 1L;
		private int totalWeight; // Total weight of all edges in the tree

		public MST(int root, int[] parent, List<Integer> searchOrder,
				int totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
		}

		public int getTotalWeight() {
			return totalWeight;
		}
	}

	/** Find single source shortest paths */
	public ShortestPathTree getShortestPath(int sourceIndex) {
		// T stores the vertices whose path found so far
		List<Integer> T = new ArrayList<Integer>();
		// T initially contains the sourceVertex;
		T.add(sourceIndex);

		// vertices is defined in AbstractGraph
		int numberOfVertices = vertices.size();

		// parent[v] stores the previous vertex of v in the path
		int[] parent = new int[numberOfVertices];
		parent[sourceIndex] = -1; // The parent of source is set to -1

		// costs[v] stores the cost of the path from v to the source
		int[] costs = new int[numberOfVertices];
		for (int i = 0; i < costs.length; i++) {
			costs[i] = Integer.MAX_VALUE; // Initial cost set to infinity
		}
		costs[sourceIndex] = 0; // Cost of source is 0

		// Get a copy of queues
		List<PriorityQueue<WeightedEdge>> queues = deepClone(this.queues);

		// Expand verticesFound
		while (T.size() < numberOfVertices) {
			int v = -1; // Vertex to be determined
			int smallestCost = Integer.MAX_VALUE; // Set to infinity
			for (int u : T) {
				while (!queues.get(u).isEmpty()
						&& T.contains(queues.get(u).peek().v)) {
					queues.get(u).remove(); // Remove the vertex in
											// verticesFound
				}

				if (queues.get(u).isEmpty()) {
					// All vertices adjacent to u are in verticesFound
					continue;
				}

				WeightedEdge e = queues.get(u).peek();
				if (costs[u] + e.weight < smallestCost) {
					v = e.v;
					smallestCost = costs[u] + e.weight;
					// If v is added to the tree, u will be its parent
					parent[v] = u;
				}
			} // End of for
			if (v == -1)
				break;
			T.add(v); // Add a new vertex to T
			costs[v] = smallestCost;
		} // End of while

		// Create a ShortestPathTree
		return new ShortestPathTree(sourceIndex, parent, T, costs);
	}

	/** ShortestPathTree is an inner class in WeightedGraph */
	public class ShortestPathTree extends Tree {
		private static final long serialVersionUID = 1L;
		private int[] costs; // costs[v] is the cost from v to source

		/** Construct a path */
		public ShortestPathTree(int source, int[] parent,
				List<Integer> searchOrder, int[] costs) {
			super(source, parent, searchOrder);
			this.costs = costs;
		}

		/** Return the cost for a path from the root to vertex v */
		public int getCost(int v) {
			return costs[v];
		}

	}

	public List<PriorityQueue<WeightedEdge>> getWeightedEdges() {
		return queues;
	}

	public void addVertex(V vertex) {
		super.addVertex(vertex);
		queues.add(new PriorityQueue<WeightedEdge>());
	}

	public void addEdge(int u, int v, int weight) {
		super.addEdge(u, v);
		queues.get(u).add(new WeightedEdge(u, v, weight));
		queues.get(v).add(new WeightedEdge(v, u, weight));
	}
}

abstract class AbstractGraph<V> implements Graph<V> {
	private static final long serialVersionUID = 1L;
	protected List<V> vertices; // Store vertices
	protected List<List<Integer>> neighbors; // Adjacency lists

	/** Construct a graph from edges and vertices stored in arrays */
	protected AbstractGraph(int[][] edges, V[] vertices) {
		this.vertices = new ArrayList<V>();
		for (int i = 0; i < vertices.length; i++)
			this.vertices.add(vertices[i]);

		createAdjacencyLists(edges, vertices.length);
	}

	/** Construct a graph from edges and vertices stored in List */
	protected AbstractGraph(List<Edge> edges, List<V> vertices) {
		this.vertices = vertices;
		createAdjacencyLists(edges, vertices.size());
	}

	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
		vertices = new ArrayList<V>(); // Create vertices
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i))); // vertices is {0, 1, ...}
		}
		createAdjacencyLists(edges, numberOfVertices);
	}

	/** Construct a graph from integer vertices 0, 1, and edge array */
	protected AbstractGraph(int[][] edges, int numberOfVertices) {
		vertices = new ArrayList<V>(); // Create vertices
		for (int i = 0; i < numberOfVertices; i++) {
			vertices.add((V) (new Integer(i))); // vertices is {0, 1, ...}
		}
		createAdjacencyLists(edges, numberOfVertices);
	}

	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
		// Create a linked list
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < edges.length; i++) {
			int u = edges[i][0];
			int v = edges[i][1];
			neighbors.get(u).add(v);
		}
	}

	/** Create adjacency lists for each vertex */
	private void createAdjacencyLists(List<Edge> edges, int numberOfVertices) {
		// Create a linked list
		neighbors = new ArrayList<List<Integer>>();
		for (int i = 0; i < numberOfVertices; i++) {
			neighbors.add(new ArrayList<Integer>());
		}

		for (Edge edge : edges) {
			neighbors.get(edge.u).add(edge.v);
		}
	}

	/** Return the number of vertices in the graph */
	public int getSize() {
		return vertices.size();
	}

	/** Return the vertices in the graph */
	public List<V> getVertices() {
		return vertices;
	}

	/** Return the object for the specified vertex */
	public V getVertex(int index) {
		return vertices.get(index);
	}

	/** Return the index for the specified vertex object */
	public int getIndex(V v) {
		return vertices.indexOf(v);
	}

	/** Return the neighbors of vertex with the specified index */
	public List<Integer> getNeighbors(int index) {
		return neighbors.get(index);
	}

	/** Return the degree for a specified vertex */
	public int getDegree(int v) {
		return neighbors.get(v).size();
	}

	/** Return the adjacency matrix */
	public int[][] getAdjacencyMatrix() {
		int[][] adjacencyMatrix = new int[getSize()][getSize()];

		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = 0; j < neighbors.get(i).size(); j++) {
				int v = neighbors.get(i).get(j);
				adjacencyMatrix[i][v] = 1;
			}
		}

		return adjacencyMatrix;
	}

	/** Print the adjacency matrix */
	public void printAdjacencyMatrix() {
		int[][] adjacencyMatrix = getAdjacencyMatrix();
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[0].length; j++) {
				System.out.print(adjacencyMatrix[i][j] + " ");
			}

			System.out.println();
		}
	}

	/** Print the edges */
	public void printEdges() {
		for (int u = 0; u < neighbors.size(); u++) {
			System.out.print("Vertex " + u + ": ");
			for (int j = 0; j < neighbors.get(u).size(); j++) {
				System.out.print("(" + u + ", " + neighbors.get(u).get(j)
						+ ") ");
			}
			System.out.println();
		}
	}

	/** Edge inner class inside the AbstractGraph class */
	public static class Edge {
		public int u; // Starting vertex of the edge
		public int v; // Ending vertex of the edge

		/** Construct an edge for (u, v) */
		public Edge(int u, int v) {
			this.u = u;
			this.v = v;
		}
	}

	/** Obtain a DFS tree starting from vertex v */
	/** To be discussed in Section 27.6 */
	public Tree dfs(int v) {
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1; // Initialize parent[i] to -1

		// Mark visited vertices
		boolean[] isVisited = new boolean[vertices.size()];

		// Recursively search
		dfs(v, parent, searchOrders, isVisited);

		// Return a search tree
		return new Tree(v, parent, searchOrders);
	}

	/** Recursive method for DFS search */
	private void dfs(int v, int[] parent, List<Integer> searchOrders,
			boolean[] isVisited) {
		// Store the visited vertex
		searchOrders.add(v);
		isVisited[v] = true; // Vertex v visited

		for (int i : neighbors.get(v)) {
			if (!isVisited[i]) {
				parent[i] = v; // The parent of vertex i is v
				dfs(i, parent, searchOrders, isVisited); // Recursive search
			}
		}
	}

	/** Starting bfs search from vertex v */
	/** To be discussed in Section 27.7 */
	public Tree bfs(int v) {
		List<Integer> searchOrders = new ArrayList<Integer>();
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < parent.length; i++)
			parent[i] = -1; // Initialize parent[i] to -1

		java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>(); // list
																					// used
																					// as
																					// a
																					// queue
		boolean[] isVisited = new boolean[vertices.size()];
		queue.offer(v); // Enqueue v
		isVisited[v] = true; // Mark it visited

		while (!queue.isEmpty()) {
			int u = queue.poll(); // Dequeue to u
			searchOrders.add(u); // u searched
			for (int w : neighbors.get(u)) {
				if (!isVisited[w]) {
					queue.offer(w); // Enqueue w
					parent[w] = u; // The parent of w is u
					isVisited[w] = true; // Mark it visited
				}
			}
		}

		return new Tree(v, parent, searchOrders);
	}

	/** Tree inner class inside the AbstractGraph class */
	/** To be discussed in Section 27.5 */
	public class Tree implements Serializable {
		private static final long serialVersionUID = 1L;
		private int root; // The root of the tree
		private int[] parent; // Store the parent of each vertex
		private List<Integer> searchOrders; // Store the search order

		/** Construct a tree with root, parent, and searchOrder */
		public Tree(int root, int[] parent, List<Integer> searchOrders) {
			this.root = root;
			this.parent = parent;
			this.searchOrders = searchOrders;
		}

		/**
		 * Construct a tree with root and parent without a particular order
		 */
		public Tree(int root, int[] parent) {
			this.root = root;
			this.parent = parent;
		}

		/** Return the root of the tree */
		public int getRoot() {
			return root;
		}

		/** Return the parent of vertex v */
		public int getParent(int v) {
			return parent[v];
		}

		/** Return an array representing search order */
		public List<Integer> getSearchOrders() {
			return searchOrders;
		}

		/** Return number of vertices found */
		public int getNumberOfVerticesFound() {
			return searchOrders.size();
		}

		/** Return the path of vertices from a vertex index to the root */
		public List<V> getPath(int index) {
			ArrayList<V> path = new ArrayList<V>();
			if (!searchOrders.contains(index)) {
				return null;
			}
			do {
				path.add(vertices.get(index));
				index = parent[index];
			} while (index != -1);

			return path;
		}

		public List<Integer> getIndexesPath(int index) {
			ArrayList<Integer> path = new ArrayList<Integer>();
			if (!searchOrders.contains(index)) {
				return null;
			}
			do {
				path.add(index);
				index = parent[index];
			} while (index != -1);

			return path;
		}

	
		
	}






	public void addVertex(V vertex) {
		vertices.add(vertex);
		neighbors.add(new ArrayList<Integer>());
	}

	public void addEdge(int u, int v) {
		neighbors.get(u).add(v);
		neighbors.get(v).add(u);
	}
}

class UnweightedGraph<V> extends AbstractGraph<V> {
	private static final long serialVersionUID = 1L;

	/** Construct a graph from edges and vertices stored in arrays */
	public UnweightedGraph(int[][] edges, V[] vertices) {
		super(edges, vertices);
	}

	/** Construct a graph from edges and vertices stored in List */
	public UnweightedGraph(List<Edge> edges, List<V> vertices) {
		super(edges, vertices);
	}

	/** Construct a graph for integer vertices 0, 1, 2 and edge list */
	public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}

	/** Construct a graph from integer vertices 0, 1, and edge array */
	public UnweightedGraph(int[][] edges, int numberOfVertices) {
		super(edges, numberOfVertices);
	}
}

interface Graph<V> extends Serializable {
	/** Return the number of vertices in the graph */
	public int getSize();

	/** Return the vertices in the graph */
	public java.util.List<V> getVertices();

	/** Return the object for the specified vertex index */
	public V getVertex(int index);

	/** Return the index for the specified vertex object */
	public int getIndex(V v);

	/** Return the neighbors of vertex with the specified index */
	public java.util.List<Integer> getNeighbors(int index);

	/** Return the degree for a specified vertex */
	public int getDegree(int v);

	/** Return the adjacency matrix */
	public int[][] getAdjacencyMatrix();

	/** Print the adjacency matrix */
	public void printAdjacencyMatrix();

	/** Print the edges */
	public void printEdges();

	/** Obtain a depth-first search tree */
	public AbstractGraph<V>.Tree dfs(int v);

	/** Obtain a breadth-first search tree */
	public AbstractGraph<V>.Tree bfs(int v);

}

class WeightedEdge extends AbstractGraph.Edge implements
		Comparable<WeightedEdge> {
	public int weight; // The weight on edge (u, v)

	/** Create a weighted edge on (u, v) */
	public WeightedEdge(int u, int v, int weight) {
		super(u, v);
		this.weight = weight;
	}

	/** Compare two edges on weights */
	public int compareTo(WeightedEdge edge) {
		if (weight > edge.weight)
			return 1;
		else if (weight == edge.weight) {
			return 0;
		} else {
			return -1;
		}
	}

	public boolean equals(Object o) {
		WeightedEdge edge = (WeightedEdge) o;
		return (edge.u == u && edge.v == v);
	}
}
