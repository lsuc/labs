package hr.fer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Node {

	private String label;
	private Map<Node, Integer> connections = null;
	private int distance;
	private Node predecessor;
	
	public Node(String label){
		this.setLabel(label);
		this.connections = new HashMap<Node, Integer>();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	public void connect(Node n, int value){
		this.connections.put(n, value);
	}

	public void setConnections(Map<Node, Integer> connections) {
		this.connections = connections;
	}

	public Map<Node, Integer> getConnections() {
		return connections;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	public Node getPredecessor() {
		return predecessor;
	}
}