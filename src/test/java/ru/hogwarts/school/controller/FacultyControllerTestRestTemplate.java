package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void creatFaculty() {
        ResponseEntity<Faculty> newFaculty =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);

        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty newFacultyTest = newFaculty.getBody();

        assertThat(newFacultyTest.getName()).isEqualTo(FACULTY.getName());
        assertThat(newFacultyTest.getColor()).isEqualTo(FACULTY.getColor());
    }

    @Test
    public void getFacultyById() {
        ResponseEntity<Faculty> newFaculty =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);
        Faculty newFacultyTest = newFaculty.getBody();

        ResponseEntity<Faculty> newFacultyGet =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFacultyTest.getId(), Faculty.class);

        Faculty faculty = newFacultyGet.getBody();

        assertThat(faculty.getId()).isEqualTo(newFacultyTest.getId());
        assertThat(faculty.getName()).isEqualTo(newFacultyTest.getName());
        assertThat(faculty.getColor()).isEqualTo(newFacultyTest.getColor());
    }

    @Test
    public void deleteFaculty() {
        ResponseEntity<Faculty> newFaculty =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);
        Faculty newFacultyTest = newFaculty.getBody();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + newFacultyTest.getId(), Faculty.class);

        ResponseEntity<Faculty> newFacultyGet =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFacultyTest.getId(), Faculty.class);

        Faculty faculty = newFacultyGet.getBody();

        assertThat(faculty.getName()).isNull();
    }

    @Test
    public void getFacultyByNameOrColor() {
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty?name=" + FACULTY_NAME + "&color=" + FACULTY_COLOR, String.class))
                .isNotNull();
    }

    @Test
    public void getAllFaculty() {
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);

        assertThat(testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/all", String.class))
                .isNotNull();
    }

    @Test
    public void updateFaculty() {
        ResponseEntity<Faculty> newFaculty =
                testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", FACULTY, Faculty.class);
        Faculty newFacultyTest = newFaculty.getBody();
        newFacultyTest.setName(FACULTY_OTHER_NAME);

        testRestTemplate.put("http://localhost:" + port + "/faculty", newFacultyTest, Faculty.class);

        ResponseEntity<Faculty> newFacultyGet =
                testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/" + newFacultyTest.getId(), Faculty.class);

        Faculty faculty = newFacultyGet.getBody();
        assertThat(faculty.getName()).isEqualTo(newFacultyTest.getName());
    }


}