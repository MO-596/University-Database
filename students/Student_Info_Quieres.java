package students;
import java.sql.*;
import java.io.*;

/**
 * The {@code Student_Info_Quieres} class provides a console-based interface
 * for retrieving and displaying student information from the `U_STUDENTS` table
 * in an Oracle database.
 *
 * <p>Features include:
 * <ul>
 *   <li>Prompting the user for a student number</li>
 *   <li>Validating numeric input</li>
 *   <li>Executing a parameterized SQL query using a prepared statement</li>
 *   <li>Displaying student details if found</li>
 * </ul>
 *
 * <p>This class connects to the database using JDBC and supports multiple student lookups in one session.
 */
public class Student_Info_Quieres {
    /**
     * Launches the student information lookup session.
     * Prompts for database credentials, connects to Oracle DB, and allows repeated student queries.
     */
	public static void run(){	    
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		
	    String url = "[your URL]"; 
		String more = "yes";

		try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement()) {
	            System.out.println("\nConnected successfully.");
	            
	            // Prepare SQL queries for course and section lookups
	            String query = "select * FROM U_STUDENTS WHERE STUDENTS_NUMBER = ?";
	            
	            // PreparedStatements for secure parameterized queries
	            PreparedStatement p = conn.prepareStatement (query);
	            
	            while(more.equalsIgnoreCase("yes")) {
	            		  
	            	String stuNumStr = readEntry("Enter Student Number: ");
	                if (!stuNumStr.matches("\\d+")) {
	                    System.out.println("Invalid input. Please enter numeric values only.");
	                    continue;
	                }
	                
	                int stuNum = Integer.parseInt(stuNumStr);

	            	p.clearParameters();
	            	p.setInt(1, stuNum);
	            
	            	ResultSet rs = p.executeQuery();
	            	if (rs.next()) {
	            		System.out.println("Student: " + rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"));
	            		System.out.println("Student Number: " + rs.getString("STUDENTS_NUMBER"));
	            		System.out.println("SSN: " + rs.getString("SSN"));
	                
	            		System.out.println("Current Address: " + rs.getString("CURRENT_ADDRESS"));
	            		System.out.println("Permant Address: " + rs.getString("PERMANENT_ADDRESS"));
	            		System.out.println("Phone Number: " + rs.getString("PHONE_NUMBER"));
	            		System.out.println("Permant Phone Number: " + rs.getString("PERMANENT_PHONE_NUMBER"));
	            		System.out.println("Birthdate: " + rs.getString("BDATE"));
	            		System.out.println("Gender: " + rs.getString("GENDER"));
	            		System.out.println("Class: " + rs.getString("CLASS"));
	                	System.out.println("Major: " + rs.getString("MAJOR_DEPT"));
	                	System.out.println("Minor: " + rs.getString("MINOR_DEPT"));
	                	System.out.println("Degree: " + rs.getString("DEGREE"));
	            	}else {
	            		System.out.println("No student found with that number.");
	            	}
	            	rs.close();
	            	
	                more = readEntry("\nSearch for another student? (yes/no): ");
	                if(more == "no" ||more == "No") {
	                	System.out.println("\nCome back later");
	                }
	            }
	        } 
		catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
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
