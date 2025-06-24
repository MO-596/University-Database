package university;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classroom_Info allows users to interactively query a university Oracle database
 * to view information about courses and course sections.
 */
public class Classroom_Info {

	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		  int choice = 0;
	    String url = "[your url]"; 
	    boolean Repeat = true;
	    
	    try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement())
	    {
            System.out.println("\nConnected successfully.");
            
            // Prepare SQL queries for course and section lookups
            String coursesQuery = "select * FROM U_COURSES WHERE COURSE_NUMBER = ?";
            String sectionsQuery = "select * FROM U_SECTIONS WHERE SECTION_NUM = ?";
            
            // PreparedStatements for secure parameterized queries
            PreparedStatement CQ = conn.prepareStatement (coursesQuery);
            PreparedStatement SQ = conn.prepareStatement (sectionsQuery);
            
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, CQ,SQ); // Handle logic for selected choice
                
            }while(Repeat);
	    	
	    	
	    }catch (SQLException e) {
	    	System.out.println("Connection failed:");
	    	e.printStackTrace();
	    }
	    
	}
	
    /**
     * Handles logic based on the user's menu selection.
     * Executes appropriate SQL query and prints results.
     *
     * @param choice user selection (1 = course, 2 = section, 3 = exit)
     * @param CQ     PreparedStatement for courses
     * @param SQ     PreparedStatement for sections
     * @return whether the loop should repeat
     * @throws SQLException in case of query execution error
     */
    static boolean process(int choice, PreparedStatement CQ, PreparedStatement SQ ) throws SQLException
	{
	    boolean Repeat = true;
	    
	    String courseNumStr;
	    String sectionNumStr;
	    
	    switch(choice) {
	    	case 1: // view course info
	    		courseNumStr = readEntry("Enter Course Number: ");
	    		int courseNum = Integer.parseInt(courseNumStr);
	    		CQ.setInt(1, courseNum);
	    		ResultSet courseRs = CQ.executeQuery();
	    			
	    		if (courseRs.next()) {
	    			System.out.println("\nCourse Name: " + courseRs.getString("COURSE_NAME"));
	    			System.out.println("Course Number: " + courseRs.getInt("COURSE_NUMBER"));
	    			System.out.println("Description: " + courseRs.getString("DESCRIPTION"));
	    			System.out.println("Semester Hours: " + courseRs.getInt("SEMESTER_HOURS"));
	    			System.out.println("Course Level: " + courseRs.getInt("COURSE_LEVEL"));
	    			System.out.println("Offering Dept: " + courseRs.getInt("OFFERING_DEPT"));
	    		} 
	    		else
	    		{
	    			System.out.println("No course found with that number.");
	    		}   
	    			
	    		courseRs.close();
	    		break;	    				  
                
	    	case 2: // view section info
	    		sectionNumStr = readEntry("Enter Section Number: ");
	    		int sectionNum = Integer.parseInt(sectionNumStr);
	            SQ.setInt(1, sectionNum);
	            ResultSet sectionRs = SQ.executeQuery();
	                
	            if (sectionRs.next()) {
	                System.out.println("\nInstructor: " + sectionRs.getString("INSTRUCTOR"));
	                System.out.println("Semester: " + sectionRs.getString("SEMESTER"));
	                System.out.println("Year: " + sectionRs.getInt("YEAR"));
	                System.out.println("Course: " + sectionRs.getInt("COURSE"));
	                System.out.println("Section Number: " + sectionRs.getInt("SECTION_NUM"));
	            } 
	            else 
	            {
	                System.out.println("No section found with that number.");
	            }
	            
	            sectionRs.close();
	            break;	             	
	    		
	    	case 3: // exit program
	            System.out.println("Exiting, Thank you for you for using the app!");
	            System.out.println("\n------------------------\n");

	            Repeat = false;
	            break;
	    }
	    
	    return Repeat;
	}
	
    /**
     * Displays menu options to the user.
     */
	static void menuOptions() {
        System.out.println("\n--- MENU ---");
        System.out.println("Chose one of the following:");
        System.out.println("1. View Course by Course Number");
        System.out.println("2. View Section by Section Number");
        System.out.println("3. Exit");
	}
	
    /**
     * Reads and validates the user's numeric menu input.
     *
     * @param choice default value (unused now)
     * @return validated menu choice between 1 and 3
     */
	static int readInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 3) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 3");
		}
	}	
	
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
}
