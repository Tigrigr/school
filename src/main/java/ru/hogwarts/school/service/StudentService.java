package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();

    private long idCount = 1;

    public Student addStudent(Student student) {
        students.put(idCount++, student);
        return student;
    }

    public Student findStudent(long id) {
        if (students.containsKey(id)) {
            return students.get(id);
        }
        throw new EntityNotFoundException("Студент с таким id не найден");
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            return students.put(student.getId(), student);
        }
        throw new EntityNotFoundException("Студент с таким id не найден");
    }

    public Student delStudent(long id) {
        if (students.containsKey(id)) {
            return students.remove(id);
        }
        throw new EntityNotFoundException("Студент с таким id не найден");
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }

//    public Collection<Student> findByAge(int age) {
//        ArrayList<Student> result = new ArrayList<>();
//        for (Student student : students.values()) {
//            if (student.getAge() == age) {
//                result.add(student);
//            }
//        }
//        return result;
//    }

    public Collection<Student> findByAge(int age) {
        return this.students.values().stream()
                .filter(s -> s.getAge() == age).collect(Collectors.toList());
    }
}