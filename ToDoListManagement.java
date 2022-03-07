/** Program that allow the user to manage their to-do list using the command line*/
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ToDoListManagement {
	

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		ToDoList myToDo = new ToDoList();
		System.out.println("Hello!  This application allows you to manage your to-do list.");
		myToDo.listItems(); // showing the number of items in the to do list
		menu(); // the available operations
		userInteraction(myToDo); 

	}

	/** Method to return the functions the program supports*/
	static void menu() {

		System.out.println("Type view to view your to-do list");
		System.out.println("Type add followed by the item description to add an item to the list");
		System.out.println("Type remove followed by the item number to remove an item from the list");
		System.out.println("Type mark-done followed by the item number to mark an item as done ");
		System.out.println("Type mark-not-done followed by the item number to mark an item as not done");
		System.out.println("Type find and a keyword to find all items containing the given keyword");
		System.out.println("Type save followed by filename to save the current to do list");
		System.out.println("Type retrieve followed by filename to use a To-do list you saved");
		System.out.println("Type quit to end the program.");

	}

	/**Method to interact with the user and call required functions*/
	static void userInteraction(ToDoList myToDo) throws FileNotFoundException, IOException {

		Scanner entry = new Scanner(System.in);

		while (true) {

			String option = entry.nextLine();

			// seperating the input to get the function and the parameter
			String[] optionArray = option.split(" ");

			// the first word is the function to be perfommed
			String func = optionArray[0].toLowerCase();

			String theParameter = "";
			for (int i = 1; i<optionArray.length; i++) {
				theParameter += " " + optionArray[i];
			}
			theParameter = theParameter.trim(); // removing blank spaces on the parameter

			// Exiting the program when quit is entered
			if (func.equals("quit")) {
				System.out.println("GoodBye!");
				System.exit(0);
			}

			// adding an item to the to do list
			if (func.equals("add")) {
				myToDo.addItem(theParameter);

			// viewing the items in the list
			} else if (func.equals("view")) {
				myToDo.listItems();

			// showing the options available
			}  else if (func.equals("menu")) {
				menu();

			// removing an item from the to-do list
			} else if (func.equals("remove")) {
				myToDo.removeItemAt(Integer.parseInt(theParameter) - 1);

			// marking an item as done
			} else if (func.equals("mark-done")) {
				myToDo.markAsDone(Integer.parseInt(theParameter)-1);

			// marking an item as not done
			} else if (func.equals("mark-not-done")) {
				myToDo.markAsNotDone(Integer.parseInt(theParameter)-1);

			// saving the to do list to a file
			} else if (func.equals("save")) {
				myToDo.writeListToFile(theParameter);
				System.out.println("File successfully save in: " + theParameter + ".csv");

			// loading items from a file
			} else if (func.equals("retrieve")) {
				myToDo.readListFromFile(theParameter);
				System.out.println("To-do List from : [" + theParameter + ".csv]" + " was successfully loaded.");
				myToDo.listItems();
			} 
			// finding items with the given substring
			else if (func.equals("find")) {
				int index = myToDo.find(theParameter);
				String indexes = "";

				// checking for all occurrences with the given substring/keyword
				while (index != -1) {
					indexes += index;
					try {
						index = myToDo.findNext(theParameter, index + 1);
					} catch (ArrayIndexOutOfBoundsException e) {
						break;
					}
					
				}
				if(indexes.length() == 0) {
					System.out.println("Not element found");
					continue;
				}

				String[] indexArray = indexes.split("");
				for(int i = 0; i<indexArray.length; i++) {
					int itm = Integer.parseInt(indexArray[i]);
					System.out.println((itm+1) + ") " + myToDo.getArray()[itm]);
				}
			}

			System.out.println("DONE!!! Type 'quit' to end or 'menu' if you have forgetten the supported functions");

		}
		
	}
}


