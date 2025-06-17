package students;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class New_Student_Info_Program {

	public static void main(String[] args) {
	    String fName,lName,currAddress,permAddress,phoneNum,permPhoneNum,bDate,sex,studentClass,major,minor,degree;
	    int ssn;
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		  String more = "yes";
	    String url = "[your url]"; 
        int rows;

		try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement()
	       ) {
	            System.out.println("\nConnected successfully.");
	            String insertQuery = "INSERT INTO U_STUDENTS (FIRST_NAME, LAST_NAME, STUDENTS_NUMBER, SSN, CURRENT_ADDRESS, " +
	            "PERMANENT_ADDRESS, PHONE_NUMBER, PERMANENT_PHONE_NUMBER, BDATE, SEX, CLASS, MAJOR_DEPT, MINOR_DEPT, DEGREE)" +
	            "VALUES (?, ?, SEQ_STUDENTS_NUMBER.NEXTVAL, ?, ?, ?, ?, ?, TO_DATE(?, 'DD-MON-YYYY'), ?, ?, ?, ?, ?)";
	            PreparedStatement p = conn.prepareStatement (insertQuery);
	            
	            while(more.equalsIgnoreCase("yes")){	      
	            	
	            	fName = readEntry("Enter First Name: ");
	            	lName = readEntry("Enter Last Name: ");
	            	ssn = Integer.parseInt(readEntry("Enter student's SSN (9 digits): "));
	            	currAddress = readEntry("Enter Current Address: ");
	            	permAddress = readEntry("Enter Permanentt Address: ");
	            	phoneNum = readEntry("Enter Phone Number: ");
	            	permPhoneNum = readEntry("Enter Permanent Phone Number: ");
	            	bDate = readEntry("Enter Birthdate (ex: DD-MON-YYYY): ");
	            	sex = readEntry("Enter Sex (M/F): ");
	            	studentClass= readEntry("Enter Class (e.g., Freshmen, Sophomore): ");
	            	major = readEntry("Enter Major: ");
	            	minor = readEntry("Enter Minor (can be empty): ");
	            	degree = readEntry("Enter Degree (e.g., B.A, B.S): ");

	                // Bind values
	                p.setString(1, fName);
	                p.setString(2, lName);
	                p.setInt(3, ssn);
	                p.setString(4, currAddress);
	                p.setString(5, permAddress);
	                p.setString(6, phoneNum.isEmpty() ? null : phoneNum);
	                p.setString(7, permPhoneNum);
	                p.setString(8, bDate);
	                p.setString(9, sex);
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
		
        //read entry functions to read the string inputted
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
