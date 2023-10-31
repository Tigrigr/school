package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.List;

public class TestConstants {
    public static final Long FACULTY_ID = 1L;
    public static final String FACULTY_NAME = "Faculty name";
    public static final String FACULTY_OTHER_NAME = "Faculty other name";
    public static final String FACULTY_COLOR = "Faculty color";
    public static final Faculty FACULTY = new Faculty(
            FACULTY_ID,
            FACULTY_NAME,
            FACULTY_COLOR
    );
    public static final List<Faculty> FACULTY_LIST = Collections.singletonList(FACULTY);

    public static final Long STUDENT_ID = 1L;
    public static final String STUDENT_NAME = "Student name";
    public static final String STUDENT_OTHER_NAME = "Student other name";
    public static final Integer STUDENT_AGE = 23;

    public static final Integer STUDENT_AGE_2 = 24;

    public static final Student STUDENT = new Student(
            STUDENT_ID,
            STUDENT_NAME,
            STUDENT_AGE
    );

    public static final List<Student> STUDENT_LIST = Collections.singletonList(STUDENT);

}
