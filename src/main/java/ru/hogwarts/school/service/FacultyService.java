package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();

    private long idCoun = 1;

    public Faculty addFaculty(Faculty faculty) {
        faculties.put(idCoun++, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.get(id);
        }
        throw new EntityNotFoundException("Факультет с таким id не найден");
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            return faculties.put(faculty.getId(), faculty);
        }
        throw new EntityNotFoundException("Факультет с таким id не найден");
    }

    public Faculty delFaculty(long id) {
        if (faculties.containsKey(id)) {
            return faculties.remove(id);
        }
        throw new EntityNotFoundException("Факультет с таким id не найден");
    }

    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }

//    public Collection<Faculty> findByColor(String color) {
//        ArrayList<Faculty> result = new ArrayList<>();
//        for (Faculty faculty : faculties.values()) {
//            if (Objects.equals(faculty.getColor(), color)) {
//                result.add(faculty);
//            }
//        }
//        return result;
//    }

    public Collection<Faculty> findByColor(String color) {
        return this.faculties.values().stream()
            .filter(f -> f.getColor().equals(color)).collect(Collectors.toList());
}
}