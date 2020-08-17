package com.projectx.university.service

import com.projectx.university.dto.CreationOutput
import com.projectx.university.dto.Student
import com.projectx.university.repository.StudentRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class StudentServiceTest {

    private val studentRepositoryMock: StudentRepository = mockk()
    private val sut: StudentService = StudentService(studentRepositoryMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun addStudent() {
        // given
        val student = Student("testId", "testName")
        every { studentRepositoryMock.addStudent(student) } returns Mono.just(true)

        // when
        val result = sut.addStudent(student)

        // then
        StepVerifier.create(result)
                .expectNext(CreationOutput(true))
                .verifyComplete()

        verify(atMost = 1) { studentRepositoryMock.addStudent(student) }
    }

    @Test
    fun getStudent() {
        // given
        val studentId = "testId"
        val expectedStudent = Student(studentId, "testName")
        every { studentRepositoryMock.getStudent(studentId) } returns Mono.just(expectedStudent)

        // when
        val result = sut.getStudent(studentId)

        // then
        StepVerifier.create(result)
                .expectNext(expectedStudent)
                .verifyComplete()

        verify(atMost = 1) { studentRepositoryMock.getStudent(studentId) }
    }
}