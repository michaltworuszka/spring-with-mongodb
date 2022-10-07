package com.tworuszka.springwithmongodb.service;

import com.tworuszka.springwithmongodb.api.StudentController;
import com.tworuszka.springwithmongodb.model.Student;
import com.tworuszka.springwithmongodb.model.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
