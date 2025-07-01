package students;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * The {@code Students_Grade_Reports} class provides a command-line interface to
 * insert and query student grade reports in the `U_GRADE_REPORT` table within
 * an Oracle database.
 *
 * <p>Users can:
 * <ul>
 *   <li>Add new grade reports for students</li>
 *   <li>View grade reports for a specific section</li>
 *   <li>Exit the application</li>
 * </ul>
 *
 * <p>This class uses parameterized SQL queries via {@link PreparedStatement}
 * for secure and efficient database interaction.
 */
public class Students_Grade_Reports {
    /**
     * Starts the grade report management session.
     * Prompts the user for Oracle credentials, connects to the database,
     * and handles grade-related operations via menu choices.
     *
     * @throws SQLException if a database error occurs
     */
	public static void run() throws SQLException{
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
			
            // Prepare SQL queries for insertion and student lookups
		    String StudentQuery = "SELECT * FROM U_GRADE_REPORTS WHERE STUDENTS_NUM = ? ";
		    String SectionQuery = "SELECT * FROM U_GRADE_REPORTS WHERE SECTION_NUM = ?";
		    String insertQuery = "INSERT INTO U_GRADE_REPORT (STUDENT_NUM, SECTION_NUM, COURSE_NAME, COURSE_NUMBER, SEMESTER, YEAR, LETTER_GRADE, NUMERIC_GRADE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		    
            // PreparedStatements for secure parameterized queries
            PreparedStatement STQ = conn.prepareStatement (StudentQuery);
            PreparedStatement SEQ = conn.prepareStatement (SectionQuery);
            PreparedStatement insertGrade = conn.prepareStatement(insertQuery);
            
            // Interactive menu loop
		    do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, STQ, SEQ, insertGrade); // Handle logic for selected choice
                
            }while(Repeat);       
		}catch (SQLException e) {
	    	System.out.println("Connection failed:");
	    	e.printStackTrace();
	    }
	}

	//////////////////////////////////////////////////////////////////////////////
    /**
     * Executes logic for a selected menu choice.
     *
     * @param choice       menu selection
     * @param STQ          prepared statement for student-grade lookup
     * @param SEQ          prepared statement for section-grade lookup
     * @param insertGrade  prepared statement to insert grade report
     * @return true if the program should continue running
     * @throws SQLException if a query fails
     */
	 static boolean process(int choice, PreparedStatement STQ, PreparedStatement SEQ, PreparedStatement insertGrade ) throws SQLException
		{
		    boolean Repeat = true;
		    
		    switch(choice) {
	    	case 1: // Add grade report
		    	System.out.println("Chosen to add grade report by student number....\n");

		    	int studentNum = Integer.parseInt(readEntry("Enter Student Number: "));
                int sectionNumber = Integer.parseInt(readEntry("Enter Section Number: "));
                String courseName = readEntry("Enter Course Name: ");
                int courseNumber = Integer.parseInt(readEntry("Enter Course Number: "));
                String semester = readEntry("Enter Semester (e.g., Fall): ");
                int year = Integer.parseInt(readEntry("Enter Year: "));
                String letterGrade = readEntry("Enter Letter Grade (A-F): ");
                double numericGrade = Double.parseDouble(readEntry("Enter Numeric Grade (0-100): "));
                
                insertGrade.setInt(1, studentNum);
                insertGrade.setInt(2, sectionNumber);
                insertGrade.setString(3, courseName);
                insertGrade.setInt(4, courseNumber);
                insertGrade.setString(5, semester);
                insertGrade.setInt(6, year);
                insertGrade.setString(7, letterGrade);
                insertGrade.setDouble(8, numericGrade);
                
                int rows = insertGrade.executeUpdate();
                
                if (rows > 0) {
                    System.out.println("Grade report added successfully.");
                } else {
                    System.out.println("Insert failed.");
                }
                
	    		break;	    				  
                
	    	case 2: // view section info
		    	System.out.println("Chosen to look at students grade by section number....\n");

		    	boolean found = false;
	    		String sectionNumStr = readEntry("Enter Section Number: ");
	    		
	    		if (!sectionNumStr.matches("\\d+")) {
	    			System.out.println("Invalid input. Section number must be numeric.");
	                break;
	            }
	    		 
	    		int sectionNum = Integer.parseInt(sectionNumStr);
	    		
	    		SEQ.setInt(1, sectionNum);
	    		
	            ResultSet sectionRs = SEQ.executeQuery();
	                
	            while (sectionRs.next()) {
	            	found = true;
	            	
                    System.out.println("\nStudent Number: " + sectionRs.getInt("STUDENT_NUM"));
                    System.out.println("Course: " + sectionRs.getString("COURSE_NAME"));
                    System.out.println("Grade (Letter): " + sectionRs.getString("LETTER_GRADE"));
                    System.out.println("Grade (Numeric): " + sectionRs.getDouble("NUMERIC_GRADE"));
                    System.out.println("Semester: " + sectionRs.getString("SEMESTER"));
                    System.out.println("Year: " + sectionRs.getInt("YEAR"));
                    System.out.println("---------------------------");
	            } 
	            
	            if(!found) 
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
        System.out.println("1. Add Grade by Student Number");
        System.out.println("2. View Grades by Section Number");
        System.out.println("3. Exit");
	}
	
//////////////////////////////////////////////////////////////////////////////
	
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

}
