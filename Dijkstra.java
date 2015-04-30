import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
    private static BigDecimal truncateDecimal(double x,int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
    public static void main(String args[]) {
    	 Set mySet = new HashSet();
        TreeMap<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("C:/DELL/graph2.txt"));
            String line;
            boolean inVertex = true;
           
            while ((line = in.readLine()) != null) {
            	
            	 String[] parts = line.split(" ");
                 String vFrom = parts[0];
                 String vTo = parts[1];
                 String[] test= {parts[0],parts[1],parts[2]};
                 Vertex v = new Vertex(vFrom);
                 if(!mySet.contains(v.name)){
                 	vertexMap.put(v.name,v);
                 mySet.add(v.name);}
                 Vertex v1 = new Vertex(vTo);
                 if(!mySet.contains(v1.name)){
                 	vertexMap.put(v1.name,v1);
                mySet.add(v1.name);}
               // System.out.println("xx"+mySet);
                 double weight = Double.parseDouble(parts[2]);
                 Vertex x = vertexMap.get(v.name);
                 if (x != null) {
                     x.addEdge(new Edge(vertexMap.get(v1.name), weight));
                 }
                 
                  vFrom = parts[1];
                  vTo = parts[0];
                  Vertex v2 = new Vertex(vFrom);
                  if(!mySet.contains(v2.name)){
                  	vertexMap.put(v2.name,v2);
                  mySet.add(v2.name);}
                  Vertex v3 = new Vertex(vTo);
                  if(!mySet.contains(v3.name)){
                  	vertexMap.put(v3.name,v3);
                 mySet.add(v3.name);}
                  weight = Double.parseDouble(parts[2]);
                  Vertex y = vertexMap.get(v2.name);
                 if (y != null) {
                     y.addEdge(new Edge(vertexMap.get(v3.name), weight));
                 }
             }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        finally{
            if(in!= null)
                try {
                    in.close();
                } catch (IOException ignore) {
                }
        }
        //get a list of all the vertices
        Collection<Vertex> vertices = vertexMap.values();
       
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the action to be performed");
        System.out.println("1.Find Shortest path");
        System.out.println("2.Print Graph");
        System.out.println("3.Quit");
        String number = sc.nextLine();
        try{
        if(number.equals("1"))
        {
        System.out.println("Enter the path");
        String[] parts = sc.nextLine().split(" ");
        String sourcePath = parts[1];
        String destPath = parts[2];
        	Vertex source = vertexMap.get(sourcePath);
            computePaths(source); 
        Vertex Dest = vertexMap.get(destPath);
    	List<Vertex> path = getShortestPathTo(Dest);//truncateDecimal(Dest.minDistance, 4)
        System.out.println("Path: " + path+" "+    Math.round(Dest.minDistance*100)/100D);}
        if(number.equals("2"))
        {
        printGraph(vertices);}
        if(number.equals("3"))
        {
        	System.out.println("Terminated");
sc.close();        
        }

        }catch(Exception e){
        	System.out.println("Invalid Input");
        	
        }
        }

	private static void printGraph(Collection<Vertex> vertices) {
		for (Vertex v : vertices)
    	{
        	System.out.println("\n");
        	System.out.println(v);
        	v.getEdge(v.adjacencies);
    	}
	}
}

       
         
       

	
