package mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class WeightedGraph<V> extends AbstractGraph<V> {
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