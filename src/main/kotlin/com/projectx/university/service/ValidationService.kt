package com.projectx.university.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.validation.*
import org.springframework.web.server.ServerWebInputException

@Service
class ValidationService(@Qualifier("defaultValidator") private val validator: Validator) {
    fun <T> validate(dto: T, dtoClass: Class<T>): T {
        val errors: Errors = BeanPropertyBindingResult(dto, dtoClass.name)
        validator.validate(dto, errors)
        if (errors.hasErrors()) {
            val errorMessages = errors.allErrors.asSequence()
                    .map { getErrorMessage(it) }
                    .sorted()
                    .joinToString(separator = "; ")
            throw ServerWebInputException(errorMessages)
        }
        return dto
    }

    private fun getErrorMessage(error: ObjectError): String =
            if (error is FieldError)
                "'${error.field}' property ${error.defaultMessage}"
            else error.defaultMessage ?: ""
}