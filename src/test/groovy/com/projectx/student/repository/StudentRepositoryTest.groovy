package com.projectx.student.repository

import com.projectx.student.dto.Student
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveValueOperations
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

class StudentRepositoryTest extends Specification {

    @Subject
    StudentRepository sut
    ReactiveRedisOperations<String, Student> redisTemplateMock
    ReactiveValueOperations<String, Student> redisOperationsMock

    void setup() {
        redisTemplateMock = Mock()
        redisOperationsMock = Mock()
        sut = new StudentRepository(redisTemplateMock)
    }

    void 'addStudent should correctly add student'() {
        given:
        final student = Student.builder()
                .id('testId')
                .name('testName')
                .build()

        when:
        final result = sut.addStudent(student)

        then:
        1 * redisTemplateMock.opsForValue() >> redisOperationsMock
        1 * redisOperationsMock.set(student.getId(), student) >> Mono.just(true)

        and:
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete()
    }

    void 'getStudent should get correct student'() {
    }
}
