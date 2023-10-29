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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import javax.print.attribute.standard.Media;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void creatFaculty() throws Exception{

        when(facultyRepository.save(any(Faculty.class))).thenReturn(FACULTY);

        JSONObject creatFacultyJS = new JSONObject();
        creatFacultyJS.put("name", FACULTY_NAME);
        creatFacultyJS.put("color", FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(creatFacultyJS.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                        .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void getFacultyById() throws Exception{

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(FACULTY_ID))
                        .andExpect(jsonPath("$.name").value(FACULTY_NAME))
                        .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void updateFaculty() throws Exception{

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));
        FACULTY.setName(FACULTY_OTHER_NAME);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(FACULTY);

        JSONObject updateFaculty = new JSONObject();
        updateFaculty.put("id", FACULTY_ID);
        updateFaculty.put("name", FACULTY_NAME);
        updateFaculty.put("color", FACULTY_COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(updateFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(FACULTY_ID))
                        .andExpect(jsonPath("$.name").value(FACULTY_OTHER_NAME))
                        .andExpect(jsonPath("$.color").value(FACULTY_COLOR));
    }

    @Test
    public void deleteFaculty() throws Exception{

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void getAllFaculty() throws Exception{

        when(facultyRepository.findAll()).thenReturn(FACULTY_LIST);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(mapper.writeValueAsString(FACULTY_LIST)));
    }

    @Test
    public void getFacultyByNameOrColor() throws Exception{

        when(facultyRepository.findFacultiesByColorIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(FACULTY_LIST);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/find?startAge=" + FACULTY_NAME + "&color=" + FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(mapper.writeValueAsString(FACULTY_LIST)));
    }
}