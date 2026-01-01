package com.stemlink.skillmentor.controllers;

import com.stemlink.skillmentor.dto.StudentDTO;
import com.stemlink.skillmentor.entities.Mentor;
import com.stemlink.skillmentor.entities.Student;
import com.stemlink.skillmentor.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
@Validated
public class StudentController extends AbstractController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(Pageable pageable) {
        Page<Student> students = studentService.getAllStudents(pageable);
        return sendOkResponse(students);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        return sendOkResponse(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        Student createStudent = studentService.createNewStudent(student);
        return sendCreatedResponse(createStudent);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO updatedStudentDTO) {
        Student student = modelMapper.map(updatedStudentDTO, Student.class);
        Student updateStudent = studentService.updateStudentById(id, student);
        return sendOkResponse(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return sendNoContentResponse();
    }
}