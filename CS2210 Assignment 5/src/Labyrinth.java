//Labyrinth object, for building a maze from designated text file
//Written by Ben Dumais, 250699195, for CS2210 Assignment 5

/* Imports */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class Labyrinth {

	/* Attributes */
	private Graph graph;							//Declare graph object
	private int width, length, k, start, finish;	//Declare integers for the width, length, walls we can break (k) and the start/finish values of the labyrinth
	
	
	/* Constructor */
	public Labyrinth(String inputFile) throws LabyrinthException{	
	 try{
	      BufferedReader input = new BufferedReader(new FileReader(inputFile));	//Create a reader from the input file
	     
	      Integer.parseInt(input.readLine());			//Throw away the first line of the input, as we do not need it
	      width = Integer.parseInt(input.readLine());	//Set the width the the second line
	      length = Integer.parseInt(input.readLine());	//Set the Length to the third line
	      k = Integer.parseInt(input.readLine());		//Set k to the fourth line
	      
	      graph = new Graph(width*length);				//Build a graph of appropriate size to hold all nodes/rooms
	      
	      String line;									//Declare variable for string
	      int row = -1;									//Declare and initialize integer for current row of labyrinth we are reading
	      boolean isRoom;								//Declare a boolean to check if we are reading a room or an edge
	      
	      while( (line = input.readLine() ) != null ){	//Loop until we read a null string
	    	  
	    	  row++;									//Increment current row
	    	  
	    	  for(int i = 0; i < line.length(); i++){	//Loop through current line
	    		  
	    		  isRoom = (i%2 == 0 && row%2 == 0);	//If the current character is even and the row number is even, we are reading a room
	    		  
	    		  /*
	    		   * For the following, We can calculate the nodes associated with the current character using the following knowledge:
	    		   * 	Rooms are every even character in every even row, edges only appear between nodes, and we already know how many nodes are in each row.
	    		   * 	We can calculate the value of a node within its current row by dividing it by 2 (position 4 is node #2 in that row etc)
	    		   * 	We can adjust this to the node's actual value within the graph by adding the width of the labyrinth * the row/2
	    		   */
	    		  
	    		  if(isRoom){							//If we are reading a room
	    			  if(line.charAt(i) == 's')				//If the character we are reading is s, we are reading the start
	    				  start = (i/2) + (row/2)*width;	//Set start to the corresponding node value
	    			  else if(line.charAt(i) == 'e')		//If its an e, its the exit
	    				  finish = (i/2) + (row/2)*width;	//Set Finish to the corresponding node value   				  
	    		  }
	    		  else{									//Otherwise we are reading an edge or blank space
	    			  if(line.charAt(i) == '-')			//If the character is a -, its a horizontal hallway
	    				  graph.insertEdge(graph.getNode( (i/2) + (row/2)*width ), graph.getNode( (i/2) + (row/2)*width + 1 ), "hall");	//Create a new hall edge from the corresponding nodes
	    			  else if(line.charAt(i) == '|')	//If its an | its a vertical hall
	    				  graph.insertEdge(graph.getNode( (i/2) +((row-1)/2)*width ), graph.getNode( (i/2) +((row+1)/2)*width ), "hall");//Create a new hall edge from the corresponding nodes
	    			  else if(line.charAt(i) == 'h')	//If its an h its a horizontal wall
	    				  graph.insertEdge(graph.getNode( (i/2) + (row/2)*width ), graph.getNode( (i/2) + (row/2)*width + 1), "wall");	//Create a new wall edge from the corresponding nodes
	    			  else if(line.charAt(i) == 'v') 	//If its a v its a vertical wall
	    				  graph.insertEdge(graph.getNode( (i/2) +((row-1)/2)*width ), graph.getNode( (i/2) +((row+1)/2)*width ), "wall"); //Create a new wall edge from the corresponding nodes
	    		  }

	    	  }
	    	  
	      }//End of while line != null
	      
	      input.close();	//Close the input reader, as we have read the whole file
		
	 }
	 catch(Exception e){	//Catch any exceptions thrown while trying to create the labyrinth
		 throw new LabyrinthException("Error creating labyrinth");	//Throw a new labyrinth exception
	 }
	}

	/* Methods */
	
	//Method to return graph object of this Labyrinth
	public Graph getGraph() throws LabyrinthException{
		if(graph == null)	//If the graph does not exist, throw an exception
			throw new LabyrinthException("Graph is undefined");
		else	//Otherwise return it
			return graph;
	}
	
	//Method to solve and return an iterator of the labyrinth
	public Iterator solve(){
		
		/*
		 * To solve the labyrinth, I decided to use a modified DFS search that utilizes two stacks:
		 * 		The first stack is "sol" or the solution stack, which will hold the nodes that make up the path to the exit
		 *		The second is "paths" or the available paths to try, which will store unmarked and traversable nodes we can try next
		 *
		 * The method works by placing the entrance in the solution stack, searching all adjacent nodes for possible paths to try and then pushing these nodes
		 *  onto paths as they are found. Once all adjacent nodes have been looked at and added to paths if untravelled in our current solution, we take the next
		 *  path and add it to the solution before repeating the search.
		 *  
		 * The Stack allows us to search paths and manage nodes in a LIFO manner. This causes the latest node discovered to be added next to the solution, causing
		 *  the Labyrinth to be search as far down, then as far right, then left, then up as possible (highest to lowest node number). As most mazes follow this pattern
		 *  (entrance top left exit bottom right) this is a rather efficient means of search.
		 *  
		 * In the event that no new paths are found from the current node in solution, we can backtrack by simply popping the solution stack
		 *  until the new top is adjacent to the next possible path.
		 */
			
		try{
			Stack paths = new Stack(), sol = new Stack();					//Initialize 2 stacks, one for the solution and the other for the available nodes to try
			Node cur, s = graph.getNode(start), e = graph.getNode(finish);	//Initialize two nodes for the start and finish, and declare a third node
			boolean added;													//Declare a boolean variable for if a node was added
			Edge edge = null;												//Declare an Edge variable for use in the search
		
			sol.push(s);				//Push the start of the labyrinth onto the available paths
			s.setMark(true);			//Mark the start (so we dont read it to the stack)
		
			while(!sol.isEmpty()){	//Loop until all nodes are removed from the solution (The entrance does not lead to the exit)
			
				cur = (Node) sol.peek();	//Set the current node to the top of the solution stack
					
				if(cur == e){				//If the current node is the exit, return the iterator for the solution stack
					return sol.iterator();
				}
				else{						//Other wise we must search the current solution node for available paths
					
					Iterator it = graph.incidentEdges(cur);		//get the iterator of incident edges on the current node
					added = false;								//Set added to false
					
					while(it.hasNext()){			//Loop through all the incident edges
						edge = (Edge)it.next();			//Set the current edge to the next edge from the iterator
						cur = edge.secondEndpoint();	//Set the current node to the endpoint of the edge
						
						if(!cur.getMark()){							//If the node is not marked (is not in the stack already), check the following
							
							if(edge.getType() == "wall" && k > 0){		//If the edge is a wall and we can break it, do the following
								cur.setMark(true);							//Mark this node so we do not readd it
								paths.push(cur);							//Push this node onto paths
								added = true;								//Set added to true, as we added a node to the stack (found at least one path from the current solution node)
							}
							
							else if(edge.getType() == "hall"){			//If its a hall
								cur.setMark(true);							//Mark the node
								paths.push(cur);							//Push it onto paths stack
								added = true;								//Update added variable
							}
						}			
					}//End of while more nodes in iterator
						
					if(!added){	//If no nodes were added from the current solution path, we must backtrack
							
						//Pop solution (remove the current solution node as its a dead end) and get the edge between it and the new top (previous solution node)
						if(graph.getEdge((Node)sol.pop(), (Node)sol.peek()).getType() == "wall")	
							k++;				//If it was a wall, increment k so we can break a different wall
						
						//Backtrack as long both stacks have nodes in them and the top nodes are not adjacent (the node on the top is not connected to the next available path)
						while( sol.size() > 0 && paths.size() > 0 && !graph.areAdjacent((Node)sol.peek(), (Node)paths.peek()) ){
							Node tmpNode = (Node)sol.pop();							//Pop the top node from solution and set it to tmpNode
							Edge tmpEdge = graph.getEdge(tmpNode, (Node)sol.peek());//Get the edge between the popped node and the new top of the stack
							tmpNode.setMark(false);									//Unmark the node we popped, as we may need to traverse it later
							if(tmpEdge.getType() == "wall")							//If the edge between the two nodes was a wall, increment k as we can now break a different wall
								k++;
						}
							
						/*
						 * After the above loop is done, any nodes not connected to the top node of paths (next available pathway) are removed from the solution
						 */
							
						//If either stack is now empty (no new paths to try or every node in the solution stack didn't lead to the exit (including the start))
						if(paths.size() == 0 || sol.size() == 0)
							return null;	//return null, a solution could not be found
						
					}//End of backtracking
					
					/* Now we take the latest path found (top of paths stack) and explore it (push it onto solution and continue) */

					//Check if this path takes us through a wall
					//This check will never cause a negative k, as we do not add a path to the stack if we do not have enough 'bombs' and we regain them from backtracking
					if(graph.getEdge((Node)sol.peek(), (Node)paths.peek()).getType() == "wall")	//If the edge between the latest node found and the previous node is a wall
						k--;				//Decrement k
							
					sol.push(paths.pop());	//Pop paths and push it onto solution stack
					
					//Now we go back to the top and search from this newly added node
				}
			
			}//End of while solution stack is not empty	
			
			//If the solution stack was emptied and we did not return an iterator, no solution could be found
			return null;
			
		}//End of try
		catch(Exception e){	//Catch any exceptions thrown by the above code
			System.out.println("Error solving labyrinth");	//Print an error message
			return null;									//Return null
		}
		
	}//End of solve method
	
}
