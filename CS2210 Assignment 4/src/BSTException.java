//BSTException, error for illegal operation on BST (remove non existent entry etc)
//Written by Ben Dumais, 250669195, for CS2210 Assignment 4
public class BSTException extends RuntimeException{

	//Constructor
	public BSTException(){
		super("Illegal Operation on Binary Search Tree");	//Create an exception with the message "illegal operation"
	}
	
}
