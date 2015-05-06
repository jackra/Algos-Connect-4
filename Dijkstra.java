import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Dijkstra {

	public static void computePaths(Vertex source) {
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// Visit each edge exiting u

			for (Edge e : u.adjacencies) {
				Vertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU;
					v.previous = u;
					vertexQueue.add(v);
				}

			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target) {
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}

	class Heap {

		private ArrayList<Vertex> sortedQ;

		public Heap(ArrayList<Vertex> ar) {
			this.sortedQ = ar;
			build(this.sortedQ, sortedQ.size());
		}

		private void build(ArrayList<Vertex> unsortedQ, int n) {

			for (int i = (n / 2) - 1; i >= 0; i--) {
				minHeapify(unsortedQ, i, n);
			}
		}

		private void minHeapify(ArrayList<Vertex> unsortedQ, int i, int n) {
			int l, r;
			l = left(i);
			r = right(i);
			int smallest;
			if (l <= n - 1) {
				if (getDist(unsortedQ.get(l)) < getDist(unsortedQ.get(i))) {
					smallest = l;
					unsortedQ.get(l).heap_index = i;
					unsortedQ.get(i).heap_index = l;
				} else {
					unsortedQ.get(l).heap_index = l;
					unsortedQ.get(i).heap_index = i;
					smallest = i;
				}

			} else {
				unsortedQ.get(i).heap_index = i;
				smallest = i;
			}
			if (r <= n - 1) {
				if (getDist(unsortedQ.get(r)) < getDist(unsortedQ.get(smallest))) {
					unsortedQ.get(r).heap_index = i;
					unsortedQ.get(i).heap_index = r;
					smallest = r;
				} else {
					unsortedQ.get(r).heap_index = r;
					unsortedQ.get(i).heap_index = i;
				}
			}

			if (smallest != i) {
				Vertex temp = unsortedQ.get(i);
				unsortedQ.set(i, unsortedQ.get(smallest));
				unsortedQ.set(smallest, temp);
				minHeapify(unsortedQ, smallest, n);
			}
		}

		public Vertex extractMin(ArrayList<Vertex> sortedQ, int n) {

			if (n < 1) {
				System.out.println("Min-Heap undeflow!");
				return null;
			}
			Vertex min = sortedQ.get(0);
			sortedQ.set(0, sortedQ.get(n - 1));
			sortedQ.remove(n - 1);
			n = n - 1;
			if (n > 0) {
				minHeapify(sortedQ, 0, n);
			}

			return min;
		}

		public void decreaseKey(int i, double key) {
			if (sortedQ.get(i).minDistance < key) {
				return;
			}
			sortedQ.get(i).minDistance = key;
			while (i > 0 && sortedQ.get(parent(i)).minDistance > sortedQ.get(i).minDistance) {
				sortedQ.get(parent(i)).heap_index = i;
				sortedQ.get(i).heap_index = parent(i);

				Vertex temp = sortedQ.get(i);
				sortedQ.set(i, sortedQ.get(parent(i)));
				sortedQ.set(parent(i), temp);
				i = parent(i);
			}
		}

		private int parent(int i) {
			return (i - 1) / 2;
		}

		private int left(int i) {
			return 2 * i + 1;
		}

		private int right(int i) {
			return 2 * i + 2;
		}

		public boolean isEmpty() {
			return sortedQ.isEmpty();
		}

		public Vertex extractMin() {
			return extractMin(sortedQ, sortedQ.size());
		}

		private double getDist(Vertex v) {
			return v.minDistance;
		}
	}

	public static void main(String args[]) {
		Set mySet = new HashSet();
		TreeMap<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("C:/DELL/graph2.txt"));
			String line;
			while ((line = in.readLine()) != null) {
				String[] parts = line.split(" ");
				String vFrom = parts[0];
				String vTo = parts[1];
				Vertex v = new Vertex(vFrom);
				if (!mySet.contains(v.name)) {
					vertexMap.put(v.name, v);
					mySet.add(v.name);
				}
				Vertex v1 = new Vertex(vTo);
				if (!mySet.contains(v1.name)) {
					vertexMap.put(v1.name, v1);
					mySet.add(v1.name);
				}
				// System.out.println("xx"+mySet);
				double weight = Double.parseDouble(parts[2]);
				Vertex x = vertexMap.get(v.name);
				if (x != null) {
					x.addEdge(new Edge(vertexMap.get(v1.name), weight));
				}

				vFrom = parts[1];
				vTo = parts[0];
				Vertex v2 = new Vertex(vFrom);
				if (!mySet.contains(v2.name)) {
					vertexMap.put(v2.name, v2);
					mySet.add(v2.name);
				}
				Vertex v3 = new Vertex(vTo);
				if (!mySet.contains(v3.name)) {
					vertexMap.put(v3.name, v3);
					mySet.add(v3.name);
				}
				weight = Double.parseDouble(parts[2]);
				Vertex y = vertexMap.get(v2.name);
				if (y != null) {
					y.addEdge(new Edge(vertexMap.get(v3.name), weight));
				}
			}
		} catch (IOException e) {
			System.out.println("Exception in input file");
			return;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ignore) {
				}
		}
		// get a list of all the vertices
		Collection<Vertex> vertices = vertexMap.values();

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the action to be performed");
		System.out.println("1.Find Shortest path");
		System.out.println("2.Print Graph");
		System.out.println("3.Quit");
		System.out.println("Choice[3]:");
		String number = sc.nextLine();
		try {
			if (number.equals("1")) {
				System.out.println("Enter the path  (Ex : path Belk Education)");
				String[] parts = sc.nextLine().split(" ");
				String sourcePath = parts[1];
				String destPath = parts[2];
				Vertex source = vertexMap.get(sourcePath);
				computePaths(source);
				Vertex Dest = vertexMap.get(destPath);
				List<Vertex> path = getShortestPathTo(Dest);// truncateDecimal(Dest.minDistance,
															// 4)
				System.out.println("Path: " + path + " " + Math.round(Dest.minDistance * 100) / 100D);
			}
			if (number.equals("2")) {
				printGraph(vertices);
			}
			if (number.equals("3")) {
				System.out.println("Terminated");
				sc.close();
			}
		} catch (Exception e) {
			System.out.println("Kindly Correct the Input");

		}
	}

	private static void printGraph(Collection<Vertex> vertices) {
		for (Vertex v : vertices) {
			System.out.println("\n");
			System.out.println(v);
			v.getEdge(v.adjacencies);
		}
	}
}
