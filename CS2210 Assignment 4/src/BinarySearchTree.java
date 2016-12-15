//BinarySearchTree object
//Written by Ben Dumais, 250669195, for CS2210 Assignment 4
public class BinarySearchTree implements BinarySearchTreeADT{
	
	/* Attributes */
	private int size;			//Integer to hold the current size of the tree (number of nodes)
	private BinaryNode root;	//BinaryNode to store the root of the tree
	
	/* Constructor */
	public BinarySearchTree(){		//Constructor to create the tree
		size=0;						//Set size to 0, so we know it has no nodes in it right now
	}
	
	/* Methods */
	
	//Method to find and return a Position within the Tree
	public DictEntry find(Position key){
		return findNode(key).getData();		//Call private helper method findNode and return the data from it's result
	}

	//Method to insert a new entry into the tree
	public void insert(DictEntry data) throws BSTException{
		if(isEmpty()){							//Check if the tree is empty
			root = new BinaryNode(data);		//If so, create a new node and set it as the root
			size++;									//Increase size
			return;									//Exit method
		}
		
		BinaryNode p = findNode(data.getPosition());	//Set p to the result of the findNode method
		if(!p.isLeaf()){							//If p is not a leaf (findNode returned an internal node containing the key), throw an exception
			throw new BSTException();
		}
			
		else{									//Otherwise p is a leaf
			p.setData(data);					//Convert p from a leaf into an internal node via setData method
		    size++;								//Increment size of tree
		}		
	}
	
	public void remove(Position key) throws BSTException{
		
		BinaryNode n = findNode(key);	//find the node containing the key and set it to n
		
		if(n == null || n.isLeaf())		//If n is a leaf or null was returned (n is not in tree or tree is empty) throw an exception
			throw new BSTException();
		
		else{	//Otherwise, test the following cases:
			if(n.getRight().isLeaf() && n.getLeft().isLeaf())				//If both children are leafs, turn this node into a leaf
				n.clear();													//This method preserves the link to the parent node
			
			else if(n.getRight().isLeaf() && !n.getLeft().isLeaf()){		//If n only has a left child, do the following:
				
				if(n.isLeftChild())						//If n is the left child
					n.getParent().setLeft(n.getLeft());	//Set the left child of the n's parent to n's left child
				
				else
					n.getParent().setRight(n.getLeft());//Otherwise set the left child as n's parent's right child
			}
			
			else if(!n.getRight().isLeaf() && n.getLeft().isLeaf())		//If n only has a right child, do the following
				
				if(n.isLeftChild())							//Same as above, but using n's right child
					n.getParent().setLeft(n.getRight());
			
				else
					n.getParent().setRight(n.getRight());
			
			else {
			
				/*
				 * In the case that the node to be removed from the tree has both of its children as internal nodes, we need to replace n with
				 * an appropriate node. To do this, we find the smallest node in the right subtree of n, and change the data in n to this new node's data.
				 * We must then remove this new node from the tree so that we do not have duplicates.
				 */
				
				BinaryNode p = smallestNode(n.getRight()); //Create new node p, and set it to the successor of n (smallest from right)
				
				n.swapData(p);								//Switch the data in n to p's
				
				/* Now we must remove p from the tree:
				 * Since p will be the furthest left node in the right subtree, it either has two leafs as children 
				 * or only a right child - it does not have a left child. As such test the right child to see if it is a leaf or not
				 */
				
				 if(p.getRight().isLeaf())							//If the right child is a leaf, set p to a leaf
					p.clear();
				else												//Otherwise the right child is internal
				  p.getParent().setLeft(p.getRight());				//Set the parent of p's left child as the right child of p										
			}
		}
		
		size--;			//Decrement size of the tree
		
	}
	
	//Method to return Dictionary entry containing the smallest key larger than the input key
	public DictEntry successor(Position key){
		BinaryNode n = successorNode(key);		//Find the successor to the key via helper method
		if(n == null)				//If null was returned, return null
			return null;
		else						//Otherwise return that node's data
			return n.getData();
		
	}
	
	//Method to return Dictionary Entry containing the largest key smaller than the input key
	public DictEntry predecessor(Position key){
		BinaryNode n = predecessorNode(key);	//Find predecessor to key via helper method
		if(n == null)			//If null was returned, return null
			return null;
		else					//Otherwise return the data from that node
			return n.getData();
	}
	

	//Method to return the DictEntry containing the smallest key in the whole tree
	public DictEntry smallest(){
		BinaryNode n = smallestNode(root);	//Call the smallestNode method
		if(n == null)		//If null was returned, return null
			return null;
		else				//Otherwise return the data from that node
			return n.getData();
	}
	
