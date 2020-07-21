package com.projectx.university.service;

import com.projectx.university.dto.CreationOutput;
import com.projectx.university.dto.Student;
import com.projectx.university.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Mono<Student> getStudent(String studentId) {
        return studentRepository.getStudent(studentId);
    }

    public Mono<CreationOutput> addStudent(Student student) {
        return studentRepository.addStudent(student)
                .map(CreationOutput::of);
    }
}
