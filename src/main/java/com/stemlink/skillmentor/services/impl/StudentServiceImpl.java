package com.stemlink.skillmentor.services.impl;

import com.stemlink.skillmentor.entities.Student;
import com.stemlink.skillmentor.exceptions.SkillMentorException;
import com.stemlink.skillmentor.repositories.StudentRepository;
import com.stemlink.skillmentor.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    //TODO: handle exceptions

    @Override
    public Student createNewStudent(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception exception) {
            log.error("Failed to create new student", exception);
            throw new SkillMentorException("Fail to create a new student", HttpStatus.CONFLICT);
        }
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        try {
            log.debug("getting students");
            return studentRepository.findAll(pageable);
        } catch (Exception exception) {
            log.error("Failed to get all students", exception);
            throw new SkillMentorException("Failed to get all students", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student getStudentById(Integer id) {
        try {
            Student student= studentRepository.findById(id).orElseThrow(
                    ()->new SkillMentorException("Student not found", HttpStatus.NOT_FOUND)
            );
            log.info("Successfully fetched Student {}", id);
            return student;
        } catch (SkillMentorException skillMentorException) {
            log.warn("Student not found with id: {} to fetch", id, skillMentorException);
            throw new SkillMentorException("Student Not Found", HttpStatus.NOT_FOUND);

        } catch (Exception exception) {
            log.error("Error getting student", exception);
            throw new SkillMentorException("Failed to get student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Student updateStudentById(Integer id, Student updatedStudent) {
        try {
            Student student = studentRepository.findById(id).orElseThrow(
                    () -> new SkillMentorException("Student not found with id: " + id, HttpStatus.NOT_FOUND)
            );
            modelMapper.map(updatedStudent, student);
            return studentRepository.save(student);

        } catch (SkillMentorException skillMentorException) {
            log.warn("Student not found with id: {} to update", id, skillMentorException);
            throw new SkillMentorException("Student Not found", HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            log.error("Error updating student", exception);
            throw new SkillMentorException("Failed to update student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteStudent(Integer id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception exception) {
            log.error("Failed to delete student with id {}", id, exception);
            throw new SkillMentorException("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
