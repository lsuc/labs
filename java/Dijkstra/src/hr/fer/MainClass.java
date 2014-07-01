package hr.fer;

import java.util.ArrayList;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataLoading data = new DataLoading();
		Graph g = data.loadGraph("C:\\graf.txt");
		
		ArrayList<Node> list = g.Dijkstra("a", "s");
		
		System.out.println("Pronadeni najkraci put:");
		for(int i = 0; i < list.size(); i++){
			Node n = list.get(i);
			System.out.print(n.getLabel());
			if(i != list.size()-1){
				System.out.print("->");
			}
			
		}
		g.saveGraph(list);
	}

}