	//Method to return the DictEntry containing the largest key in the whole tree
	public DictEntry largest(){
		BinaryNode n = largestNode(root);	//Call largestNode method with the root of the tree
		if(n == null)			//If null was returned, return null
			return null;
		else					//Otherwise return the data from that node
			return n.getData();
	}
	
	
	/*
	 * The following methods were implemented due to:
	 * A) restrictions on methods in DictEntry: since we are unable to add any public methods to DictEntry, I have decided to store
	 *    each dictionary entry into a BinaryNode object, which will be the objects stored in the tree.
	 * B) Make tree traversals easier: the ability to link nodes via left right and parent makes certain methods much easier to implement
	 * C) Generally make everything a bit more optimized and easier to read rather than have multiple searches to find parents and the like
	 * 
	 * All the following methods are private as to not violate guidelines in the assignment. 
	 */
	
	
	//Private Method to determine if tree is empty or not
	private boolean isEmpty(){
		return size==0;		//Return the result of the number of nodes = 0
	}
	
	//Private Method to find and return a specific Node in the tree, as opposed to a DictEntry
	private BinaryNode findNode(Position k){
		if(isEmpty())		//If the tree is empty, the key is not stored in the tree so return null
			return null;
		
		int result = root.compare(k); //Get the result of comparing the root's stored Position to the key
		
		if(result == 0)		//If its a match, return the root
			return root;
		else				//Otherwise we must search the tree
			if(result < 0)								//If comparison returns -1, the key is a position lower than the key, meaning it has HIGHER x and y
				return findNodeRec(root.getRight(), k);	//Since the values are higher, they are stored in the right subtree if at all, so search there
			else										//Otherwise call helper on left tree
				return findNodeRec(root.getLeft(), k);	
	}
	
	//Helper Method for FindNode, recursively calls itself to find and return a node in the tree
	private BinaryNode findNodeRec(BinaryNode r, Position k){
		if(r.isLeaf())		//If current node is a leaf, return this leaf (indicates that the key is not in the tree, but should be in this node)
			return r;
		int result = r.compare(k);	//Compare the key to the current node's position
		if(result == 0)											//If its a match, return this root
			return r;
		else{											//Otherwise call this function again
			if(result < 0)								
				return findNodeRec(r.getRight(), k);	//If result is negative (key is larger than current), search right tree
			else
				return findNodeRec(r.getLeft(), k);		//Otherwise search left tree
		}
	}
	
	//Private method to return the node containing the smallest key that is larger than the input key
	private BinaryNode successorNode(Position key){
		if(isEmpty() || size == 1)		//If the tree is empty or contains one node, return null
			return null;
		else{
			BinaryNode n = findNode(key);	//Set n to be the node containing the key
		
			if(!n.isLeaf() && !n.getRight().isLeaf()){	//If n and its right child are internal
				return smallestNode(n.getRight());					//return the smallest node in the right tree
			}
			
			else{		//Otherwise we must search upwards
				while(n.getParent() != null && n.isRightChild())	//While n has a parent and is the right child of its parent
					n = n.getParent();											//Set n to it's parent
				
				if(n == root)			//If n is the root (loop terminated when parent was null)
					return null;		//return null
				else					//Otherwise return n's parent
					return n.getParent();
			}
	
		}
	}
	
	//Method to return node containing the largest key smaller than the input key
	private BinaryNode predecessorNode(Position key){
		if(isEmpty() || size == 1)	//If the tree is empty or only has one node, return null
			return null;
		else{
			BinaryNode n = findNode(key);	//Set n to node containing the input key
		
			if(!n.isLeaf() && !n.getLeft().isLeaf()){	//If n and its left child are internal
				return largestNode(n.getLeft());					//Set n to be the largest node in the left tree
			}
			else{											//Otherwise we must search above the node
				while(n.getParent() != null && n.isLeftChild())	//While n is the left child of it's parent and its parent exists
					n = n.getParent();											//Set n to it's parent (climb the tree)
				
				if(n == root)		//if n is the root (loop was terminated when parent was null) return null
					return null;
				else			//Otherwise return n's Parent
					return n.getParent();
			}
	
		}
	}
	
	//Private Method to return the NODE containing the smallest key in the tree rooted by 'start'
	private BinaryNode smallestNode(BinaryNode r){
		if(isEmpty())		//If the tree is empty, return null
			return null;
		else if(size == 1)		//If there is only one node (the root) return it
			return root;
		else {							//Otherwise we need to traverse the tree
		
			while(!r.getLeft().isLeaf())	//While the left child of r is not a leaf
			r = r.getLeft();					//Set r to the left child
		
			return r;				//After termination of the loop, r is the furthest left node in the tree, so return it
		}
	}
	
	//Private method to return the NODE containing the largest key in the tree rooted by 'start'
	private BinaryNode largestNode(BinaryNode r){
		if(isEmpty())					//If the tree is empty, return null
			return null;
		else if(size == 1)				//If there is only one node (the root) return it
			return root;
		else{							//Otherwise we need to traverse the tree
		
			while(!r.getRight().isLeaf())	//While the right child of r is not a leaf
				r = r.getRight();				//Set r to the right child
		
			return r;				//After termination of the loop, r is the furthest right node in the tree, so return it
		}
	}
	
	
}
