# College Course Registration System
Java Servlets + JSP + JDBC + MySQL (Tomcat 9)

## Run
1. Run `database.sql` in MySQL.
2. Set your MySQL password in `src/com/college/DBUtil.java`.
3. Copy `mysql-connector-j-8.x.jar` into `WebContent/WEB-INF/lib`.
4. Import into Eclipse as Dynamic Web Project, add Tomcat 9, Run on Server.
5. Open http://localhost:8080/course-registration/

## SQL operations
INSERT: register student, enroll course
SELECT: login, list courses, my courses, profile
UPDATE: seats, drop course, update profile
