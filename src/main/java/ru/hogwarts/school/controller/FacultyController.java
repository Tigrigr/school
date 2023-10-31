package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Факультеты")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета по id")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty greatedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(greatedFaculty);
    }

    @PutMapping
    @Operation(summary = "Редактирование факультета")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletedFaculty = facultyService.delFaculty(id);
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping("all")
    @Operation(summary = "Получение всех факультетов")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyService.getAllFaculty();
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }
    @GetMapping("find")
    @Operation(summary = "Получение всех факультетов по цвету или имени")
    public ResponseEntity<Collection<Faculty>> getByColorOrName (@RequestParam(required = false) String color,
                                                                 @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank()) {
            Collection<Faculty> faculties = facultyService.getByColorOrName(color, name);
            return ResponseEntity.ok(faculties);
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("students/{facultyId}")
    @Operation(summary = "Получение студентов факультета")
    public ResponseEntity<Collection<Student>> getStudents (@PathVariable long facultyId) {
        List<Student> students = facultyService.findFaculty(facultyId).getStudents();
        return ResponseEntity.ok(students);
    }
}