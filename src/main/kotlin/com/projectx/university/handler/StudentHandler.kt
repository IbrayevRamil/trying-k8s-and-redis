package com.projectx.university.handler

import com.projectx.university.dto.Student
import com.projectx.university.service.StudentService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class StudentHandler(private val studentService: StudentService) {
    fun getStudent(serverRequest: ServerRequest): Mono<ServerResponse> =
            studentService.getStudent(serverRequest.pathVariable("id"))
                    .flatMap { ok().bodyValue(it) }
                    .switchIfEmpty(notFound().build())

    fun addStudent(serverRequest: ServerRequest): Mono<ServerResponse> =
            serverRequest.bodyToMono(Student::class.java)
                    .flatMap { studentService.addStudent(it) }
                    .flatMap { ok().bodyValue(it) }
}