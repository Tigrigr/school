package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void creatStudent() throws Exception{

        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT);

        JSONObject creatStudent = new JSONObject();
        creatStudent.put("name", STUDENT_NAME);
        creatStudent.put("age", STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(creatStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(STUDENT_NAME))
                        .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }

    @Test
    public void getStudentById() throws Exception{

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(STUDENT_ID))
                        .andExpect(jsonPath("$.name").value(STUDENT_NAME))
                        .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }

    @Test
    public void updateStudent() throws Exception{

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));
        STUDENT.setName(STUDENT_OTHER_NAME);

        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT);

        JSONObject updateStudent = new JSONObject();
        updateStudent.put("id", STUDENT_ID);
        updateStudent.put("name", STUDENT_NAME);
        updateStudent.put("age", STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(updateStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(STUDENT_ID))
                        .andExpect(jsonPath("$.name").value(STUDENT_OTHER_NAME))
                        .andExpect(jsonPath("$.age").value(STUDENT_AGE));
    }

    @Test
    public void deleteStudent() throws Exception{

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void getAllStudent() throws Exception{

        when(studentRepository.findAll()).thenReturn(STUDENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

    @Test
    public void getStudentByAge() throws Exception{

        when(studentRepository.findStudentsByAgeBetween(any(Integer.class), any(Integer.class)))
                .thenReturn(STUDENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?startAge=" + STUDENT_AGE + "&endAge=" + STUDENT_AGE_2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(mapper.writeValueAsString(STUDENT_LIST)));
    }

}
