//Node object, for use with graphs to store data
//Written by Ben Dumais, 250699195, for CS2210 Assignment 5
public class Node {

	//Attributes
	private int name;			//Int variable for the node number, which will serve as its name
	private boolean mark;		//Boolean value to indicate whether node has been marked or not
	
	//Constructor
	public Node(int name){
		this.name = name;
		mark = false;
	}
	
	/* Methods */
	
	//Set method for mark
	public void setMark(boolean mark){
		this.mark = mark;
	}
	
	//Get method for mark
	public boolean getMark(){
		return mark;
	}
	
	//Get method for name
	public int getName(){
		return name;
	}
	
}

