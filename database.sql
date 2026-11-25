CREATE DATABASE IF NOT EXISTS course_db;
USE course_db;

CREATE TABLE students (
  student_id INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(100) NOT NULL UNIQUE,
  password   VARCHAR(64)  NOT NULL,
  department VARCHAR(100)
);

CREATE TABLE courses (
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  code      VARCHAR(20)  NOT NULL UNIQUE,
  name      VARCHAR(150) NOT NULL,
  credits   INT NOT NULL,
  seats     INT NOT NULL
);

CREATE TABLE registrations (
  reg_id     INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course_id  INT NOT NULL,
  status     VARCHAR(10) NOT NULL DEFAULT 'ENROLLED',
  reg_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES students(student_id),
  FOREIGN KEY (course_id)  REFERENCES courses(course_id)
);

INSERT INTO courses (code, name, credits, seats) VALUES
('ME101', 'Engineering Mechanics', 4, 30),
('ME102', 'Thermodynamics', 3, 25),
('ME103', 'Engineering Graphics (CAD)', 3, 20),
('CS101', 'Problem Solving using Python', 3, 40),
('MA101', 'Engineering Mathematics I', 4, 50);
