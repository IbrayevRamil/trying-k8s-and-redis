package com.projectx.student.service;

import com.projectx.student.dto.CreationOutput;
import com.projectx.student.dto.Student;
import com.projectx.student.repository.StudentRepository;
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
