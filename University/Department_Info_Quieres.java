package university;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Department_Info_Quieres {
	public static void main(String[] args) {
		
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
		  String more = "yes";
	    String url = "[your url]"; 
		
	    try(Connection conn = DriverManager.getConnection(url, user, password);
		        Statement stmt = conn.createStatement()
		       ) {
            System.out.println("\nConnected successfully.");
            String query = "select * FROM U_DEPARTMENT WHERE DEPT_CODE = ?";
            
            PreparedStatement p = conn.prepareStatement (query);
            
            while(more.equalsIgnoreCase("yes")) {
            	String deptCodeStr = readEntry("Enter Department Number: ");
            	
            	// Input validation: ensure input is numeric
                if (!deptCodeStr.matches("\\d+")) {
                    System.out.println("Invalid input. Please enter numeric values only.");
                    continue;
                }
                int DeptNum = Integer.parseInt(deptCodeStr);

            	p.clearParameters();
            	p.setInt(1,DeptNum);
            	
            	ResultSet rs = p.executeQuery();
            	
            	if (rs.next()) {
            		System.out.println("Department: " + rs.getString("DEPT_NAME"));
            		System.out.println("Department Code: " + rs.getString("DEPT_CODE"));
            		System.out.println("Office Number: " + rs.getString("OFFICE_NUMBER"));
                
            		System.out.println("Office Phone: " + rs.getString("OFFICE_PHONE"));
            		System.out.println("College: " + rs.getString("COLLEGE"));
            	}else {
            		System.out.println("No department found with that number.");
            	}
            	rs.close();
            	
                more = readEntry("\nSearch for department? (yes/no): ");
            }
            
	    }catch (SQLException e) {
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
