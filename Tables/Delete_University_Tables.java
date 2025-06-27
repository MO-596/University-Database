package Tables;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete_University_Tables {
	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your URL]"; 
		try(Connection conn = DriverManager.getConnection(url, user, password);
			    Statement stmt = conn.createStatement()) 
			{			
				System.out.println("\nConnected successfully.");
				
				//To drop all tables and constraints along with it
				String[] dropTables = {
						"DROP TABLE U_STUDENTS CASCADE CONSTRAINTS ",
						"DROP TABLE U_DEPARTMENT CASCADE CONSTRAINTS",
						"DROP TABLE U_COURSES  CASCADE CONSTRAINTS",
						"DROP TABLE U_SECTIONS CASCADE CONSTRAINTS",
						"DROP TABLE U_GRADE_REPORTS CASCADE CONSTRAINTS"};
				
				for(String drop : dropTables) {
					try {
						stmt.executeUpdate(drop);
						System.out.println("Executed: " + drop);
					}catch(SQLException e){
	                    System.out.println("Skipping (not found or failed): " + drop);

					}
				}
				
				String[] dropSeq = {
						"DROP SEQUENCE SEQ_SECTION_NUM",
						"DROP SEQUENCE SEQ_COURSE_NUMBER",
						"DROP SEQUENCE SEQ_UNI_DEPT_CODE",
						"DROP SEQUENCE SEQ_STUDENTS_NUMBER"};
				
				for(String drop : dropSeq) {
					try {
						stmt.executeUpdate(drop);
						System.out.println("Executed: " + drop);
					}catch(SQLException e){
	                    System.out.println("Skipping (not found or failed): " + drop);

					}
				}
				
			}catch (SQLException e) {
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


}
