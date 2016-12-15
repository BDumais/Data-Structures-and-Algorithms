//EmptyListException, error for empty list
//Written by Ben Dumais, 250669195, for CS2210 Assignment 2
public class EmptyListException extends RuntimeException {

	public EmptyListException(){
		super("The List is empty");
	}
}
