package com.projectx.university.handler

import com.projectx.university.dto.CreationOutput
import com.projectx.university.dto.Student
import com.projectx.university.service.StudentService
import com.projectx.university.service.ValidationService
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

class StudentHandlerTest extends Specification {

    @Subject
    StudentHandler studentHandler
    StudentService studentServiceMock
    ValidationService validationServiceMock

    void setup() {
        studentServiceMock = Mock()
        validationServiceMock = Mock()
        studentHandler = new StudentHandler(studentServiceMock, validationServiceMock)
    }

    void 'getStudent should return OK status with corresponding student if exists'() {
        given:
        final MockServerRequest mockServerRequest = MockServerRequest.builder()
                .method(HttpMethod.GET)
                .pathVariable('id', 'testId')
                .build()

        and:
        final expectedStudent = Student.builder()
                .name('name')
                .id('testId')
                .build()

        when:
        final result = studentHandler.getStudent(mockServerRequest)

        then:
        1 * studentServiceMock.getStudent('testId') >> Mono.just(expectedStudent)

        and:
        StepVerifier.create(result)
                .expectNextMatches({ response -> response.statusCode() == HttpStatus.OK })
                .verifyComplete()
    }

    void 'getStudent should return NOT FOUND status if student with corresponding id does not exist'() {
        given:
        final MockServerRequest mockServerRequest = MockServerRequest.builder()
                .method(HttpMethod.GET)
                .pathVariable('id', 'testId')
                .build()

        when:
        final result = studentHandler.getStudent(mockServerRequest)

        then:
        1 * studentServiceMock.getStudent('testId') >> Mono.empty()

        and:
        StepVerifier.create(result)
                .expectNextMatches({ response -> response.statusCode() == HttpStatus.NOT_FOUND })
                .verifyComplete()
    }

    void 'createStudent should return OK status with corresponding output result'() {
        given:
        final student = Student.builder()
                .name('name')
                .id('testId')
                .build()
        and:
        final mockServerRequest = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .body(Mono.just(student))

        and:
        1 * validationServiceMock.validateInput(student) >> student
        1 * studentServiceMock.addStudent(student) >> Mono.just(CreationOutput.of(true))

        when:
        final result = studentHandler.addStudent(mockServerRequest)

        then:
        StepVerifier.create(result)
                .expectNextMatches({ response -> response.statusCode() == HttpStatus.OK })
                .verifyComplete()
    }
}
