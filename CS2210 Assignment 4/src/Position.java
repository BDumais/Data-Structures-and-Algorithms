//Position Object
//Written by Ben Dumais, 250669195, for CS2210 Assignment 4
public class Position {

    /* Attributes */
	private int x, y;			//Integer variables to for the x and y coordinates of the object
	
	/* Constructor */
	public Position(int x, int y){		
		this.x = x;					//Set object's X to the X passed by constructor
		this.y = y;					//Set object's Y to the Y passed by constructor
	}
	
	/* Methods */
	
	//Method to return X of Position object
	public int getX(){
		return x;		//Return X value of this object
	}
	
	//Method to return Y of position object
	public int getY(){
		return y;		//Return Y of this object
	}
	
	//Method to compare the X and Y of this position with the passed position
	public int compareTo(Position p){
		
		int pY = p.getY(), pX = p.getX();	//Set pY and xY to the coordinates of the passed position
		
		if(y == pY && x == pX)		//If the coordinates are exact matches, return 0
			return 0;
		else if (y < pY || (y == pY && x < pX))	//Otherwise, if this y is less than the y from p or the y's are equal but the x is smaller
			return -1;							//Return -1, as this position is less than the passed position
		else				//Otherwise it is greater than, so return 1
			return 1;
	}

//End of class
}
