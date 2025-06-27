package Tables;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_University_Tables {

	public static void main(String[] args) {
		// Prompt user for Oracle credentials
	    String user = readEntry("Enter Oracle DB username: ");        
	    String password = readEntry("Enter Oracle password username: ");
	    String url = "[your url]"; 
		try(Connection conn = DriverManager.getConnection(url, user, password);
			    Statement stmt = conn.createStatement()) 
			{
				System.out.println("\nConnected successfully.");
				
				// U_STUDENTS Table
			    String studentTableQuery  = "CREATE TABLE U_STUDENTS ("
			    		+ "FIRST_NAME				VARCHAR(25) NOT NULL, "
			    		+ "LAST_NAME				VARCHAR(25), "
			    		+ "STUDENTS_NUMBER			NUMBER(25), "
			    		+ "SSN						NUMBER(9)   NOT NULL, "
			    		+ "CURRENT_ADDRESS			VARCHAR(40) NOT NULL, "
			    		+ "PERMANENT_ADDRESS		VARCHAR(40) NOT NULL, "
			    		+ "PHONE_NUMBER				VARCHAR(30), "
			    		+ "PERMANENT_PHONE_NUMBER	VARCHAR(30) NOT NULL, "
			    		+ "BDATE					DATE 	    NOT NULL, "
			    		+ "SEX						CHAR, "
			    		+ "CLASS					VARCHAR(25) NOT NULL, "
			    		+ "MAJOR_DEPT				VARCHAR(25) NOT NULL, "
			    		+ "MINOR_DEPT				VARCHAR(25), "
			    		+ "DEGREE					VARCHAR(6)  NOT NULL ) ";
			    
			    String studentsPrimaryKey = "ALTER TABLE U_STUDENTS "
			    		+ "ADD CONSTRAINT PK_U_STUDENTS PRIMARY KEY(STUDENTS_NUMBER)";

			    String studentsSequence= "CREATE SEQUENCE SEQ_STUDENTS_NUMBER START WITH 11 INCREMENT BY 1"
			    		+ "NOCACHE " + "NOCYCLE";
			    
			    stmt.executeUpdate(studentTableQuery); // same as commit
	            System.out.println("Table U_STUDENTS created successfully.");
				
			    stmt.executeUpdate(studentsPrimaryKey);
	            System.out.println("Primary Key for U_STUDENTS successfully added.");
	            
	            stmt.executeUpdate(studentsSequence);
	            System.out.println("Sequence for U_STUDENTS successfully added.");

	        	//////////////////////////////////////////////////////////////////////////////
	            
				// U_DEPARTMENT Table
	            String departmentTableQuery = "CREATE TABLE U_DEPARTMENT ("
	            		+ "DEPT_NAME	VARCHAR(25), "
	            		+ "DEPT_CODE	NUMBER(10), "
	            		+ "OFFICE_NUMBER	NUMBER(10)   NOT NULL, "
	            		+ "OFFICE_PHONE	VARCHAR(30)  NOT NULL, "
	            		+ "COLLEGE		VARCHAR(30)  NOT NULL )";
	            
	            String departmentPrimaryKey = "ALTER TABLE U_DEPARTMENT "
	            		+ "ADD CONSTRAINT PK_U_DEPARTMENT PRIMARY KEY(DEPT_CODE)";	            
	            
			    String departmentSequence= "CREATE SEQUENCE SEQ_UNI_DEPT_CODE START WITH 4 INCREMENT BY 1"
			    		+ "NOCACHE " + "NOCYCLE";
	            
			    stmt.executeUpdate(departmentTableQuery); // same as commit
	            System.out.println("Table U_DEPARTMENT created successfully.");
				
			    stmt.executeUpdate(departmentPrimaryKey);
	            System.out.println("Primary Key for U_DEPARTMENT successfully added.");
	            
	            stmt.executeUpdate(departmentSequence);
	            System.out.println("Sequence for U_DEPARTMENT successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////
     
				// U_COURSES Table
	            String coursesTableQuery = "CREATE TABLE U_COURSES ( "
	            		+ "COURSE_NAME	VARCHAR(30) NOT NULL, "
	            		+ "DESCRIPTION	VARCHAR(40) NOT NULL, "
	            		+ "COURSE_NUMBER	NUMBER(10), "
	            		+ "SEMESTER_HOURS	NUMBER(20)  NOT NULL, "
	            		+ "COURSE_LEVEL	NUMBER(6)   NOT NULL, "
	            		+ "OFFERING_DEPT	NUMBER(10)  NOT NULL) ";
	           
	            String coursesPrimaryKey = "ALTER TABLE U_COURSES "
	            		+ "ADD CONSTRAINT PK_U_COURSES_NUM PRIMARY KEY(COURSE_NUMBER)";	   
	           
			    String courseSequence= "CREATE SEQUENCE SEQ_COURSE_NUMBER START WITH 4 INCREMENT BY 1"
			    		+ "NOCACHE " + "NOCYCLE";
	            
			    stmt.executeUpdate(coursesTableQuery); // same as commit
	            System.out.println("Table U_COURSES created successfully.");
				
			    stmt.executeUpdate(coursesPrimaryKey);
	            System.out.println("Primary Key for U_COURSES successfully added.");
	            
	            stmt.executeUpdate(courseSequence);
	            System.out.println("Sequence for U_COURSES successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////

				// U_SECTIONS Table
	            String sectionsTableQuery = "CREATE TABLE U_SECTIONS ( "
	            		+ "INSTRUCTOR	VARCHAR(25) NOT NULL, "
	            		+ "SEMESTER	VARCHAR(10) NOT NULL, "
	            		+ "SECTION_NUM	NUMBER(10), "
	            		+ "YEAR		NUMBER(4)   NOT NULL, "
	            		+ "COURSE		NUMBER(10)  NOT NULL) "
	            		;
	            String sectionsPrimaryKey = "ALTER TABLE U_SECTIONS "
	            		+ "ADD CONSTRAINT PK_U_SECTIONS_SECT_NUM PRIMARY KEY(SEMESTER,SECTION_NUM,YEAR)";	   
	            
			    String sectionSequence= "CREATE SEQUENCE SEQ_SECTION_NUM "
			    		+ "START WITH 4 "
			    		+ "INCREMENT BY 1"
			    		+ "NOCACHE " + "NOCYCLE";
	            
			    stmt.executeUpdate(sectionsTableQuery); // same as commit
	            System.out.println("Table U_SECTIONS created successfully.");
				
			    stmt.executeUpdate(sectionsPrimaryKey);
	            System.out.println("Primary Key for U_SECTIONS successfully added.");
	            
	            stmt.executeUpdate(sectionSequence);
	            System.out.println("Sequence for U_SECTIONS successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////

				// U_GRADE_REPORTS Table
	            String gradeReportsTableQuery = "CREATE TABLE U_GRADE_REPORTS ( "
	            		+ "STUDENT_NUM	NUMBER(25), "
	            		+ "SECTION_NUM	NUMBER(10), "
	            		+ "COURSE_NAME	VARCHAR(30) NOT NULL, "
	            		+ "COURSE_NUMBER	NUMBER(25)  NOT NULL, "
	            		+ "SEMESTER	VARCHAR(10) NOT NULL, "
	            		+ "YEAR		NUMBER(4)   NOT NULL, "
	            		+ "LETTER_GRADE	VARCHAR(5)  NOT NULL, "
	            		+ "NUMERIC_GRADE	NUMBER(5)   NOT NULL )";
	            
			    stmt.executeUpdate(gradeReportsTableQuery); // same as commit
	            System.out.println("Table U_GRADE_REPORTS created successfully.");
	            
	            String gradeReportsPrimaryKey = "ALTER TABLE U_GRADE_REPORTS "
	            		+ "ADD CONSTRAINT PK_U_GRADE_REPORTS_GRADE PRIMARY KEY(STUDENT_NUM,SECTION_NUM)";	  

			    stmt.executeUpdate(gradeReportsPrimaryKey);
	            System.out.println("Primary Key for U_GRADE_REPORT successfully added.");
	            
	        	//////////////////////////////////////////////////////////////////////////////
	            //ADDING UNIQUE CONSTRAINT
	            stmt.executeUpdate("ALTER TABLE U_STUDENTS ADD CONSTRAINT UNIQ_U_STUDENTS_NAME UNIQUE(SSN)");
	            stmt.executeUpdate("ALTER TABLE U_DEPARTMENT ADD CONSTRAINT UNIQ_U_DEPARTMENT_NAME UNIQUE(DEPT_NAME)");
	            stmt.executeUpdate("ALTER TABLE U_COURSES ADD CONSTRAINT UNIQ_U_COURSES_NAME UNIQUE(COURSE_NAME)");
	            System.out.println("Unique constraints added successfully.");

	        	//////////////////////////////////////////////////////////////////////////////
	            //ADDING CONSTRAINTS FOR UNIVERSITY DATABASE
	            //U_STUDENTS
	            //Ensures that every MAJOR_DEPT value in U_STUDENTS matches an existing DEPT_NAME in the U_DEPARTMENT table.
	            stmt.executeUpdate("ALTER TABLE U_STUDENTS ADD CONSTRAINT FK_U_STUDENTS_MAJOR_DEGREE "
	            		+ "FOREIGN KEY(MAJOR_DEPT) "
	            		+ "REFERENCES U_DEPARTMENT(DEPT_NAME)");

	            //Ensures that every MINOR_DEPT value in U_STUDENTS matches an existing DEPT_NAME in the U_DEPARTMENT table.
	            stmt.executeUpdate("ALTER TABLE U_STUDENTS ADD CONSTRAINT FK_U_STUDENTS_MINOR_DEGREE "
	            		+ "FOREIGN KEY(MINOR_DEPT) "
	            		+ "REFERENCES U_DEPARTMENT(DEPT_NAME)");
	            
	            //U_COURSES
	            //Ensures OFFERING_DEPT in U_COURSES must match a valid DEPT_CODE in U_DEPARTMENT.
	            stmt.executeUpdate("ALTER TABLE U_COURSES ADD CONSTRAINT FK_U_COURSES_OFFERING_DEPT "
	            		+ "FOREIGN KEY(OFFERING_DEPT) "
	            		+ "REFERENCES U_DEPARTMENT(DEPT_CODE)");
	            
	            //U_SECTIONS
	            //Ensures that each section in U_SECTIONS is linked to an existing course in U_COURSES.
	            stmt.executeUpdate("ALTER TABLE U_SECTIONS ADD CONSTRAINT FK_U_SECTIONS_COURSE "
	            		+ "FOREIGN KEY(COURSE) "
	            		+ "REFERENCES U_COURSES(COURSE_NUMBER)");
	            
	            //U_GRADE_REPORTS
	            //Ensures that every STUDENT_NUM in U_GRADE_REPORTS must exist in the U_STUDENTS table's STUDENTS_NUMBER column.
	            stmt.executeUpdate("ALTER TABLE U_GRADE_REPORTS ADD CONSTRAINT FK_U_GRADE_REPORTS_STUDENT_NUM "
	            		+ "FOREIGN KEY(STUDENT_NUM) "
	            		+ "REFERENCES U_STUDENTS(STUDENTS_NUMBER)");
	            
	            //Ensures that each grade report is linked to a valid course section offering.
	            //It requires that the combination (SEMESTER, SECTION_NUM, YEAR) in U_GRADE_REPORTS exists in the U_SECTIONS table.
	            stmt.executeUpdate("ALTER TABLE U_GRADE_REPORTS ADD CONSTRAINT FK_U_GRADE_REPORTS_SECTION_AREA "
	            		+ "FOREIGN KEY(SEMESTER,SECTION_NUM,YEAR) "
	            		+ "REFERENCES U_SECTIONS(SEMESTER,SECTION_NUM,YEAR)");	            
	            
	            //Ensures that the COURSE_NAME in U_GRADE_REPORTS matches a valid course name in the U_COURSES table.
	            //Prevents invalid or misspelled course names from being inserted into the grade report.
	            stmt.executeUpdate("ALTER TABLE U_GRADE_REPORTS ADD CONSTRAINT FK_U_GRADE_REPORTS_COURSES_NAME "
	            		+ "FOREIGN KEY(COURSE_NAME) "
	            		+ "REFERENCES U_COURSES(COURSE_NAME)");	    
	            
	            //Ensures that the COURSE_NUMBER in U_GRADE_REPORTS exists in U_COURSES.
	            //This maintains consistency between grade records and the official course list.
	            stmt.executeUpdate("ALTER TABLE U_GRADE_REPORTS ADD CONSTRAINT FK_U_GRADE_REPORTS_COURSE_NUMBER "
	            		+ "FOREIGN KEY(COURSE_NUMBER) "
	            		+ "REFERENCES U_COURSES(COURSE_NUMBER)");	
	            
	            System.out.println("Foreign key constraints added successfully.");

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
