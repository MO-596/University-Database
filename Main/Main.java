package Main;
import java.io.IOException;
import java.sql.SQLException;

import students.New_Student_Info_Program;
import students.Student_Info_Quieres;
import students.Students_Grade_Reports;
import university.Classroom_Info;
import university.Departments;

/**
 * The {@code Main} class serves as the entry point of the University Information Management System.
 * It provides a command-line interface for managing students, departments, and classroom/section data.
 * <p>
 * This program interacts with an Oracle database to perform operations such as:
 * <ul>
 *   <li>Adding and retrieving student records</li>
 *   <li>Viewing or creating department records</li>
 *   <li>Managing course sections and classrooms</li>
 * </ul>
 */
public class Main {
    /**
     * Main method that initiates the application.
     *
     * @param args command-line arguments (not used)
     * @throws SQLException if a database access error occurs
     */
	public static void main(String[] args) throws SQLException {

		int choice = 0;
	    boolean Repeat = true;            
           
	    // Interactive menu loop
        do {
        	menuOptions(); // Displays Menu options
            choice = readInput(choice); // Gets user input
            Repeat = process(choice); // Handle logic for selected choice
                
         }while(Repeat);	    
	    
	}
//////////////////////////////////////////////////////////////////////////////
    /**
     * Handles logic based on the main menu user's selection.
     *
     * @param choice the menu option chosen by user
     * @return true if the menu should repeat, false to exit
     * @throws SQLException if an error occurs while running database operations
     */
	static boolean process(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Navigates to the students sub menu options
		    	System.out.println("Chosen to manage Students....\n");
		    	Students();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to manage Departments....\n");
		    	Departments.run(); // Navigates to the departments sub menu & handles department-related tasks
	    		break;	    	

	    	case 3:
		    	System.out.println("Chosen to manage Courses/Sections....\n");
		    	Classroom_Info.run(); // Navigates to the course / section sub menu & operations
	    		break;	    	

	    	case 4:	
	            System.out.println("Exiting, Thank you for you for using the app!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}
	
//////////////////////////////////////////////////////////////////////////////

    /**
     * Handles the student submenu interactions.
     *
     * @throws SQLException if a database operation fails
     */
	static void Students() throws SQLException {
		int choice = 0;
	    boolean Repeat = true;   
	    
    	do {
    		studentsMenuOptions(); // Display student-related options
    		choice = studentReadInput(choice); // Gets user input
    		Repeat = studentsProcess(choice); // Process student menu input
    	}while(Repeat);
    	
	}
	
	
//////////////////////////////////////////////////////////////////////////////
    /**
     * Executes operations based on student submenu choice.
     *
     * @param choice the selected student menu option
     * @return true to continue, false to return to main menu
     * @throws SQLException if a database error occurs
     */
	static boolean studentsProcess(int choice) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Students
		    	System.out.println("Chosen to add new Students....\n");
		    	New_Student_Info_Program.run();
	    		break;	    	

	    	case 2:
		    	System.out.println("Chosen to look for students info....\n");
		    	Student_Info_Quieres.run();
	    		break;	    	

	    	case 3:
		    	System.out.println("Chosen to look for grade reports....\n");
		    	Students_Grade_Reports.run();
	    		break;	    	

	    	case 4:	
	            System.out.println("Exiting, Going back to Main Menu!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    return Repeat;
	}
	
//////////////////////////////////////////////////////////////////////////////

    /**
     * Displays menu options to the user.
     */
	static void menuOptions() {
        System.out.println("\n--- MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Manage Students.");
        System.out.println("2. Manage Departments.");
        System.out.println("3. Manage Courses/Sections.");
        System.out.println("4. Exit");
	}
	
//////////////////////////////////////////////////////////////////////////////
    /**
     * Displays students menu options to the user.
     */
	static void studentsMenuOptions() {
		System.out.println("\n--- Students MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. Add New Students.");
        System.out.println("2. Look for Student Info.");
        System.out.println("3. Look for Grade Reports.");
        System.out.println("4. Exit");
	}

//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 
     */
	static int readInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 4) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 4");
		}
	}	
	
	//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Validates and retrieves user input from the student submenu.
     *
     * @param choice a placeholder (unused)
     * @return the validated student menu option (1–4)
     */
	static int studentReadInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 4) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 4");
		}
	}	
	
	
//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Prompts the user and reads input from console.
     *
     * @param prompt the message to display
     * @return the trimmed string entered by the user
     */
	static String readEntry(String prompt) {
     	try{
     		StringBuffer buffer = new StringBuffer();
     		System.out.print(prompt);
     		System.out.flush();
     		int c =System.in.read();
     		while(c != '\n' && c != -1) {
     			buffer.append((char)c);
     			c = System.in.read();
     		}
     		return buffer.toString().trim();
     	}catch (IOException e) {
     		return "";
     	}
	}
	
//////////////////////////////////////////////////////////////////////////////
}
