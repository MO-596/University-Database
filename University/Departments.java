package university;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * The {@code Departments} class provides an interactive command-line interface
 * to manage and query department data in a university Oracle database system.
 *
 * <p>Features include:
 * <ul>
 *   <li>Adding a new department after validating uniqueness</li>
 *   <li>Viewing a department's information using its department code</li>
 * </ul>
 *
 * <p>This class connects to the Oracle database using JDBC and relies on the
 * U_DEPARTMENT table schema with fields: DEPT_NAME, DEPT_CODE, OFFICE_NUMBER,
 * OFFICE_PHONE, and COLLEGE.
 */
public class Departments {
    /**
     * Starts the interactive session for managing courses and sections.
     * Prompts for user credentials, displays menu options, and processes
     * choices using prepared SQL statements.
     */
	public static void run() {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
		int choice = 0;
	    boolean Repeat = true;

	    try(Connection conn = DriverManager.getConnection(url, user, password);
		        Statement stmt = conn.createStatement()) {
	    	
            System.out.println("\nConnected successfully.");
            
            // Prepare SQL queries for course and section lookups
            String departmentQuery = "select * FROM U_DEPARTMENT WHERE DEPT_CODE = ?";
            String insertDepQuery = "INSERT INTO U_DEPARTMENT (DEPT_NAME, DEPT_CODE, OFFICE_NUMBER, OFFICE_PHONE, COLLEGE)"
            		+ "VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement deptQuery = conn.prepareStatement (departmentQuery);
            PreparedStatement insertQuery = conn.prepareStatement(insertDepQuery);
            
            // Interactive menu loop
            do {
            	menuOptions(); // Displays Menu options
                choice = readInput(choice); // Gets user input
                Repeat = process(choice, deptQuery, insertQuery); // Handle logic for selected choice
                
            }while(Repeat);       
         
            
	    }catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
        }
	}
	
//////////////////////////////////////////////////////////////////////////////

    /**
     * Handles the logic for each menu selection.
     *
     * @param choice       user's selected option
     * @param deptQuery    PreparedStatement for department lookups
     * @param insertQuery  PreparedStatement for department inserts
     * @return true to continue menu loop, false to exit
     * @throws SQLException if any SQL operation fails
     */
	static boolean process(int choice, PreparedStatement deptQuery, PreparedStatement insertQuery) throws SQLException
	{
	    boolean Repeat = true;
	    
	    switch(choice) {
	    	case 1: // Add New Department
		    	System.out.println("Chosen to add a new department....\n");
		    	
		    	String deptName = readEntry("Enter Department Name: ");
                int deptCode = Integer.parseInt(readEntry("Enter Department Code: "));
                
                // Check if department already exists
                if (departmentExists(deptQuery.getConnection(), deptName, deptCode)) {
                    System.out.println("Department with that name or code already exists. Insert aborted.");
                    break;
                }
                
                int officeNum = Integer.parseInt(readEntry("Enter Office Number: "));
                String phoneNum = readEntry("Enter Department Phone Number (Need to be 10 numbers): ");
                String college = readEntry("Enter college (e.g., College of Math): ");
	    		
                insertQuery.setString(1, deptName);
                insertQuery.setInt(2, deptCode);
                insertQuery.setInt(3, officeNum);
                insertQuery.setString(4, phoneNum);
                insertQuery.setString(5, college);
                
                int rows = insertQuery.executeUpdate();
                
                if (rows > 0){
                    System.out.println("New Department added successfully.");
                } 
                else{
                    System.out.println("Insert failed.");
                }
                
	    		break;	
	    		
	    	case 2:
		    	System.out.println("Chosen to look for a department by department code....\n");
	    		String deptCodeStr = readEntry("Enter Department Number: ");
            	
            	// Input validation: ensure input is numeric
                if (!deptCodeStr.matches("\\d+")) {
                    System.out.println("Invalid input. Please enter numeric values only.");
                    break;
                }
                
                int DeptNum = Integer.parseInt(deptCodeStr);

                deptQuery.clearParameters();
                deptQuery.setInt(1,DeptNum);
            	
            	ResultSet rs = deptQuery.executeQuery();
            	
            	if (rs.next()) {
            		System.out.println("Department: " + rs.getString("DEPT_NAME"));
            		System.out.println("Department Code: " + rs.getString("DEPT_CODE"));
            		System.out.println("Office Number: " + rs.getString("OFFICE_NUMBER"));
                
            		System.out.println("Office Phone: " + rs.getString("OFFICE_PHONE"));
            		System.out.println("College: " + rs.getString("COLLEGE"));
            	}
            	else {
            		System.out.println("No department found with that number.");
            	}
            	
            	rs.close();
            	break;
            	
	    	case 3:	
	            System.out.println("Exiting!");
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
        System.out.println("1. Add New Department.");
        System.out.println("2. View Departments by Department Code.");
        System.out.println("3. Exit\n");
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
	
////////////////////////////////////////////////////////////////////////////// 
  
    /**
     * Checks if a department already exists in the database using its name or code.
     *
     * @param conn      active database connection
     * @param deptName  department name
     * @param deptCode  department code
     * @return true if the department exists; false otherwise
     */
	static boolean departmentExists(Connection conn, String deptName, int deptCode) {
  	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM U_DEPARTMENT WHERE DEPT_NAME = ? OR DEPT_CODE = ?")) {
  		  ps.setString(1, deptName);
  		  ps.setInt(2,deptCode);
          ResultSet rs = ps.executeQuery();
          return rs.next();// true if any row is returned
          
  	  }catch (SQLException e) {
          System.out.println("Failed to check department existence.");
          e.printStackTrace();
          return false;
  	  }
    }
	
    
	
}



