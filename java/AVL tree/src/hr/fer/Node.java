package hr.fer;

public class Node {
	
	 private int data;
	 private Node parent;
	 private Node leftChild;
	 private Node rightChild;	 
	    
	 public Node() {
		 this.leftChild = null;
		 this.rightChild = null;
		 this.parent = null;
	    }	 
	    
	 public Node(int data, Node parent) {
	     setData(data);
	     this.leftChild = null;
		 this.rightChild = null;
		 this.parent = parent;		 
	    }
	 
	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getData() {
		return data;
	}

	public boolean isRoot (){
		if (this.getParent() == null){
			return true;
		}
		return false;
	}
	public boolean isLeaf (){
		if (this.getLeftChild() == null && this.getRightChild() == null){
			return true;
		}
		return false;
	}	
	
	public int getHeight() {
		if (this == null)
			return 0;
		
		int rightHeight = (getRightChild() != null) ? getRightChild().getHeight() : 0;
		int leftHeight = (getLeftChild() != null) ? getLeftChild().getHeight() : 0;
		return Math.max(rightHeight, leftHeight) + 1;
	}
	
	public int getBalanceFactor() {
		if (this == null)
			return 0;
		
		int rightHeight = (getRightChild() != null) ? getRightChild().getHeight() : 0;
		int leftHeight = (getLeftChild() != null) ? getLeftChild().getHeight() : 0;
		
		return rightHeight - leftHeight;
	}
}
