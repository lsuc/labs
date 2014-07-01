package hr.fer;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {		
		if ( n1.getDistance() < n2.getDistance()) {
			return -1;
		} else if ( n1.getDistance() > n2.getDistance()) {
			return 1;
		} else {
			return 0;
		}
	}
}