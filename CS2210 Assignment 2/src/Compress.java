//Compress Algorithm
//Written by Ben Dumais, 250669195, for CS2210 Assignment 2
import java.io.*;

public class Compress {

	public static void main(String[] args){
		
		final int TABLE_SIZE = 4093;									//Table size: 4093 is the highest prime number less than 4096, the specified size of the table
		Dictionary dictionary = new Dictionary(TABLE_SIZE);				//Dictionary object to hold dictionary entries
		DictEntry newEntry;												//DictEntry object for use in compression algorithm - holds a key and a code for that key
		MyOutput myOutput = new MyOutput();								//MyOutput object to write to output file
		
		String inputFile = args[0], outputFile = inputFile + ".zzz";	//variables to store input and output file names
		int bytes;														//pb variable from description
		String str="", str2="";									//p and p prime variables from description, as well a variable for the previous string
		
		for (int i=0;i<256;i++){			//Loop through values of i from 0 to 255
			char ch = (char)i;						//Set ch to the char representation of i
			String k = "" + ch;						//Add ch to a blank string
			DictEntry entry = new DictEntry(k,i);	//Create a new Dictionary Entry with the string and value of i 
			dictionary.insert(entry);							//Add this entry to the dictionary
		}
		
		int nCode = dictionary.numElements();							//The next available code to be added into the dictionary
		
		
		//Try the algorithm
		try{
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(inputFile));						//Create InputStream
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(outputFile));					//Create OutputStream
				
			str += (char)inStream.read();	//Read first byte into the string
			
			while((bytes = inStream.read()) != -1){		//read byte and set bytes to be equal to it: if it is not -1 (input file is done) then continue

				str2 = str;					//Holds previous value of string
				str += (char)bytes;			//Holds the current value of string
				
				//If the current value of the string is not found, proceed. Otherwise, keep adding bytes to the string
				if(dictionary.find(str)==null){
				
					/* At this point, str2 contains the longest sequence of found characters in the dictionary and
					 * str is str2 with the latest character which made it not found
					 * so we want to output str2's code to the output file and create a new entry for str
					 */
					
					/*
					 * Here the algorithm should write the integer code to the output file using the MyOutput class that was provided to us.
					 * However despite my efforts the output does not write anything and I could not get it to work as intended, and I am
					 * using it properly as far as I know.
					 * 
					 * Specifically: using test1 or test3 will not write anything to the file, and will result in just a blank .zzz file as the output.
					 * test2 works but will cut off 3kb short: when decompressing and comparing to the original file, the files are identical but for
					 * the decompressed version cutting off early
					 * 
					 * I have tried using the flush method, several different implementations for writing the output and changing restrictions
					 * such as the number of entries in the Dictionary: none have worked. When creating a test file that contains the aaabbbb string from the example,
					 * the algorithm processes and outputs the exact same thing as described with the same integer codes (seen by uncommenting the System.out calls)
					 * but still will not write to the file. To see what I mean please use test2 to compress and compress, then test the files with the cmp program
					 * 
					 */
					
					//System.out.print(dictionary.find(str2).getCode()+ " ");
					myOutput.output(dictionary.find(str2).getCode(), outStream);	//Output str2's code to the output file
				
					if(dictionary.numElements() <= TABLE_SIZE){									//If the dictionary has less entries than the table size, continue
						newEntry = new DictEntry(str, nCode);					//Create a new DictEntry with str and the next available code
						dictionary.insert(newEntry);							//Add newEntry to the dictionary
					}
					
					nCode++;												//Increment nCode
					str2 = "";												//Clear str2's value
					str = "" + (char)bytes;									//Set str's value to the latest character read
				}	
					
			}
			
			//When algorithm terminates, write the current string to output
			
			//System.out.print(dictionary.find(str).getCode());
			myOutput.output(dictionary.find(str).getCode(), outStream);		//output whatever is left in the string (ensure the last bytes were output)
			myOutput.flush(outStream);										//Flush output (ensure all the buffer is output)
			outStream.close();
			inStream.close();												//Close input stream			
		}
		//If an exception is thrown, catch it and print an appropriate error message
		catch (FileNotFoundException e) {
			System.out.println("Error: Input file not found, please check program arguments");
		}
		catch (IOException e){
			System.out.println("Error: Input/Output exception caught");
		}
		
	}//End of Main
	
}//End of Compress
