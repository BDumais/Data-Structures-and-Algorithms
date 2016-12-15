//DictionaryException, error for empty list
//Written by Ben Dumais, 250669195, for CS2210 Assignment 2
public class DictionaryException extends RuntimeException {

	public DictionaryException(){
		super("Dictionary attempted to add an existing entry or remove a non-existant one");
	}
}
