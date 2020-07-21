package com.projectx.university.service

import com.projectx.university.dto.Student
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.Validator
import org.springframework.web.server.ServerWebInputException
import spock.lang.Specification

class ValidationServiceTest extends Specification {
    ValidationService sut
    Validator validatorMock

    void setup() {
        validatorMock = Mock()
        sut = new ValidationService(validatorMock)
    }

    void 'validateInput should not thrown any exceptions when has no validation errors'() {
        given:
        final input = Student.builder().build()

        when:
        sut.validateInput(input)

        then:
        1 * validatorMock.validate(input, _)
        noExceptionThrown()
    }

    void 'validateInput should throw ServerWebInputException when has validation errors'() {
        given:
        final input = Student.builder().name('testName').build()
        final validationError = new FieldError("someObject", "id", "is bad")

        when:
        sut.validateInput(input)

        then:
        1 * validatorMock.validate(input, _) >> { args -> args[1].addError(validationError) }
        final ex = thrown(ServerWebInputException)
        assert ex.status == HttpStatus.BAD_REQUEST
        assert ex.reason == "'id' property is bad"
    }
}
