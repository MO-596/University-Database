
--(DEPT_NAME, DEPT_CODE, OFFICE_NUMBER, OFFICE_PHONE, COLLEGE)

INSERT INTO U_DEPARTMENT VALUES ('CS', 1, 5,'708-123-45' ,'College of Computer Science');
COMMIT;

INSERT INTO U_DEPARTMENT VALUES ('MATH', 2, 6,'123-456-7890', 'College of Mathematics');
COMMIT;

INSERT INTO U_DEPARTMENT VALUES ('BIO', 3, 9,'012-345-6789', 'College of Biology');
COMMIT;

--//////////////////////////////////////////////////////////////

--U_STUDENTS(FIRST_NAME, LAST_NAME, STUDENTS_NUMBER, SSN, CURRENT_ADDRESS, PERMANENT_ADDRESS, PHONE_NUMBER, PERMANENT_PHONE_NUMBER, BDATE, SEX, CLASS, MAJOR_DEPT, MINOR_DEPT, DEGREE)


INSERT INTO U_STUDENTS VALUES 
  ('Lonnie', 'Serle', 1, 241536978, '6548 Montana Lane', '60777 Kennedy Parkway', '542-672-5988', '939-249-6053','04-FEB-2021', 'F', 'Freshmen', 'CS', NULL, 'B.A');

INSERT INTO U_STUDENTS VALUES
  ('Davy', 'Goranov', 2, 718251064, '97312 Hazelcrest Terrace', '774 Daystar Circle', NULL, '528-136-2616','05-AUG-2020', 'M', 'Freshmen', 'MATH', 'BIO', 'B.S'); 

INSERT INTO U_STUDENTS VALUES
  ('Boyd', 'Abella', 3, 529810346, '8084 Anzinger Point', '6724 Hansons Court', '572-476-0315', '632-258-5319','09-NOV-2021', 'M', 'Sophmore', 'CS', 'MATH', 'B.A');

INSERT INTO U_STUDENTS VALUES 
 ('Kizzee', 'Hustings', 4, 574639189, '31275 Mccormick Parkway', '878 Dapin Trail', '319-250-7985', '618-257-8818','04-MAR-2022', 'F', 'Sophmore', 'BIO', NULL, 'B.S');

INSERT INTO U_STUDENTS VALUES 
 ('Guillemette', 'Hallett', 5, 758269413, '5 Butterfield Drive', '0 Ohio Point', '619-632-3692', '870-602-9503','01-JUN-2020', 'F', 'Junior', 'CS', NULL, 'B.S');

INSERT INTO U_STUDENTS VALUES 
 ('Wren', 'Vannikov', 6, 835629741, '31815 Roxbury Road', '27 Namekagon Pass', '904-832-9544', '138-593-8524
','19-JAN-2022', 'M', 'Junior', 'MATH', NULL, 'B.A');

INSERT INTO U_STUDENTS VALUES 
 ('Laverne', 'Stones', 7, 246987315, '59878 Dryden Circle', '8 Helena Lane', NULL, '927-638-4143','05-DEC-2022', 'F', 'Junior', 'MATH', 'CS', 'B.S');

INSERT INTO U_STUDENTS VALUES 
 ('Bailie', 'Ethridge', 8, 893564721, '43626 Oakridge Lane', '43862 John Wall Park', NULL,'491-883-3510','28-JUN-2022', 'M', 'Senior', 'BIO', NULL, 'B.S');

INSERT INTO U_STUDENTS VALUES 
 ('Selia', 'Hanham', 9, 678239145, '73 Riverside Parkway', '2 Sachs Place', NULL, '242-143-4679','08-JUN-2022', 'F', 'Senior', 'CS', 'BIO', 'B.A');
 
INSERT INTO U_STUDENTS VALUES 
('Paule', 'Netherwood', 10, 452769831, '15 Maywood Hill', '4 Lindbergh Lane', '347-355-1190', '113-826-8497','15-JAN-2021', 'F', 'Senior', 'BIO', 'MATH', 'B.A');
COMMIT;

--//////////////////////////////////////////////////////////////

--COURSE_NAME, DESCRIPTION, COURSE_NUMBER, SEMESTER_HOURS, COURSE_LEVEL, OFFERING_DEPT

INSERT INTO U_COURSES VALUES 
('Computer Science I','Covers the basics of Computer Science', 1, 3, 1, 1);

INSERT INTO U_COURSES VALUES 
('Algebra II','Continuing Algebra I with new topics', 2, 4, 2, 2);

INSERT INTO U_COURSES VALUES 
('General Biology','Covers the basics of Biology', 3, 2, 1, 3);
COMMIT;

--//////////////////////////////////////////////////////////////

--INSTRUCTOR, SEMESTER, YEAR, COURSE, SECTION_NUM

INSERT INTO  U_SECTIONS VALUES ('Victor Govindaswamy', 'Fall', 1, 7, 1);

INSERT INTO  U_SECTIONS VALUES ('Richard King', 'Spring', 2, 6, 2);

INSERT INTO  U_SECTIONS VALUES ('John John', 'Fall', 3, 8, 3);
COMMIT;

--//////////////////////////////////////////////////////////////

--STUDENT_NUM, SECTION_NUM, COURSE_NAME, COURSE_NUMBER, SEMESTER, YEAR, LETTER_GRADE, NUMERIC_GRADE

INSERT INTO  U_GRADE_REPORTS VALUES ( 1, 3, 'General Biology', 3, 'Fall', 8, 'B', 3);

INSERT INTO  U_GRADE_REPORTS VALUES ( 2, 1, 'Computer Science I', 1, 'Fall', 7, 'D', 1);

INSERT INTO  U_GRADE_REPORTS VALUES ( 3, 1, 'Computer Science I', 1, 'Fall', 7, 'C', 2);

INSERT INTO  U_GRADE_REPORTS VALUES ( 4, 1, 'Computer Science I', 1, 'Fall', 7, 'A', 4);

INSERT INTO  U_GRADE_REPORTS VALUES ( 5, 2, 'Algebra II', 2, 'Spring', 6, 'C', 2);

INSERT INTO  U_GRADE_REPORTS VALUES ( 6, 3, 'General Biology', 3, 'Fall', 8,'C',2);

INSERT INTO  U_GRADE_REPORTS VALUES ( 7, 1, 'Computer Science I', 1, 'Fall', 7,'F',0);

INSERT INTO  U_GRADE_REPORTS VALUES ( 8, 2, 'Algebra II', 2, 'Spring', 6, 'D',1);

INSERT INTO  U_GRADE_REPORTS VALUES ( 9, 1, 'Computer Science I', 1, 'Fall', 7, 'B', 3);

INSERT INTO  U_GRADE_REPORTS VALUES ( 10, 3, 'General Biology', 3, 'Fall', 8, 'A', 4);

COMMIT;
