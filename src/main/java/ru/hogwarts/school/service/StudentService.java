package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException("Студент с таким id не найден");
        }
    }

    public Student editStudent(Student student) {
        Optional<Student> editStudent = studentRepository.findById(student.getId());
        if (editStudent.isPresent()) {
            return studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Такого студента нет");
        }
    }

    public Student delStudent(long id) {
        Student student = findStudent(id);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
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

    public Collection<Student> getByAgeBetween(int startAge, int endAge) {
        return studentRepository.findStudentsByAgeBetween(startAge, endAge);
    }
}