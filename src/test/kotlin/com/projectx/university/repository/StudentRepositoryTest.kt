package com.projectx.university.repository

import com.projectx.university.dto.Student
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class StudentRepositoryTest {

    private val redisTemplateMock: ReactiveRedisOperations<String, Student> = mockk()
    private val sut: StudentRepository = StudentRepository(redisTemplateMock)
    private val reactiveValueOperationsMock = mockk<ReactiveValueOperations<String, Student>>()

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun addStudent() {
        // given
        val student = Student("testId", "testName")
        every { redisTemplateMock.opsForValue() } returns reactiveValueOperationsMock
        every { reactiveValueOperationsMock.set("testId", student) } returns Mono.just(true)

        // when
        val result = sut.addStudent(student)

        // then
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete()

        verifySequence {
            redisTemplateMock.opsForValue()
            reactiveValueOperationsMock.set("testId", student)
        }
    }

    @Test
    fun getStudent() {
        // given
        val studentId = "testId"
        val expectedStudent = Student(studentId, "testName")
        every { redisTemplateMock.opsForValue() } returns reactiveValueOperationsMock
        every { reactiveValueOperationsMock.get(studentId) } returns Mono.just(expectedStudent)

        // when
        val result = sut.getStudent(studentId)


        // then
        StepVerifier.create(result)
                .expectNext(expectedStudent)
                .verifyComplete()

        verifySequence {
            redisTemplateMock.opsForValue()
            reactiveValueOperationsMock.get(studentId)
        }
    }
}