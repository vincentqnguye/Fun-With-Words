package project1;

import java.io.*;
import java.util.*;

class Dictionary {

	/////////////////////////////////////
	///////////// Private Variables//////
	/////////////////////////////////////

	// private variables related to the dictionary
	private String name; // name of the dictionary
	private int nbwords = 0; // number of words
	private String[] words; // array of words
	private int maxsize; // max fixed size of the array
	private boolean sorted = false; // flag for sorted/unsorted

	// private variables related to the search for the dictionary
	private int searchIndex = 0;
	private int searchStepCounter = 0;
	private String file;

	/////////////////////////////////////
	///////////// Constructors///////////
	/////////////////////////////////////

	public Dictionary(int maxsize) {
		this.maxsize = maxsize;
		words = new String[maxsize];
	}

	////////////////////////////////////////////////////////////
	///////////// Methods //////////////////////////////////////
	////////////////////////////////////////////////////////////

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNbwords() {
		return nbwords;
	}

	public void setNbwords(int nbwords) {
		this.nbwords = nbwords;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public int getSearchIndex() {
		return searchIndex;
	}

	public void setSearchIndex(int searchIndex) {
		this.searchIndex = searchIndex;
	}

	public int getSearchStepCounter() {
		return searchStepCounter;
	}

	public void setSearchStepCounter(int searchStepCounter) {
		this.searchStepCounter = searchStepCounter;
	}

	////////////////////////////////////////////////////////////
	///////////// Methods //////////////////////////////////////
	////////////////////////////////////////////////////////////

	public boolean isFull() {
		if (getMaxsize() <= nbwords) // if the maxsize is less than or equal
			return true; // to the number of words, then the dict is full
		else
			return false;
	}

	public void info() {
		System.out.println("The dictionary named '" + this.name + "' is of size " + this.nbwords + ".");
		// Prints if dict is sorted and if it is full
		if (isSorted())
			System.out.println("The dictionary is sorted.");
		else
			System.out.println("The dictionary is not sorted.");
		if (isFull())
			System.out.println("Warning: The dictionary is full.");
	}

	public void loadDictionary(String fn) {
		// Creates readers to read in words from the dictionary.txt and
		// puts inside array called words
		FileReader fr = null;
		this.setName(fn);
		BufferedReader br1 = null;
		try {
			String fileName = fn;
			setFileName(fn);
			fr = new FileReader(fileName);
			br1 = new BufferedReader(fr);
			for (int x = 0; x < maxsize; x++) {
				String w = br1.readLine();

				if (w == null) // if words at the position does not exist
					break; // it stops loading the dict array
				else {
					nbwords++; // the number of words increases each time there
								// is a word
					words[x] = w;
				}
			}
		} catch (Exception E) {
			System.out.println("File cannot be found");
		}
	}

	public void saveDictionary(String fn) {
		// this method saves the updated dict array back into
		// a new .txt file
		try {
			PrintWriter out = new PrintWriter(this.file + fn);
			for (int x = 0; x < maxsize; x++) {
				if (words[x] != null) {
					out.println(words[x]);
				} else {
					break;
				}
			}
			out.close();
		} catch (Exception E) {
			System.out.println("Error");
			E.printStackTrace();
		}
	}

	public void setFileName(String f) {
		this.file = f;
	}

	public String getFileName() {
		return this.file;
	}

	public void sortBubble() {
		int in, out;
		String temp;

		for (out = maxsize - 1; out > 0; out--) // outer loop (backward)
		{

			for (in = 0; in < out; in++) // inner loop (forward)
			{
				if (words[out] == null) {
					break;
				}
				if (words[in].compareToIgnoreCase(words[in + 1]) > 0) // out of
																		// order
																		// // s
																		// //
																		// them
				{
					temp = words[in];
					words[in] = words[in + 1];
					words[in + 1] = temp;
				}
			}
		}
		this.setSorted(true);
	}

	public void sortSelection() {
		int in, out, min;
		String temp; // temp variable
		for (out = 0; out < this.maxsize - 1; out++) // outer loop
		{
			// find the minimum item between [out+1,N-1]
			min = out; // initialize minimum index
			for (in = out + 1; in < this.maxsize; in++) {// inner loop
				if (words[in] == null) {
					break;
				}
				if (words[in].compareTo(words[min]) < 0)
					min = in; // update minimum index
				// swap item ”array[min]” with item ”array[out]”
				temp = words[out];
				words[out] = words[min];
				words[min] = temp;
			}
		} // end outer loop
	} // end selectionSort()

	public void sortInsertion() {
		int in, out;
		String temp; // temp variable
		for (out = 1; out < maxsize; out++) // outer loop – select key
		{
			if (words[out] == null)
				break;
			temp = words[out]; // save in memory select key item
			in = out; // start shifting at out
			while (in > 0 && (words[in - 1].compareToIgnoreCase(temp) > 0))// shift
																			// until
																			// key-item
																			// in
																			// position
			{
				words[in] = words[in - 1]; // shift up
				in--; // go down one position
			}
			words[in] = temp; // insert select key item
		} // end outer loop
	} // end insertionSort()

	public String getRandomWord() {
		// finds and returns a random word from the words array
		Random rnd = new Random();
		int index = rnd.nextInt(nbwords - 1); // select random number in
												// [0:out-1]
		return words[index];
	}

	public boolean linearSearch(String x) {
		// this method searches a sorted dict array for the word in the
		// parameter
		// if it is found in the dict array, it returns true
		setSearchStepCounter(0);
		for (int i = 0; i < nbwords; i++) {
			increaseStep();
			if (words[i].equals(x)) {
				searchIndex = i;
				return true;
			}
		}
		return false;
	}

	public boolean binarySearch(String term) {
		setSearchStepCounter(0);
		int lowerBound = 0;
		int upperBound = nbwords - 1;
		int curIn;

		while (true) {
			increaseStep();
			curIn = (lowerBound + upperBound) / 2;
			if (words[curIn].compareToIgnoreCase(term) == 0) {
				this.setSearchIndex(curIn);
				searchIndex = curIn;
				return true; // found it
			} else if (lowerBound > upperBound) {
				searchIndex = lowerBound;
				return false; // can't find it
			} else // divide range
			{
				if (words[curIn].compareToIgnoreCase(term) < 0) {
					lowerBound = curIn + 1; // in upper half
				} else {
					upperBound = curIn - 1; // in lower half
				}
			} // end else divide range
		} // end while
	}

	public void increaseStep() {

		this.searchStepCounter++;
	}

	public int getStep() {
		return this.searchStepCounter;
	}

	public void sortEnhancedInsertion() {
		// TODO Auto-generated method stub

	}

	public int getSize() {
		return nbwords;
	}

	public String deleteLast() {
		// this method deletes the last word in the dict array
		// it returns the word that was deleted
		if (nbwords > 0) {
			String temp = words[nbwords - 1];
			words[nbwords - 1] = null;
			this.setNbwords(nbwords - 1);
			return temp;
		} else {
			return "";
		}
	}

	public void insert(String x) {
		// this method inserts the string parameter in the correct position
		// in the sorted array. the number of words is increased by 1 regardless
		if (this.nbwords == 0) {
			words[0] = x;
			this.setNbwords(nbwords + 1);
			return;
		} else if (isSorted()) {
			binarySearch(x);
			// searchIndex keeps track of the position where the word belongs
			int temp = searchIndex;

			// this moves all the words to the right of the word inserted
			// over 1
			for (int i = nbwords; i > temp; i--) {
				words[i] = words[i - 1];
			}
			words[temp] = x;
			this.setNbwords(nbwords + 1);
		} else if (isSorted() == false) {
			words[nbwords] = x;
			this.setNbwords(nbwords + 1);
		}
	}

	public int maxSizeWord() {
		// this method finds the word with the max size and returns the size
		int max = 0;
		for (int i = 0; i < nbwords; i++) {
			if (max < words[i].length()) {
				max = words[i].length();
			}
		}
		return max;
	}

	public boolean delete(String x) {
		// this method finds and deletes the String parameter
		// then shifts all the words to the right, to the left
		if (binarySearch(x)) {
			int temp = searchIndex;
			words[temp] = null;

			for (int i = temp; i < getSize(); i++) {
				words[i] = words[i + 1];
			}

			this.setNbwords(nbwords - 1);
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Dictionary d1 = new Dictionary(6);
		Dictionary d2 = new Dictionary(3);
		d1.loadDictionary("tiny.txt");
		d2.loadDictionary("tiny.txt");

		// d1.getWords();
		// d1.sortBubble();

		/*
		 * for (int x = 0; x < d1.words.length; x++)
		 * System.out.println(d1.words[x]);
		 * 
		 * System.out.println();
		 * 
		 * d1.sortBubble(); for (int x = 0; x < d1.words.length; x++)
		 * System.out.println(d1.words[x]);
		 * 
		 * d1.linearSearch("pipe");
		 * 
		 * System.out.println(d1.getStep());
		 */
		d1.sortBubble();
		while ((d2.getSize() > 0) && !d1.isFull()) {
			d1.insert(d2.deleteLast());

		}

		for (int x = 0; x < d1.words.length; x++)
			System.out.println(d1.words[x]);

	}
}
