package project1;

import java.io.*;
import java.util.*;

class Collection {

	/////////////////////////////////////
	///////////// Private Variables//////
	/////////////////////////////////////
	private Dictionary dict;
	private Dictionary Collection[];
	private int newStepCounter;

	/////////////////////////////////////
	///////////// Constructor///////////
	/////////////////////////////////////
	public Collection(Dictionary x) {
		dict = x;
		int dictsize = x.getSize();
		Collection = new Dictionary[dict.maxSizeWord()];

		//this creates a dict of size 300000 for each dict in the Collection
		for (int i = 0; i < Collection.length; i++) {
			Collection[i] = new Dictionary(300000);
		}
		
		//this puts all the words from the Dict parameter and inserts
		//them in their corresponding word length dicts minus 1
		// i.e. : 'cat' will be moved to Collection[2]
		for (int j = 0; j < dictsize; j++) {
			String temp = dict.deleteLast();

			int length = temp.length();
			if (length > 0) {
				Collection[length - 1].setSorted(true);
				Collection[length - 1].insert(temp);
			}

			// x.insert(array[i]);
		}
		System.out.println();
	}

	/////////////////////////////////////
	///////////// Methods ///////////
	/////////////////////////////////////

	public void info() {
		for (int x = 0; x < Collection[0].getNbwords(); x++) {
			System.out.println(Collection[0].getWords()[x]);
		}
		System.out.println(
				"The collection named '" + dict.getName() + "' contains " + Collection.length + " dictionaries");
		System.out.println("dict --> size");
		int element;
		//prints out the sizes of each dict in Collection
		for (int i = 0; i < Collection.length; i++) {
			element = i + 1;
			System.out.println(element + " --> " + Collection[i].getSize());
		}
	}

	public void saveCollection() {
		//this method saves each dictionary in Collection in its own txt. files
		//this is very similiar to saveDictionary
		for (int i = 0; i < Collection.length; i++) {
			if (Collection[i].getWords().length > 0) {
				try {
					PrintWriter out = new PrintWriter(dict.getName() + "-" + (i + 1));
					for (int x = 0; x < dict.maxSizeWord(); x++) {
						out.println(Collection[x].getWords()[x]);
					}
					out.close();
				} catch (Exception E) {
					System.out.println("Error");
					E.printStackTrace();
				}
			}
		}
	}

	public boolean search(String s) {
		//this method returns true if the String parameter is found in the dictionary
		//with the corresponding length
		int length = s.length();
		for (int i = 0; i < Collection[length - 1].getSize(); i++) {
			if (dict.linearSearch(s)) {
				newStepCounter = dict.getSearchStepCounter();
				return true;
			}
		}
		return false;
	}

	public int getStep() {
		return newStepCounter;
	}

	public void setStep(int step) {
		newStepCounter = step;
	}

	public void crackLock(char[][] lock, int nbletter, int nboption) {
		//this method creates random words by choosing letters from the lock array
		//it prints all the words it finds and the number of words it finds
		
		Random rnd = new Random();
		int y; //random column int
		int counter = 0; //counts number of words
		String word = "";
		int c = (int) Math.pow(nboption, nbletter);
		int combos = c*10; 

		//runs the following for loop a certain number of times in order
		//to make up for repetitive words. 
		while (combos > 0) {
			//this is the for loop where I create a random word
			//it goes through each row of the lock array
			for (int row = 0; row < nbletter; row++) {
				y = rnd.nextInt(nboption); 	  //a random column is chosen each time
				word = word + lock[row][y]; //word is initialized as a blank string and it keeps
											  //adding each random character to create the word
			}
			//this uses the delete method in Dictionary.java
			//it finds the word, deletes it, and shifts the array
			//so there are not any nulls in the middle of the dictionary
			if(Collection[nbletter-1].delete(word))
			{
				System.out.println(word);
				counter++;
			}
			word = "";
			//this makes sure the forloop&if runs x combos times to find all possible word combinations
			combos--;
			}
		System.out.println("The number of words is " + counter);
		}

	public static void main(String[] args) {
		Dictionary d1 = new Dictionary(3);
		d1.loadDictionary("tiny.txt");

		Collection c1 = new Collection(d1);
		c1.info();
		/*
		 * String [] y = c1.Collection[0].getWords();
		 * 
		 * for (int x = 0; x < y.length; x++) System.out.println(y[x]);
		 */

	}
}
