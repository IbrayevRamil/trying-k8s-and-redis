package com.projectx.university

import com.projectx.university.dto.CreationOutput
import com.projectx.university.dto.Student
import com.projectx.university.handler.StudentHandler
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import spock.lang.Specification

@SpringBootTest
@DirtiesContext
@AutoConfigureWebTestClient
class StudentRouterTest extends Specification {
    @Autowired
    private WebTestClient webTestClient
    @SpringBean
    private StudentHandler studentHandlerMock = Mock(StudentHandler)

    void 'POST /api/student should return response with creation output result'() {
        given:
        final student = Student.builder().build()
        final serverResponse = ServerResponse.ok()
                .bodyValue(CreationOutput.of(false))

        and:
        1 * studentHandlerMock.addStudent(_) >> serverResponse

        expect:
        webTestClient.post()
                .uri('/api/student/')
                .bodyValue(student)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CreationOutput.class)
                .value({ result ->
                    assert !result.isCreated()
                })
    }

    void 'GET /api/student/{id} should return response with corresponding student'() {
        given:
        final student = Student.builder()
                .name('testName')
                .id('testId')
                .build()
        final serverResponse = ServerResponse.ok()
                .bodyValue(student)

        and:
        1 * studentHandlerMock.getStudent(_) >> serverResponse

        expect:
        webTestClient.get()
                .uri('/api/student/testId')
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Student.class)
                .value({ result ->
                    verifyAll(result, Student) {
                        id == 'testId'
                        name == 'testName'
                    }
                })
    }
}
