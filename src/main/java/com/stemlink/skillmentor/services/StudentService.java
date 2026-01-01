package com.stemlink.skillmentor.services;

import com.stemlink.skillmentor.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Student createNewStudent(Student student);

    Page<Student> getAllStudents(Pageable pageable);

    Student getStudentById(Integer id);

    Student updateStudentById(Integer id, Student updatedStudent);

    void deleteStudent(Integer id);
}
