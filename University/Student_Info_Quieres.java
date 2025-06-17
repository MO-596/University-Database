package students;
import java.sql.*;
import java.io.*;

public class Student_Info_Quieres {

	public static void main(String[] args){	    
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		
	    String url = "[your url]"; 
		String more = "yes";

		try(Connection conn = DriverManager.getConnection(url, user, password);
	        Statement stmt = conn.createStatement()
	       ) {
	            System.out.println("\nConnected successfully.");
	            String query = "select * FROM U_STUDENTS WHERE STUDENTS_NUMBER = ?";
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
	            		System.out.println("Sex: " + rs.getString("SEX"));
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
	               // p.close();
	              //  conn.close();
	            }
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
