package cgm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	
	/**
	 * 
	 * Read p_filename file at p_line line
	 *
	 * @param p_filename
	 * @param p_line
	 * @return Text found in p_filename or empty string
	 */
	static String readFile(String p_filename, int p_line) {
		File file = new File("./"+p_filename);	// Linux user
		String line = new String();
		
		if(!file.exists()) {
			System.out.println("File: " + p_filename + " do not exist");
			return line;
		}
		else {
			try {
				FileReader  reader = new FileReader(file);
				BufferedReader buff = new BufferedReader(reader);
				int i = 0;
				
				while(line != null) {

					line = buff.readLine();

					if(i == p_line) {
						break;
					}
					else {
						i++;
					}
				}
				
				buff.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return line;
	}

	/**
	 *
	 * Read p_filename file searching for p_text string
	 *
	 * @param p_filename
	 * @param p_text
	 * @return line where p_text string was found or -1
	 */
	static int readFile(String p_filename, String[] p_text) {
		File file = new File("./"+p_filename);	// Linux user
		
		if(!file.exists()) {
			System.out.println("File: " + p_filename + " do not exist");
			return -1;
		}
		else {
			try {
				FileReader  reader = new FileReader(file);
				BufferedReader buff = new BufferedReader(reader);
				int countLine = 0;
				String tmp = String.join(" ", p_text);
				tmp = tmp.concat(" ");

				String line = buff.readLine();

				while(line != null) {
					if(line.equals(tmp) == true) {
						buff.close();
						return countLine;
					}
					else {
						countLine++;
					}

					line = buff.readLine();
				}
				
				buff.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return -1;
	}

	/**
	 *
	 * Let the user write a sentence with question mark, then find if an answer(s) is/are available
	 *
	 */
	static void askQuestion() {
		Scanner sc = new Scanner(System.in);
		String input = new String();
		
		try {
			System.out.println("Ask your question:");
			input = sc.nextLine();
		} catch(NoSuchElementException e ) {
			e.printStackTrace();
		}
		
		String[] tmp = input.split(" ");
		int line = readFile("questions.txt", tmp);
		
		if(line == -1) {
			System.out.println("The answer to life, universe and everything is 42.");
		}
		else {
			String answer = readFile("answers.txt", line);
			
			if(answer.isEmpty() == true) {		// Question not found
				return;
			}
			
			String[] tmp2 = answer.split(" ");
			
			for(String str: tmp2) {
				System.out.println(str);
			}
		}
	}

	/**
	 *
	 * Write p_text string in p_file file
	 *
	 * @param p_file
	 * @param p_text
	 *
	 */
	static void writeFile(File p_file, String[] p_text) throws IOException{
		FileWriter  writer = new FileWriter(p_file, true);
		BufferedWriter buff = new BufferedWriter(writer);
			
		for(String str: p_text) {
			buff.write(str);
			buff.write(" ");
		}
		buff.newLine();

		buff.close();
		writer.close();
	}

	/**
	 *
	 * Save p_text string in p_filename file
	 *
	 * @param p_filename
	 * @param p_text
	 */
	static void saveFile(String p_filename, String[] p_text) {
		File file = new File("./"+p_filename);	// Linux user
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				try {
					writeFile(file, p_text);
				} catch(IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("Impossible to create the file " + p_filename);
			}
		}
		else {
			try {
				writeFile(file, p_text);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * Check if user input fill answer condition
	 *
	 * @param p_filename
	 * @param p_pos
	 * 
	 * @return -1 if failed, 0 otherwise
	 */
	static int checkAnswer(String p_str) {
		
		if(p_str.indexOf('"') != 0 && p_str.lastIndexOf('"') != p_str.length()-1) {	// If answer is not between ""
			System.out.println("Answer should be between \"\"");
			return -1;
		}
		
		if(p_str.length() > 257) {	// If answer length is > 255
			System.out.println("Answer(s) length is maximum 255 chars !");
			return -1;
		}
		return 0;
	}
	
	/**
	 *
	 * Check if user input fill question condition
	 *
	 * @param p_filename
	 * @param p_pos
	 * @return -1 if failed, 0 otherwise
	 */
	static int checkQuestion(String p_str, int p_pos) {
		int len = p_str.length();
		
		if(p_pos == -1) {				// If there is no question mark
			System.out.println("That is not even a question !");
			return -1;
		}
		
		if(p_pos == (len-1)) {			// If there is no answer
			System.out.println("Question should have at lease one answer !");
			return -1;
		}
		
		if(p_pos > 255) {			// If question length is > 255
			System.out.println("Question length is maximum 255 chars !");
			return -1;
		}
		
		return 0;
	}
	
	/**
	 * Let user add question and answer to database
	 *
	 * @return -1 if failed, 0 otherwise
	 */
	static int addQuestion() {
		Scanner sc = new Scanner(System.in);
		String input = new String();
		String[] question = new String[255];
		ArrayList<String> str2 = new ArrayList<String>();
		
		try {
			System.out.println("Question must finish by '?' and must have at least one answer");
			System.out.println("Input format: Question ? Answer_1 Answer_2 Answer_3");
			input = sc.nextLine();
		} catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		
		int posQuestion = input.indexOf('?');
		int res = checkQuestion(input, posQuestion);
		
		if(res == 0) {
			String tmp = input.substring(0, posQuestion + 1);
			question = tmp.split(" ");
		}
		else {
			return -1;
		}
		
		String str = new String();
		
		if(input.charAt(posQuestion + 1) == ' ') {
			str = input.substring(posQuestion + 2);
		}
		else {
			str = input.substring(posQuestion + 1);
		}
		
		String[] tmp = str.split(" ");

		for(int i = 0; i < tmp.length; i++) {
			
			res = checkAnswer(tmp[i]);
			
			if(res == 0) {
				tmp[i] = tmp[i].substring(1);
				tmp[i] = tmp[i].substring(0, tmp[i].length()-1);
				
				str2.add(tmp[i]);
			}
		}
		
		String[] answer = new String[str2.size()];
		
		for(int i = 0; i < str2.size(); i++) {
			answer[i] = str2.get(i);
		}
		
		saveFile("questions.txt", question);
		saveFile("answers.txt", answer);
		
		return 0;
	}

	/**
	 *
	 * Input Menu
	 *
	 * @return User choice
	 */
	static int inputMenu() {
		Scanner sc = new Scanner(System.in);
		int input = 0;

		
		try {
			System.out.println("\nWrite the number corresponding at your choice (1 2 3):");
			input = Integer.parseInt(sc.nextLine());
		} catch(NumberFormatException e) {
			System.out.println("Wrong input");
		}

		return input;
	}
	
	/**
	 *
	 * Menu
	 *
	 */
	static void menu() {
		boolean on = true;
		int menuInput = -1;

		while(on) {
			System.out.println("\n1. Ask Question");
			System.out.println("2. Add Question and answer");
			System.out.println("3. Quit");
			menuInput = inputMenu();

			if(menuInput == 1) {
				askQuestion();
			}
			if(menuInput == 2) {
				addQuestion();
			}
			if(menuInput == 3) {
				on = false;
			}
			menuInput = -1;
		}
	}
	
	public static void main(String[] args) {
		menu();		
	}
}
