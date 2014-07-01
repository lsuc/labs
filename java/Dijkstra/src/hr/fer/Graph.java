package hr.fer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class Graph {

	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Node getNode(String label){
		for(Node n : nodes){
			if(n.getLabel().equals(label)){
				return n;
			}
		}
		return null;
	}
	
	public Node addNode(String label){
		Node n = getNode(label);
		if(n == null){
			n = new Node(label);
			nodes.add(n);
		}
		return n;
	}
	
	public void connect(String n1, String n2, int value){
		addNode(n1).connect(addNode(n2), value);
	}
	
	public void saveGraph(ArrayList<Node> path){
		
		FileWriter writer = null;
		
		try {
			writer = new FileWriter("C:\\graf.dot");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter toFile = new PrintWriter(writer); 
	
		toFile.println("digraph { ");
		for(Node n : nodes){
			Iterator it = n.getConnections().entrySet().iterator();
		    while (it.hasNext()) {
		    	String s = "";
		    	
		        Map.Entry<Node, Integer> pairs = (Map.Entry<Node, Integer>)it.next();	
		        if(path.contains(pairs.getKey()) && n.equals(pairs.getKey().getPredecessor())){
		    		s = ",color=\"red\"";
		    	}
		        toFile.println(n.getLabel() + " -> " + pairs.getKey().getLabel() + " [label="+ pairs.getValue()+s+"]");
			}
		 }	
	    toFile.println("}");
	    toFile.flush();
	}		
	
	public ArrayList<Node> Dijkstra(String n1, String n2){
		
		for(Node n : nodes){
			n.setDistance(Integer.MAX_VALUE);
		}
		Node start = getNode(n1);
		if(start == null){
			return null;
		}
		start.setDistance(0);
		
		ArrayList<Node> unvisited = new ArrayList<Node>();
		for(Node n : nodes){
			unvisited.add(n);
		}		
		
		while(!unvisited.isEmpty()){
			Collections.sort(unvisited, new NodeComparator());
			
			Node v = unvisited.get(0);	
			if(v.getLabel().equals(n2)){
				break;
			}
			unvisited.remove(v);
			
			Iterator it = v.getConnections().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Node, Integer> pairs = (Map.Entry<Node, Integer>)it.next();
				Node u = pairs.getKey();
				if(unvisited.contains(pairs.getKey())){
					int distNew = v.getDistance() + v.getConnections().get(u);
					if(distNew < u.getDistance()){
						u.setDistance(distNew);
						u.setPredecessor(v);
					}
				}
			}
		}
		
		ArrayList<Node> path = new ArrayList<Node>();
		for(Node n = getNode(n2); n != null; n = n.getPredecessor() ){
			path.add(n);
		}
		
		Collections.reverse(path);
		
		return path;		
	}
}
