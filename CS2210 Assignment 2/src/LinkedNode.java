//LinkedNode Object, for use with LinkedList object
//Written by Ben Dumais, 250669195, For CS2210 Assignment 2
public class LinkedNode {

	LinkedNode next;
	DictEntry entry;
	
	public LinkedNode(DictEntry entry){
		this.entry = entry;
		next=null;
	}
	
	public LinkedNode getNext(){
		return next;
	}
	public DictEntry getEntry(){
		return entry;
	}
	public void setNext(LinkedNode nNext){
		next=nNext;
	}
	public void setEntry(DictEntry nEntry){
		entry=nEntry;
	}
	
}
