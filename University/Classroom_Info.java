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
 * to view information about courses and sections.
 * Features include:
 *   Viewing course information by course number
 *   Adding new courses with input validation
 *   Viewing section information by section number
 *   Adding new course sections
 *
 * Users are prompted for database credentials and menu choices
 * to interactively query or update the U_COURSES and U_SECTIONS tables.
 */
public class Classroom_Info {
    /**
     * Starts the interactive session for managing courses and sections.
     * Prompts for user credentials, displays menu options, and processes
     * choices using prepared SQL statements.
     */
	public static void run() {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    int choice = 0;
	    String url = "[your URL]"; 
	    boolean Repeat = true;
	    
	    try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement())
	    {
            System.out.println("\nConnected successfully.");
            
            // Prepare SQL queries for course and section lookups
            String coursesQuery = "SELECT * FROM U_COURSES WHERE COURSE_NUMBER = ?";
            String newCourseQuery = "INSERT INTO U_COURSES (COURSE_NAME, DESCRIPTION, COURSE_NUMBER, SEMESTER_HOURS, COURSE_LEVEL, OFFERING_DEPT) "
            		+ "VALUES (?, ?, ?, ?, ?, ?)";    
            
            String sectionsQuery = "SELECT * FROM U_SECTIONS WHERE SECTION_NUM = ?";
            String newSectionsQuery = "INSERT INTO U_SECTIONS (INSTRUCTOR, SEMESTER, SECTION_NUM, YEAR, COURSE) "
            		+ "VALUES (?, ?, ?, ?, ?)";
            
            // PreparedStatements for secure parameterized queries
            PreparedStatement CQ = conn.prepareStatement (coursesQuery);
            PreparedStatement insertCourse = conn.prepareStatement (newCourseQuery);
            
            PreparedStatement SQ = conn.prepareStatement (sectionsQuery);
            PreparedStatement insertSection = conn.prepareStatement (newSectionsQuery);
            
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, CQ, insertCourse, SQ, insertSection ); // Handle logic for selected choice
                
            }while(Repeat);
	    	
	    	
	    }catch (SQLException e) {
	    	System.out.println("Connection failed:");
	    	e.printStackTrace();
	    }
	    
	}
	
