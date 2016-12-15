//LinkedList object
public class LinkedList{
	
	//Attributes
	private int size;
	private LinkedNode head;				//The object at the start of the list
	
	//Constructors
	public LinkedList(LinkedNode h){		//If constructed with an entry, make that entry the head and increment size
		head=h;
		size=1;
	}
	public LinkedList(){				//If no entry is passed, create head with null and size equals 0
		head=null;
		size=0;
	}
	
	//Methods
	
	//Method to add an entry to the list
	public int add(DictEntry newEnt){
		int result = 0;			//Create new integer variable to be returned
		LinkedNode newHead = new LinkedNode(newEnt);	//Create a new node with the current entry
		
		if(size!=0)				//If the list is not empty when adding a new entry (ie a collisions is occurring), result = 1
			result = 1;
		
		newHead.setNext(head); 	//Set the new head's next attribute to the current head
		head=newHead;			//Set the head of the list to the new entry
		size++;					//Increment size
		return result;			//Return the result
	}
	public void remove(String key) throws EmptyListException{
		if(size==0)								//If list is empty, cannot remove so throw exception
			throw new EmptyListException();
						
		LinkedNode previous = null;		//Set up objects for us to loop through list with
		LinkedNode current = head;
		boolean foundTarget = false;
		
		if(current.getEntry().getKey().equals(key)){	//If the head is the target, remove it
			head=current.getNext();
			size--;
			return;
		}
		else{
		
			while(current != null && !foundTarget){		//Loop through list until target is found or we reach the end
				if(current.getEntry().getKey().equals(key)){
					foundTarget = true;				//If found, change boolean to true and exit loop
				}
				else {		//Otherwise set previous to current and current to the next
					previous = current;
					current = current.getNext();
				}
			}
		
			if (!foundTarget)		//If target was not found, throw an exception
				throw new EmptyListException();
		
			if(size==1){	//If there was only one entry in the list and the target was found, set head to point to null
				head=null;
				size --;
				return;
			}
		
			previous.setNext(current.getNext());	//Otherwise, set the previous entry's next to the targets next
			size--;									//Decrease size by 1
			return;
		}
	}
	
	//Method to find an entry in the list
	public DictEntry find(String key) {
		if(size==0)							//If list is empty, return null
			return null;
		
		LinkedNode current = head;			//Set the current to the first entry
		while(current != null){				//Loop through the list until we reach the end or method is exited

			if(current.getEntry().getKey().equals(key))
				return current.getEntry();				//If the current node's entry's key is the same as the target key, return that entry

			current=current.getNext();		//Otherwise get the next entry
		}
		return null;							//If the loop exits and target was not found, return null indicating it was not found
	}
	
	//Method to return the size of the list
	public int size(){
		return size;
	}

}
