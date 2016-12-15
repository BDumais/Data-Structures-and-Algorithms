//Edge object, for use with graphs to connect two nodes and store their relationship
//Written by Ben Dumais, 250699195, for CS2210 Assignment 5
public class Edge {

	//Attributes
	private Node start, end;		//Node variables to store the end points of this edge
	private String type, label;		//String variables to store the type and label of this edge
	
	//Constructor
	public Edge(Node u, Node v, String type){
		start = u;			//Store the passed paramaters into this edge
		end = v;
		this.type = type;
	}
	
	/* Methods */
	
	//Get method for start
	public Node firstEndpoint(){
		return start;
	}
	
	//Get method for end
	public Node secondEndpoint(){
		return end;
	}
	
	//Get method for type
	public String getType(){
		return type;
	}
	
	//Set method for label
	public void setLabel(String label){
		this.label = label;
	}
	
	//Get method for label
	public String getLabel(){
		return label;
	}
	
}
