//Dictionary Object, Implements Dictionary ADT
//Written by Ben Dumais, 250669195, for CS2210 Assignment 2
public class Dictionary implements DictionaryADT{

	//Attributes
	private LinkedList[] table;			//Array of linked lists
	private int entries=0, size;				//Integer to store amount of entries in dictionary
	
	//Constructor
	public Dictionary(int size){
		table = new LinkedList[size];	//Create an array of Linkedlists of size 'size'
		this.size=size;					//Set the size attribute to size
		for (int i=0;i<size;i++){		//Loopthrough and initialize each position to an empty list
			table[i]= new LinkedList();
		}
	}
	
	//Methods
	
	//Private Method to hash a key to a value in the table
	private int hash(String key){
		
		final int A = 11;								//Set range value to 11 -> after testing, 11 seems to produce very few collisions
		int value = (int)key.charAt(key.length()-1);	//Set the initial value to be the integer value of the last character of the key
		
		for(int i= key.length()-2; i >= 0; i--){			//Loop through the String and perform the polynomial hash, modding by the size of the table
			value=((value*A) + (int)key.charAt(i)) % size;
		}
		
		return value;			//Return the hashed value
	}	
	
	//Method to insert a Dictionary Entry into the Dictionary
	public int insert(DictEntry pair) throws DictionaryException{
		
		String key = pair.getKey();
		
		//If the dictionary's find method returns null, object is not in the dictionary so proceed		
		if(find(key)==null){
			
			entries++;	//Increment entries
			// add pair to the list at the position denoted by the hash function, and return the value of the add method (1 if a collison occured, 0 if not)		
			return table[hash(key)].add(pair);
		}
		else //Otherwise a DictEntry object was returned as it was found in the dictionary, which means it was in the list. Throw an exception in this case
			throw new DictionaryException();
	}

	//Method to remove an entry from the dictionary
	public void remove(String key) throws DictionaryException{
		try{							//Try removing
		table[hash(key)].remove(key);			//Call remove method on the linked list at position hash(key) of table
		entries--; 								//Decrease entries
		}
		catch(EmptyListException e){	//If an empty list exception was thrown, catch it and throw a dictionary exception
			throw new DictionaryException();
		}
	}
	
	//Method to find an entry in the dictionary based on its key
	public DictEntry find(String key){
		return table[hash(key)].find(key);	
	}

	//Method to return the number of elements in the dictionary
	public int numElements(){
		return entries;		//return the entries attribute
	}
	
	
}
