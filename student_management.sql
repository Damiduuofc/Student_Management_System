CREATE DATABASE student_management;
USE student_management;

CREATE TABLE admin(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO admin (username, password) VALUES ('admin', '1234');
INSERT INTO admin (username, password) VALUES ('Damidu', 'Damidu12');

CREATE TABLE student (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    father_name VARCHAR(150) NOT NULL,
    mother_name VARCHAR(150) NOT NULL,
    address1 TEXT NOT NULL,
    address2 TEXT NOT NULL,
    image_path VARCHAR(200) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE course (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT DEFAULT NULL,
    semester INT DEFAULT NULL,
    course1 VARCHAR(200) DEFAULT NULL,
    course2 VARCHAR(200) DEFAULT NULL,
    course3 VARCHAR(200) DEFAULT NULL,
    course4 VARCHAR(200) DEFAULT NULL,
    course5 VARCHAR(200) DEFAULT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

CREATE TABLE score (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT DEFAULT NULL,
    semester INT DEFAULT NULL,
    course1 VARCHAR(200) DEFAULT NULL,
    score1 DOUBLE NOT NULL,
    course2 VARCHAR(200) DEFAULT NULL,
    score2 DOUBLE NOT NULL,
    course3 VARCHAR(200) DEFAULT NULL,
    score3 DOUBLE NOT NULL,
    course4 VARCHAR(200) DEFAULT NULL,
    score4 DOUBLE NOT NULL,
    course5 VARCHAR(200) DEFAULT NULL,
    score5 DOUBLE NOT NULL,
    average DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

SELECT * FROM student_management.student;
SELECT * FROM student_management.course;
SELECT * FROM student_management.score;
SELECT * FROM student_management.admin;
