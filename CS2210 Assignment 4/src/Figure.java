//Figure object, for use with collisions
//Written by Ben Dumais, 250669195, for CS2210 Assignment 4
public class Figure implements FigureADT{

	/* Attributes */
	private int id, height, width, type;	//Variables for type, height and width, and figure id
	private Position p;						//Position object to store offset of figure
	BinarySearchTree tree;					//Binary Search Tree to store pixels of this figure
	
	/* Constructor */
	
	public Figure(int id, int height, int width, int type, Position pos){
		//Set variables to the ones passed by constructor
		this.id = id;
		this.height = width;		//This looks very wrong, I know, but the height and width of the figures were switched around otherwise
		this.width = height;		//This caused some pretty weird issues with walls as they are not square
		this.type = type;
		this.p = pos;
		//Initialize tree to a new tree
		tree = new BinarySearchTree();
	}
	
	/* Methods */
	
	//Method to set type of figure
	public void setType(int type){
		this.type = type;
	}
	
	//Method to return Width of figure
	public int getWidth(){
		return width;
	}
	
	//Method to return Height of figure
	public int getHeight(){
		return height;
	}
	
	//Method to return type of character
	public int getType(){
		return type;
	}
	
	//Method to return ID of character
	public int getId(){
		return id;
	}
	
	//Method to return the offset (position) of object
	public Position getOffset(){
		return p;
	}
	
	//Method to change the offset (position) of object
	public void setOffset(Position value){
		p = value;	
	}
	
	//Method to insert a pixel into a figure: used to initialize figure
	public void addPixel(int x, int y, int color) throws BSTException{
		//Try inserting the pixel into the tree
		try{
			 tree.insert(new DictEntry(new Position(x,y), color)); //Call insert method on tree with the passed parameters
		}
		catch(Exception e){	//If an exception is thrown, catch it and throw a BSTException
			throw new BSTException();
		}
	}
	
	//Method to see if two figures intersect
	//Returns boolean result of intersection
	public boolean intersects(Figure fig){
		
		 //First check to see if the outer rectangles collide
		 if( checkRectangles(fig) ){		//Call checkRectangles on the figure
			 //If their rectangles intersect, we need to check their pixels
			 
			 Position large = tree.largest().getPosition(); //Create a new position object from the largest key stored in the tree
			 //From e being the smallest entry to being the largest entry, increment e by calling successor method
			 for(DictEntry e = tree.smallest(); e.getPosition().compareTo(large) < 0; e = tree.successor(e.getPosition())){
				 if(fig.findPixel(genPos(e, fig))) //Call findPixel method on the passed figure, using a newly generated position
					 return true; //If the above is true, there is an intersection, so return true
			 }
		 }
		 
		 return false;	//Otherwise there is no collision, so return false
		
		
	}
	
	/* Private Methods */
	
	//Method to check the enclosing rectangles of two figures
	//Returns a boolean result as to whether the two rectangles collide
	private boolean checkRectangles(Figure fig){
		
		//If the bottom of this figures rectangle is higher than the y offset and height of the comparing figure, they do not intercept
		if(p.getY() > fig.getOffset().getY() + fig.getHeight())
			return false;
		//If the top of this figure is lower than the bottom of the compared figure, they do not intercept
		else if(p.getY() + height < fig.getOffset().getY())
			return false;
		else	//Otherwise their y's overlap, so check their x's
			//If the left side of this figure is further right than the right side of the compared figure, they do not intercept
			if(p.getX() > fig.getOffset().getX() + fig.getWidth())
				return false;
			//If the right side of this figure is further left than the left side of the compared figure, they do not intercept
			else if(p.getX() + width < fig.getOffset().getX())
				return false;
			//Otherwise both their X and Y coordinates intercept, so return true
			else
				return true;			
	}
	
	//Method to search for a pixel within a figure with matching position
	//Returns boolean result as to whether there is a pixel at position p already in the figure
	private boolean findPixel(Position p){
		
		//Create new DictEntry object from the result of find method, called on the tree storing the pixels
		DictEntry n = tree.find(p);
		if(n != null)		//If find returned a Dictionary Entry, there is a pixel in the tree with coordinates of p
			return true;	//Return true to indicate the collision
		else				//Otherwise find returned null, and there is no collision
			return false;	//Return false
		
	}
	
	//Method to generate a position object based on the offsets of two figures and the coordinates of another position
	//Used to clean up intercepts method
	//Returns Position object
	private Position genPos(DictEntry e, Figure fig){
		
		//Set variables x and y to the x and y of the position stored in e
		int x = e.getPosition().getX(), y = e.getPosition().getY();
		//Set xt and yt to the x and y of the offset of THIS figure
		int xt = this.getOffset().getX(), yt = this.getOffset().getY();
		//Set xf and yf to the x and y of the offset of the passed figure, fig
		int xf = fig.getOffset().getX(), yf = fig.getOffset().getY();
				
		//Return a new position object using the above values
		return new Position(x + xt - xf, y + yt - yf);
		
	}

}

