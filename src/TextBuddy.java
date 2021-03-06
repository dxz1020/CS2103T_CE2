import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/**
 * TextBuddy CE2
 * TextBuddy version 2: added sort and search functions
 * 
 * TextBuddy is a text-based CMD style TODO application that supports:
 * 1) Adding a new task : add <task>
 * 2) Deleting a task : delete <task number>
 * 3) Displaying a task : display
 * 4) Clearing all task : clear
 * 5) Sorting of tasks : sort
 * 6) Searching tasks : search <keyword>
 * 
 * @author Duan Xu Zhou A0108453J
 *
 */

public class TextBuddy {
	private static final String MESSAGE_MISSING_ARGUMENTS = "ERROR: %s.";
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use";
	private static final String MESSAGE_ADD = "added to %s: \"%s\"";
	private static final String MESSAGE_DELETE = "deleted from %s: \"%s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %s";
	private static final String MESSAGE_EMPTY_FILE = "%s is empty";
	private static final String MESSAGE_DISPLAY = "%d. %s";
	private static final String MESSAGE_COMMAND = "command: ";
	private static final String MESSAGE_SORT = "tasks sorted";
	private static final String MESSAGE_INVALID_SEARCH="no matching results";
	private static final String MESSAGE_NO_INPUT = "no input";
	private static final String MESSAGE_INVALID_INDEX = "invalid index";
	private static final int INDEX_OUT_OF_BOUND = -1;
	
	//indexing for task list starts from 1
	private static final int INDEX_CORRECTION = 1;

	//data structure to store text file
	private static ArrayList<String> data;

	private static File file;
	private static Scanner inputScanner;

	//exit status flag, default is true to let program run
	private static boolean isNotExit = true;

	public static void main(String[] args) {
		verifyArgument(args);
		createNewFile(args[0]);
		showWelcomeMessage();
		acceptUserCommandsUntilExit();
	}
	
	/**This method will check for filename parameter, return error and end program if parameter is not detected
	 * @param String[] args from command line
	 */
	private static void verifyArgument(String[] args) {
		if (args.length == 0) {
			displayMessage(formatMessage(MESSAGE_MISSING_ARGUMENTS, "MISSING ARGUMENTS"));
			System.exit(0);
		}
	}

	/**This method will setup textbuddy to create files for storing of data and load existing files
	 * @param filename given by String[] args
	 */
	public static void createNewFile(String filename) {
		createIOConstruct(filename);
		readFromFile(file);
	}

	private static void showWelcomeMessage() {
		displayMessage(formatMessage(MESSAGE_WELCOME, file.getName()));
	}

	/**This method will start a loop to take in user input
	 * by prompting for commands and execute the commands accordingly
	 * until the 'exit' command is detected which would terminate the loop and end the program 
	 */
	private static void acceptUserCommandsUntilExit() {
		String feedbackMessage=new String();
		while (isNotExit) {
			String userInput=scanForUserInput();
			feedbackMessage=executeCommand(userInput);
			saveFile();
			displayMessage(feedbackMessage);
		}
	}

	private static void createIOConstruct(String filename) {
		data = new ArrayList<String>();
		file = new File(filename);
		inputScanner = new Scanner(System.in);
	}

	private static void readFromFile(File file) {
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String temp;
			while ((temp = fileReader.readLine()) != null) {
				data.add(temp);
			}
			fileReader.close();
		}catch (IOException e) {
		}
	}

	private static String scanForUserInput(){
		displayMessage(MESSAGE_COMMAND);
		String userInput = inputScanner.nextLine().trim();
		return userInput;
	}

	/**
	 * This method will interpret the user commands and parse the commands to be executed to the relevant method functions
	 * @param String input entered by the user
	 * @return String feedback message
	 */
	public static String executeCommand(String input) {
		String userDataInput = null; //for the case of <display> and <sort> commands, no user data input field is given
		String[] tokenizer = input.split(" ",2);
		String userCommand=tokenizer[0];

		if(tokenizer.length>1){
			userDataInput=tokenizer[1];
		}

		switch (userCommand) {
		case "add" :
			return addToFile(userDataInput);
		case "display" :
			return displayFileContent();
		case "delete" :
			return deleteTask(userDataInput);
		case "clear" :
			return clearAllTask();
		case "sort" :
			return sortTask();
		case "search" :
			return searchTask(userDataInput);
		case "exit" :
			exitProgram();
		default :
			throw new Error(); 
		}
	}

	private static String addToFile(String input) {
		if (input == null) {
			return MESSAGE_NO_INPUT;
		}
		data.add(input);
		return formatMessage(MESSAGE_ADD, file.getName(), input);
	}

	private static String displayFileContent() {
		if (data.isEmpty()) {
			return formatMessage(MESSAGE_EMPTY_FILE, file.getName());
		}
		String output=new String();
		for (int i = 0; i < data.size()-1; i++) {
			output+=formatMessage(MESSAGE_DISPLAY, i + INDEX_CORRECTION, data.get(i));
			output+="\n";
		}
		output+=formatMessage(MESSAGE_DISPLAY,data.size(),data.get(data.size()-1)); //we do not want to add newline to last line of task
		return output;
	}

	private static String deleteTask(String input) {
		if (data.isEmpty()) {
			return formatMessage(MESSAGE_EMPTY_FILE, file.getName());
		}
		int index = getIndex(input);
		return deleteContentAtIndex(index);
	}

	private static String clearAllTask() {
		data.clear();
		return formatMessage(MESSAGE_CLEAR, file.getName());
	}

	private static String sortTask() {
		if(data.isEmpty()){
			return formatMessage(MESSAGE_EMPTY_FILE, file.getName());
		}
		Collections.sort(data,String.CASE_INSENSITIVE_ORDER);
		return MESSAGE_SORT;
	}

	private static String searchTask(String input) {
		boolean isFound=false;
		String output=new String();
		if(data.isEmpty()){
			return formatMessage(MESSAGE_EMPTY_FILE, file.getName());
		}
		for(int i=0;i<data.size();i++){
			String task=data.get(i);
			if(task.contains(input)){
				isFound=true; //flag keyword as found
				int taskIndex=i+INDEX_CORRECTION;
				output+=taskIndex+". "+task+"\n";
			}
		}
		if(isFound){
			return output;
		}else{
			return MESSAGE_INVALID_SEARCH;
		}
	}

	private static String deleteContentAtIndex(int index) {
		if (index == INDEX_OUT_OF_BOUND) {
			return MESSAGE_INVALID_INDEX;
		}
		String content = data.get(index);
		data.remove(index);
		return formatMessage(MESSAGE_DELETE, file.getName(), content);
	}

	private static void saveFile() {
		try {
			BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			for (String task : data) {
				fileWriter.write(task);
				fileWriter.newLine();
			}
			fileWriter.close();
		} catch (IOException e) {
		}
	}

	private static void exitProgram() {
		inputScanner.close();
		isNotExit = false;
		System.exit(0);
	}

	private static int getIndex(String input) {
		try {
			return Integer.parseInt(input) - INDEX_CORRECTION;
		} catch (NumberFormatException e) {
		}
		return INDEX_OUT_OF_BOUND;
	}

	private static void displayMessage(String message) {
		System.out.println(message);
	}

	private static String formatMessage(String message, Object... args) {
		return String.format(message, args);
	}
	public static int getFileSize(){
		return data.size();
	}
}