//////////////////////////////////////////////////////////////////////////////

    /**
     * Processes a single user menu choice.
     *
     * @param choice          user's menu selection
     * @param CQ              PreparedStatement for querying courses
     * @param insertCourse    PreparedStatement for inserting courses
     * @param SQ              PreparedStatement for querying sections
     * @param insertSection   PreparedStatement for inserting sections
     * @return true to continue the loop, false to exit
     * @throws SQLException in case of SQL errors
     */
    static boolean process(int choice, PreparedStatement CQ, PreparedStatement insertCourse , PreparedStatement SQ, PreparedStatement insertSection  ) throws SQLException
	{
	    boolean Repeat = true;
	    
	    String courseNumStr;
	    String sectionNumStr;
	    
	    switch(choice) {
	    	case 1: // view course info
		    	System.out.println("Chosen to look for a course by course number....\n");
	    		courseNumStr = readEntry("Enter Course Number: ");
	    		
	    		if (!courseNumStr.matches("\\d+")) {
	                    System.out.println("Invalid input. Course number must be numeric.");
	                    break;
	            }
	    		
	    		int courseNum = Integer.parseInt(courseNumStr);
	    		CQ.setInt(1, courseNum);
	    		ResultSet courseRs = CQ.executeQuery();
	    			
	    		if (courseRs.next()) {
	    			System.out.println("\nCourse Name: " + courseRs.getString("COURSE_NAME"));
	    			System.out.println("Course Number: " + courseRs.getInt("COURSE_NUMBER"));
	    			System.out.println("Description: " + courseRs.getString("DESCRIPTION"));
	    			System.out.println("Semester Hours: " + courseRs.getInt("SEMESTER_HOURS"));
	    			System.out.println("Course Level: " + courseRs.getInt("COURSE_LEVEL"));
	    			System.out.println("Offering Department: " + courseRs.getInt("OFFERING_DEPT"));
	    		} 
	    		else
	    		{
	    			System.out.println("No course found with that number.");
	    		}   
	    			
	    		courseRs.close();
	    		break;	    	
	    		
//////////////////////////////////////////////////////////////////////////////

	    	case 2: // add course info
		    	System.out.println("Chosen to add a new course....\n");
		    	
	    		String courseName = readEntry("Enter Course Name: ");
	    		int courseNumber = Integer.parseInt(readEntry("Enter Course Number: "));
	    		
	    		// Check if course already exists
                if (courseExists(CQ.getConnection(), courseName, courseNumber)) {
                    System.out.println("Course with that name or number already exists. Insert aborted.");
                    break;
                }
	    		
	    		String description = readEntry("Enter Description: ");
	    		int semesterHrs = Integer.parseInt(readEntry("Enter Semester Hours: "));
	    		int courseLV = Integer.parseInt(readEntry("Enter Course Level: "));
	    		String offeringDept = readEntry("Enter Offering Department: ");

	    		insertCourse.setString(1,courseName);
	    		insertCourse.setString(2, description);	    		
	    		insertCourse.setInt(3, courseNumber);
	    		insertCourse.setInt(4, semesterHrs);
	    		insertCourse.setInt(5, courseLV);
	    		insertCourse.setString(6, offeringDept);
	    		
	    		int courseRows = insertCourse.executeUpdate();
	                
	            if (courseRows > 0){
	            	System.out.println("New Course added successfully.");
	            } 
	            else{
	                System.out.println("Insert failed.");
	            }
	                
		    	break;	
//////////////////////////////////////////////////////////////////////////////
	    		
	    	case 3: // view section info
		    	System.out.println("Chosen to look for a section by section number....\n");
	    		sectionNumStr = readEntry("Enter Section Number: ");
	    		
	    		if (!sectionNumStr.matches("\\d+")) {
                    System.out.println("Invalid input. Section number must be numeric.");
                    break;
	    		}
	    		
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
	            
//////////////////////////////////////////////////////////////////////////////
         
	        case 4: // add section info
		    	System.out.println("Chosen to add a new section....\n");
		    	
	    		String instructor = readEntry("Enter Instructor Name: ");
	    		String semester = readEntry("Enter Semester (e.g. Fall, Spring: ");
	    		int Year = Integer.parseInt(readEntry("Enter Year (e.g ): "));
	    		int course = Integer.parseInt(readEntry("Enter Course Number: "));
	    		int section = Integer.parseInt(readEntry("Enter Section Number: "));
	    		
	    		// Check if course already exists
                if (sectionExists(CQ.getConnection(), section)) {
                    System.out.println("Sections with number already exists. Insert aborted.");
                    break;
                }
                
                insertSection.setString(1,instructor);
                insertSection.setString(2, semester);
                insertSection.setInt(3, Year);
                insertSection.setInt(4, course);
                insertSection.setInt(5, section);
	    		
	    		int sectionRows = insertCourse.executeUpdate();
	                
	            if (sectionRows > 0){
	            	System.out.println("New Section added successfully.");
	            } 
	            else{
	                System.out.println("Insert failed.");
	            }
	                
		    	break;		        	
		    	
//////////////////////////////////////////////////////////////////////////////
	        	
	    	case 5: // exit program
	            System.out.println("Exiting, Thank you for you for using the app!");
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
        System.out.println("1. View Course by Course Number.");
        System.out.println("2. Add New Course.");
        System.out.println("3. View Section by Section Numbe.");
        System.out.println("4. Add New Section.");
        System.out.println("5. Exit");
	}
	
//////////////////////////////////////////////////////////////////////////////
	
    /**
     * Reads user menu input and validates it.
     *
     * @param choice initial choice value
     * @return valid menu selection (1–5)
     */
	static int readInput(int choice)
	{
		while(true)
		{
			String input = readEntry("Enter choice: ");
            if (input.matches("\\d+")) {
                int val = Integer.parseInt(input);
                if (val >= 1 && val <= 5) {
                	return val;
                }
            }     		
            System.out.println("Invalid input. Please enter a number between 1 to 5");
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

	/**
	 * Checks if a course with the given name or number already exists.
	 *
     * @param conn          database connection
     * @param courseName    name of the course
     * @param courseNumber  course number
     * @return true if the course exists, false otherwise
	 */
	static boolean courseExists(Connection conn, String courseName, int courseNumber) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM U_COURSES WHERE COURSE_NAME = ? OR COURSE_NUMBER = ?")) {
  		  ps.setString(1, courseName);
  		  ps.setInt(2,courseNumber);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check course existence.");
          e.printStackTrace();
          return false;
  	  }
    }
	
//////////////////////////////////////////////////////////////////////////////

/**
* Checks if a section with the given name or number already exists.
*
     * @param conn        database connection
     * @param sectionNum  section number
     * @return true if the section exists, false otherwise
*/
	static boolean sectionExists(Connection conn, int sectionNum) {
		try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM U_SECTIONS WHERE SECTION_NUM = ?")) {
			ps.setInt(1,sectionNum);
			ResultSet rs = ps.executeQuery();
			return rs.next();// true if any row is returned

		}catch (SQLException e) {
			System.out.println("Failed to check section existence.");
			e.printStackTrace();
			return false;
		}
	}
	
}
