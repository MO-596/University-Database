
--1)Show students first and last name, student number, and department names of students who have a minor in biology
SELECT
	FIRST_NAME,
	LAST_NAME,
	STUDENTS_NUMBER,
	MAJOR_DEPT,
	MINOR_DEPT
FROM U_STUDENTS
WHERE MINOR_DEPT = 'BIO';

--2)Show students first and last name, and student number of students who have never taken classes only with instructor named 'king'
SELECT
	FIRST_NAME, 
	LAST_NAME,
	STUDENTS_NUMBER
FROM U_STUDENTS
WHERE STUDENTS_NUMBER NOT IN (SELECT STUDENTS_NUMBER
                              FROM U_SECTIONS
                              WHERE INSTRUCTOR = 'King');

--3)Show students first and last name, and student number of students who have taken classes only with instructor named 'king'
SELECT
	FIRST_NAME, 
	LAST_NAME,
	STUDENTS_NUMBER
FROM U_STUDENTS
WHERE STUDENTS_NUMBER IN (SELECT STUDENTS_NUMBER
                              FROM U_SECTIONS
                              WHERE INSTRUCTOR = 'Richard');

--3)
SELECT * FROM U_COURSES;

--4)
SELECT * FROM U_SECTIONS;

--5)
SELECT * FROM U_GRADE_REPORTS;

--2)Show students name and numbers in descending order base on date  
SELECT FIRST_NAME, LAST_NAME, STUDENTS_NUMBER, BDATE
FROM U_STUDENTS
ORDER BY BDATE DESC;


SELECT * FROM U_SECTIONS WHERE YEAR = 07;


SELECT SEMESTER, SECTION_NUM, YEAR FROM U_SECTIONS;
