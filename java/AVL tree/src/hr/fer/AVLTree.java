package hr.fer;

public class AVLTree {
	
	private Node rootNode;
	    
	public AVLTree() {
		rootNode = null;
	}
	public AVLTree(Node rootElement) {
		setRootElement(rootElement);
	}	  

	public void printAVLTree(Node node, int depth){  		
		if(node != null){
			printAVLTree(node.getRightChild(), depth+1);

			for (int i = 0; i < depth; i++)
				System.out.print("   ");
			System.out.println(node.getData());

		    printAVLTree(node.getLeftChild(), depth+1);		    
	    } else {
			for (int i = 0; i < depth; i++)
				System.out.print("   ");
			
			System.out.println("~");
	    }
	}
	
	public void printAVLTree(Node node) {
		System.out.println();
		printAVLTree(node, 0);
		System.out.println();
	}
	
	
	public void insert (int value){
		if (rootNode == null){
			rootNode = new Node(value, null);
		return;
		}
		Node node = rootNode;
		while (node != null){
			
			if (value < node.getData()){
				if(node.getLeftChild() == null){
					node.setLeftChild(new Node(value, node));
					balanceTree(node);
					return;
				}
				else {
					node = node.getLeftChild();
				}
			}
			else if (value > node.getData()){
				if(node.getRightChild() == null) {
					node.setRightChild(new Node(value, node));
					balanceTree(node);
					return;
				}
				else {
					node = node.getRightChild();	
				}
			}			
			else {
				System.out.println("Vrijednost " + node.getData() +" vec postoji u stablu!");
				break;
			}
		}
	}
	
	private void balanceTree (Node node){
		while (node != null){
			int nodeFactor = node.getBalanceFactor();
			if (nodeFactor == 2){						
				int childFactor = node.getRightChild().getBalanceFactor();
				if (childFactor == 1 || childFactor == 0){
					singleRotate(node, Side.LEFT);
				}
				else if (childFactor == -1){
					doubleRotate(node.getRightChild(), Side.RIGHT);
				}						
			}			
			else if (nodeFactor == -2){
				int childFactor = node.getLeftChild().getBalanceFactor();
				if (childFactor == 1){
					doubleRotate(node.getLeftChild(), Side.LEFT);
				}
				else if (childFactor == -1 || childFactor == 0){
					singleRotate(node, Side.RIGHT);
				}
			}
			node = node.getParent();
		}
	}
	
	public Node search (int value){
		Node node = rootNode;
		while (node != null){
			if (node.getData() == value){
				return node;
			}
			else if (value < node.getData()){
				node = node.getLeftChild();
			}
			else {
				node = node.getRightChild();
			}
		}
		return null;
	}
	
	public Node deleteByCopying (Node node) {
		Node tmp = node.getLeftChild();
		while (tmp.getRightChild() != null){			
			tmp = tmp.getRightChild();
		}
		node.setData(tmp.getData());
		if (tmp != node.getLeftChild()){	
			if(tmp.getLeftChild() != null){
				tmp.getParent().setRightChild(tmp.getLeftChild());
				tmp.getLeftChild().setParent(tmp.getParent());
			}
			else {
				tmp.getParent().setRightChild(null);
			}			
		}
		else {
			tmp.getParent().setLeftChild(tmp.getLeftChild());
			if (tmp.getLeftChild() != null){
				tmp.getLeftChild().setParent(tmp.getParent());
			}
		}
		return tmp.getParent();
	}
	
	public boolean delete (int value){
		Node node = search(value);
		if (node == null){
			System.out.println("Trazeni element ne postoji, te se ne moze obrisati.");
			return false;
		}
		else {
			if (node.isLeaf()){
				if (node.isRoot()){
					rootNode = null;
					System.out.println("Nakon brisanja trazenog elementa stablo je prazno.");				
				}
				else {
					if (node.getParent().getData() < value) {
						node.getParent().setRightChild(null);
						balanceTree(node.getParent());
					}
					else {
						node.getParent().setLeftChild(null);
						balanceTree(node.getParent());
					}
				}
			}
			else if (node.getRightChild() != null && node.getLeftChild() != null){
					Node nodeParent = deleteByCopying(node); //vraæa roditelja èvora koji smo obrisali kopiranjem
					balanceTree(nodeParent);
			}
			
			else if (node.getLeftChild() == null && node.getRightChild() != null) { //sluèaj kad èvor ima samo jedno dijete
				if (node != rootNode){
					if (node.getParent().getLeftChild() == node){
						node.getParent().setLeftChild(node.getRightChild());
						node.getRightChild().setParent(node.getParent());
						balanceTree(node.getParent());
					}
					else {
						node.getParent().setRightChild(node.getRightChild());
						node.getRightChild().setParent(node.getParent());
						balanceTree(node.getParent());
					}				
				}
				else {
					rootNode = node.getRightChild();
					node.getRightChild().setParent(null);
				}
			}
			
			else if (node.getLeftChild() != null && node.getRightChild() == null){ //situacija kad brišemo èvor koji ima samo lijevo dijete
				if (node != rootNode){
					if (node.getParent().getLeftChild() == node){
						node.getParent().setLeftChild(node.getLeftChild());
						node.getLeftChild().setParent(node.getParent());
						balanceTree(node.getParent());
					}
					else {
						node.getParent().setRightChild(node.getLeftChild());
						node.getLeftChild().setParent(node.getParent());
						balanceTree(node.getParent());
					}
				}
				else {
					rootNode = node.getLeftChild();
					node.getLeftChild().setParent(null);
				}
				
			}
			return true;
		}
	}
	
	public int calculateDepth (Node node){
		if (node == null){
			return 0;
		}
		else{
			return Math.max(calculateDepth(node.getLeftChild()), calculateDepth(node.getRightChild())) + 1;
		}
	}
	
	public void doubleRotate (Node node, Side side){
		if (side == Side.LEFT){
			singleRotate(node, Side.LEFT);
			singleRotate(node.getParent().getParent(), Side.RIGHT);
		}
		else{
			singleRotate(node, Side.RIGHT);
			singleRotate(node.getParent().getParent(), Side.LEFT);
		}		
	}
	
	public void singleRotate (Node node, Side side) {
		Node grandpa = node.getParent();
		Node parent = node;
		Node child = (side == Side.LEFT) ? node.getRightChild() : node.getLeftChild();		
		
		if (parent != rootNode) {
			if (grandpa.getLeftChild() == parent) {
				grandpa.setLeftChild(child);
			}
			else {
				grandpa.setRightChild(child);
			}
			child.setParent(grandpa);
		}
		else{
			setRootElement(child);
			child.setParent(null);
		}
		
		if (side == Side.RIGHT) {
			parent.setLeftChild(child.getRightChild());
			if (parent.getLeftChild() != null) {
				parent.getLeftChild().setParent(parent);
			}
			
			child.setRightChild(parent);
			parent.setParent(child);
		} 
		
		else if (side == Side.LEFT) {
			parent.setRightChild(child.getLeftChild());
			if (parent.getRightChild() != null) {
				parent.getRightChild().setParent(parent);
			}
			
			child.setLeftChild(parent);
			parent.setParent(child);
		}	
	}	

	public Node getRootElement() {
		return rootNode;
	}	 
	    
	public void setRootElement(Node rootElement) {
		this.rootNode = rootElement;
	}	

}