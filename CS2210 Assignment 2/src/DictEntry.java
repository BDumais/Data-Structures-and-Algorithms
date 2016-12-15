//DictEntry object
//Written by Ben Dumais, 250669195, for CS2210 Assignment 2
public class DictEntry {

	//Attributes
	private String key;			//String to store the key of this entry
	private int code;			//Integer to store the code for this entry
	
	//Constructor
	public DictEntry(String key, int code){
		this.key=key;
		this.code=code;
	}
	
	//Get and Set methods
	public String getKey(){
		return key;
	}
	public int getCode(){
		return code;
	}
	
//End of class	
}
