package students;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * The {@code New_Student_Info_Program} class provides an interactive interface
 * for inserting new student records into the U_STUDENTS table in an Oracle database.
 *
 * <p>Features include:
 * <ul>
 *   <li>Prompting the user for student information</li>
 *   <li>Validating SSN and birthdate input</li>
 *   <li>Auto-generating the student number using a sequence</li>
 *   <li>Inserting validated data into the database via a parameterized query</li>
 * </ul>
 *
 * This class connects to the database using JDBC and uses console-based input.
 */
public class New_Student_Info_Program {
	 /**
     * Starts the student entry session.
     * Connects to the Oracle database, prompts the user for student data,
     * validates input, and inserts the record.
     */
	
	public static void run() {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		String more = "yes";
	    String url = "[your url]"; 
        int rows;

		try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement()) {
			
	            System.out.println("\nConnected successfully.");
	            
	            // Prepare SQL queries for course and section lookups
	            String insertQuery = "INSERT INTO U_STUDENTS (FIRST_NAME, LAST_NAME, STUDENTS_NUMBER, SSN, CURRENT_ADDRESS, " +
	            "PERMANENT_ADDRESS, PHONE_NUMBER, PERMANENT_PHONE_NUMBER, BDATE, GENDER, CLASS, MAJOR_DEPT, MINOR_DEPT, DEGREE)" +
	            "VALUES (?, ?, SEQ_STUDENTS_NUMBER.NEXTVAL, ?, ?, ?, ?, ?, TO_DATE(?, 'DD-MON-YYYY'), ?, ?, ?, ?, ?)";
	            
	            PreparedStatement p = conn.prepareStatement (insertQuery);
	            
	            while(more.equalsIgnoreCase("yes")){	      
	            	
	            	String fName = readEntry("Enter First Name: ");
	            	String lName = readEntry("Enter Last Name: ");
	            	
	            	int ssn = validSSN();
	            	
	            	String currAddress = readEntry("Enter Current Address: ");
	            	String permAddress = readEntry("Enter Permanentt Address: ");
	            	
	            	String phoneNum = readEntry("Enter Phone Number: ");
	            	String permPhoneNum = readEntry("Enter Permanent Phone Number: ");
	            	
	            	String bDate = readEntry("Enter Birthdate (ex: DD-MON-YYYY): ");
	            	while(!validDate(bDate)) {
	            		System.out.println("Invalid date format. Please use DD-MON-YYYY (e.g., 25-JUN-2002)");
	            	    bDate = readEntry("Enter Birthdate (ex: DD-MON-YYYY): ");
	            	}
	            	
	            	String genderInput  = readEntry("Enter Gender (M/F): ");
	            	char gender = genderInput.isEmpty() ? 'U' : genderInput.charAt(0);

	            	String studentClass= readEntry("Enter Class (e.g., Freshmen, Sophomore): ");
	            	String major = readEntry("Enter Major: ");
	            	String minor = readEntry("Enter Minor (can be empty): ");
	            	String degree = readEntry("Enter Degree (e.g., B.A, B.S): ");

	                // Bind values
	                p.setString(1, fName);
	                p.setString(2, lName);
	                p.setInt(3, ssn);
	                p.setString(4, currAddress);
	                p.setString(5, permAddress);
	                p.setString(6, phoneNum.isEmpty() ? null : phoneNum);
	                p.setString(7, permPhoneNum);
	                p.setString(8, bDate);
	                p.setString(9, String.valueOf(gender));
	                p.setString(10, studentClass);
	                p.setString(11, major);
	                p.setString(12, minor == null || minor.isEmpty() ? null : minor);
	                p.setString(13, degree);
	            	
	            	
	            	rows = p.executeUpdate();
	            
	            	if (rows > 0) {
	            		System.out.println("Student inserted successfully.");
	            	} else {
	            		System.out.println("Student insertion failed.");
	            	}
	                more = readEntry("Insert another student? (yes/no): ");

	            }
	            p.close();
	            conn.close();
	        } 
		catch (SQLException e) {
            System.out.println("Connection failed:");
            e.printStackTrace();
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

      // Checks if department exists in U_DEPARTMENT table
      static boolean studentExists(Connection conn, int studentNum) {
    	  try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM U_DEPARTMENT WHERE DEPT_NAME = ?")) {
//    		  ps.setString(1, studentNum);
//              ResultSet rs = ps.executeQuery();
//              return rs.next();
    	  }
    	  catch (SQLException e) {
              System.out.println("Connection failed:");
              e.printStackTrace();
          }
    	  return true;
      }
      
  	//////////////////////////////////////////////////////////////////////////////

    /**
     * Validates if a string follows the "dd-MMM-yyyy" date format.
     *
     * @param date the input string
     * @return true if valid, false otherwise
     */
      static boolean validDate(String date){
    	  try {
              new SimpleDateFormat("dd-MMM-yyyy").parse(date);
              return true;
          } 
    	  catch (Exception e) {
              return false;
          }      
      }
      
  	/////////////////////////////////////////////////////////////////////////////     
      
  	/**
     * Placeholder for checking if a student exists in the U_DEPARTMENT table.
     * Currently unused and incomplete.
     *
     * @param conn        the active DB connection
     * @param studentNum  student number to check
     * @return true if exists (stub always returns true)
     */
     static int validSSN(){
    	while(true)
    	{
    		String input = readEntry("Enter student's SSN (9 digits): ");
    	    if (input.matches("\\d{9}")) {
    	    	return( Integer.parseInt(input));
    	   }     
    	    
    	  System.out.println("Invalid input. SSN must be 9 numbers long.");
    	}
     }

}
