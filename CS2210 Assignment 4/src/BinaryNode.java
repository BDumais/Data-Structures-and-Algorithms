//BinaryNode object, for use with Binary and Binary search trees
//Written by Ben Dumais, 250699195, for CS2210 Assignment 4
public class BinaryNode {
	
	/* Attributes */
	private DictEntry data;					//Variable to store data of node (dictentry)
	private BinaryNode left, right, parent;	//Node variables to store left right and parent nodes
	
	/* Constructors */
	
	//Constructor for creating an internal node (used to create root)
	public BinaryNode(DictEntry d){
		data = d;					//Store passed DictEntry as the data for this node
		parent = null;				//Set the parent to null
		right = new BinaryNode();	//Create a leaf as the right child
		right.setParent(this);		//Set this node to be the parent of its right child
		left = new BinaryNode();	//Create a leaf as the left child
		left.setParent(this);		//Set this node as the parent of the left child
	}
	
	//Constructor to create a leaf node
	public BinaryNode(){
		data = null;	//Set data to null, indicating it is a leaf
	}
	
	
	/* Methods */
	
	//Method to change the left child of a node
	public void setLeft(BinaryNode l){
		left = l;
	}
	//Method to change the right child of a node
	public void setRight(BinaryNode r){
		right = r;
	}
	//Method to change the parent of a node
	public void setParent(BinaryNode p){
		parent = p;
	}
	
	//Method to change the data of a node
	//This method is used when setting a leaf to internal, so we have to create children for the node as well
	public void setData(DictEntry d){
		data = d;					//Change the data of this node to the passed data, thus making this an internal node
		left = new BinaryNode();	//Create a new leaf as the left
		left.setParent(this);		//Link left child to this node
		right = new BinaryNode();	//Repeat for right child
		right.setParent(this);
	}
	
	//Method to replace the data of a node with another (used during remove algorithm)
	public void swapData(BinaryNode n){
		data = n.getData();		//Set the data of this node to the data from the passed node
	}
	
	//Method to convert a node into a leaf (used during remove algorithm)
	//This method preserves the parent of the node, thus preserving the tree
	public void clear(){
		data = null;
		right = null;
		left = null;
	}
	
	//Method to return left child
	public BinaryNode getLeft(){
		return left;
	}
	//Method to return right child
	public BinaryNode getRight(){
		return right;
	}
	//Method to return parent node
	public BinaryNode getParent(){
		return parent;
	}
	
	//Method to return data of a node
	public DictEntry getData(){
		return data;
	}
	
	//Method to check if the node is a leaf
	public boolean isLeaf(){
		return data == null;	//If node has no data, it is a leaf
	}
	
	//Method to check if this node is the left child of its parent
	public boolean isLeftChild(){
		//Go to the parent, then get the Position stored in it's left child and compare to this node's position
		return parent.getLeft() == this;
		//Will return true if the nodes are the same
	}
	//Method to check if this node is the right child of its parent
	public boolean isRightChild(){
		//Go to the parent, then get the Position stored in it's right child and compare to this node's position
		return parent.getRight() == this;
		//Will return true if the nodes are the same
	}
	
	//Method to compare the data in the node to the key (used to clean up the find algorithm and make it easier to understand)
	public int compare(Position k){
		
		//Get the Dictionary Entry from the node, then the Position object from that: compare it to the passed key and return the result
		return getData().getPosition().compareTo(k);	
	}
	

}
