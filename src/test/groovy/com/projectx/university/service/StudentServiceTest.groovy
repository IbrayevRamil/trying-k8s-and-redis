package com.projectx.university.service

import com.projectx.university.dto.CreationOutput
import com.projectx.university.dto.Student
import com.projectx.university.repository.StudentRepository
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

class StudentServiceTest extends Specification {

    @Subject
    StudentService sut
    StudentRepository studentRepositoryMock

    void setup() {
        studentRepositoryMock = Mock()
        sut = new StudentService(studentRepositoryMock)
    }

    void 'getStudent should correctly get student from repository'() {
        given:
        final student = Student.builder()
                .name('testName')
                .id('testId')
                .build()

        when:
        final result = sut.getStudent('testId')

        then:
        1 * studentRepositoryMock.getStudent('testId') >> Mono.just(student)

        and:
        StepVerifier.create(result)
                .expectNext(student)
                .verifyComplete()
    }

    void 'addStudent should add student and wrap repo result to corresponding output'() {
        given:
        final studentStub = Student.builder().build()

        and:
        final expectedOutput = CreationOutput.of(true)

        when:
        final result = sut.addStudent(studentStub)

        then:
        1 * studentRepositoryMock.addStudent(studentStub) >> Mono.just(true)

        and:
        StepVerifier.create(result)
                .expectNext(expectedOutput)
                .verifyComplete()
    }
}
