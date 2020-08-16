package com.projectx.university.service

import com.projectx.university.dto.CreationOutput
import com.projectx.university.dto.Student
import com.projectx.university.repository.StudentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StudentService(private val studentRepository: StudentRepository) {
    fun addStudent(student: Student): Mono<CreationOutput> =
            studentRepository
                    .addStudent(student)
                    .map { CreationOutput(it) }

    fun getStudent(studentId: String): Mono<Student> =
            studentRepository.getStudent(studentId)
}