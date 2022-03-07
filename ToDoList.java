/**
 * @author Alfaneti
 * A program that saves To-do list items of the user and allows for adding and removing
 * */
 
 import java.lang.ArrayIndexOutOfBoundsException;
 import java.io.FileNotFoundException;
 import java.io.File;
 import java.io.PrintWriter;
 import java.util.Scanner;
 import java.io.IOException;

 public class ToDoList {
 	private ToDoListItem[] toDoArray;
 	private int numItems = 0;
 	private int capacity = 5;

 	// constructor that initialises the to to list
 	public ToDoList() {
 		toDoArray = new ToDoListItem[capacity];
 	}

    /**Method to return the current number of items in the list*/
    int getNumItems() {
        return numItems;
    }

    /** Method to get the list of to-do items
     * @return  The array with the Todo list items*/
    ToDoListItem[] getArray() {
        return toDoArray;
    }

    /**Method to reset the array to empty*/
    void emptyList() {
        numItems = 0;
        toDoArray = new ToDoListItem[capacity];
    }

    /** method to get an item at a given index
     * @param i  the index for retrieving an item
     * @return  An item in the array*/
    ToDoListItem getItemAt(int i) {

        if (i >= numItems) throw new ArrayIndexOutOfBoundsException(); // negative
        else {
            return toDoArray[i];
        }
    }

    /** Method to remove an item
     * @param index  the index of the item to be removed*/
    void removeItemAt(int index) {

        try {
            ToDoListItem toRemove = getItemAt(index); // checking if the item is in the array

            if (index -1 == numItems) {
                toDoArray[index] = null;
            } else {
                for (int i = index; i<numItems-1; i++){
                    toDoArray[i] = toDoArray[i+1];
                }
            }
            numItems--;

        } catch (ArrayIndexOutOfBoundsException message) {
            throw message;
        }
    }

    /** Method to mark an item as done
     * @param index the index of the element to be marked as done*/
    void markAsDone(int index) {
        try {
            ToDoListItem toMark = getItemAt(index);
            toMark.markDone();

        } catch (ArrayIndexOutOfBoundsException message) {
            throw message;
        }

    }

    /** Method to mark an item as not done
     * @param index the index of the element to be marked as undone*/
    void markAsNotDone(int index) {
        try {
            ToDoListItem toMark = getItemAt(index);
            toMark.markNotDone();

        } catch (ArrayIndexOutOfBoundsException message) {
            throw message;
        }

    }

    /** Method for adding a new item to the list
     * @param item  the item to be be added to the to-do list*/
 	void addItem(String item) {
 		if (numItems >= toDoArray.length) helper();
        ToDoListItem newItem = new ToDoListItem(item);
        toDoArray[numItems] = newItem;
        numItems++;

    }

    /** Helper method to extend the list when the number of elements exceed the capacity*/
    private void helper() {
        capacity *= 2;
        ToDoListItem[] temp = new ToDoListItem[capacity];
        for (int i = 0; i<toDoArray.length; i++) 
            temp[i] = toDoArray[i];
        toDoArray = temp;
    }

    /**Showing the items in the array and those that are done*/
    void listItems() {
        int itemsDone = 0;

        for (int i = 0; i<numItems; i++) {

            if(toDoArray[i].isDone()) itemsDone++;
        }

        System.out.println("\nYour to-do list has " + numItems + " items, " + itemsDone + " of which are done.");
        for (int j = 0; j<numItems; j++) {

            System.out.println((j+1) + ") " + toDoArray[j]);
        }
    }

    /**Look for an item with the given keyword
     * @param theKeyWord  the word to be used for searching
     * @return  the index of the to-do item that contains the given key word or -1 when not found*/
    int find(String theKeyWord) {
        for (int i = 0; i<numItems; i++) {
            if (toDoArray[i].getTask().contains(theKeyWord)) return i;
        }
        return -1;
    }

    /**Finding an item using a keyword starting from a given index
     * @param theKeyWord  the keyword to use for searching
     * @param index   the position to start searching from
     * @return   the index of the item
    */
    int findNext(String theKeyWord, int index) {

        try {
            ToDoListItem tryGettingItem = getItemAt(index);

            for (int i = index; i<numItems; i++) {

                // looking for items that contains the given substring
                if (toDoArray[i].getTask().contains(theKeyWord)) return i;
            }
            return -1;

        } catch (ArrayIndexOutOfBoundsException message) {
            throw message;
        }
       
    }

    /**Writing the items in the Arrar 
     * @param fileName  the name to be used in saving the file
    */
    void writeListToFile(String fileName)  throws FileNotFoundException {

        File todoFile = new File(fileName + ".csv");
        PrintWriter writer = new PrintWriter(todoFile);

        for(int i = 0; i<numItems; i++) {
            if(toDoArray[i].isDone()) writer.printf("%s, done\n", toDoArray[i].getTask());
            else {
                writer.printf("%s, not done\n", toDoArray[i].getTask());
            }
        }
        writer.close();

    }

    /**Retrieving a list from the given file name
     *@param fileName  the filename where the items list is to be retrieved from
     */

    void readListFromFile(String fileName) throws IOException {

        numItems = 0;
        emptyList();

        // opening the file and loading it into the scanner
        File todoFile = new File(fileName + ".csv");
        Scanner reader = new Scanner (todoFile);
        reader.useDelimiter("\n");

        // reading the lines in the file
        while(reader.hasNext()) {
            String[] contents = reader.next().split(",");
            addItem(contents[0]);
            // setting item with done to true
            if (contents[1].trim().equals("done")) markAsDone(numItems-1);
        }
        reader.close();

    }

 }
