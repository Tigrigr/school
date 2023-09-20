package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new EntityNotFoundException("Факультет с таким id не найден");
        }
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty delFaculty(long id) {
        Faculty faculty = findFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
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
        return getAllFaculty().stream()
            .filter(f -> f.getColor().equals(color)).collect(Collectors.toList());
}
}