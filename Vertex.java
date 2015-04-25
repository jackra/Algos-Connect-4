import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Vertex implements Comparable<Vertex> {
    public final String name;
    public List<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    TreeMap<String, String> edgeMap = new TreeMap<String, String>();
    public Vertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    public void addEdge(Edge e) {
        adjacencies.add(e);
    }
    public void getEdge(List<Edge> adjacencies) {
        for(Edge a : adjacencies)
        { //System.out.println(a.target.name+" "+a.weight);
        edgeMap.put(a.target.name, Double.toString(a.weight));
        }
        for (Map.Entry<String, String> entry : edgeMap.entrySet()) {
            String key = entry.getKey().toString();;
            String value = entry.getValue();
            System.out.println(" " + key + "  " + value );
        }
        edgeMap.clear();
    }

    public String toString() {
        return name;
    }

    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }

}