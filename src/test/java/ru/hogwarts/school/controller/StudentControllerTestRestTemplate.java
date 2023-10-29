package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;


import static org.assertj.core.api.Assertions.*;
import static ru.hogwarts.school.controller.TestConstants.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void creatStudent() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);

        assertThat(newStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student newStudentGet = newStudent.getBody();

        assertThat(newStudentGet.getName()).isEqualTo(STUDENT.getName());
        assertThat(newStudentGet.getAge()).isEqualTo(STUDENT.getAge());
    }
    @Test
    public void getStudentById() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student newStudentTest = newStudent.getBody();

        ResponseEntity<Student> getStudent =
        testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudentTest.getId(), Student.class);

        Student student = newStudent.getBody();

        assertThat(student.getId()).isEqualTo(newStudentTest.getId());
        assertThat(student.getName()).isEqualTo(newStudentTest.getName());
        assertThat(student.getAge()).isEqualTo(newStudentTest.getAge());
    }

    @Test
    public void deleteStudent() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student newStudentTest = newStudent.getBody();

                testRestTemplate.delete("http://localhost:" + port + "/student/" + newStudentTest.getId(), Student.class);

        ResponseEntity<Student> getStudent =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudentTest.getId(), Student.class);

        Student student = getStudent.getBody();

        assertThat(student.getName()).isNull();
    }

    @Test
    public void getStudentsByAge() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student newStudentTest = newStudent.getBody();
                assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student?startAge=15&endAge=30", String.class))
                        .isNotNull();
    }

    @Test
    public void getAllStudents() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student newStudentTest = newStudent.getBody();
                assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/student/all", String.class))
                .isNotNull();

    }

    @Test
    public void updateStudent() {
        ResponseEntity<Student> newStudent =
                testRestTemplate.postForEntity("http://localhost:" + port + "/student", STUDENT, Student.class);
        Student newStudentTest = newStudent.getBody();
        newStudentTest.setName(STUDENT_OTHER_NAME);

        testRestTemplate.put("http://localhost:" + port + "/student", newStudentTest, Student.class);

        ResponseEntity<Student> getStudent =
                testRestTemplate.getForEntity("http://localhost:" + port + "/student/" + newStudentTest.getId(), Student.class);

        Student student = getStudent.getBody();
        assertThat(student.getName()).isEqualTo(newStudentTest.getName());
    }

}