//DictEntry object
//Written by Ben Dumais, 250669195, for CS2210 Assignment 4
//Modified from Assignment 2
public class DictEntry {

	/* Attributes */
	private Position p;			//Position object, acts as the key
	private int color;			//Integer to store the code for this entry
	
	/* Constructor */
	public DictEntry(Position p, int color){
		this.p=p;
		this.color=color;
	}
	
	/* Methods */
	
	//Get method for Position
	public Position getPosition(){
		return p;
	}
	
	//Get method for Color
	public int getColor(){
		return color;
	}
	
//End of class	
}
