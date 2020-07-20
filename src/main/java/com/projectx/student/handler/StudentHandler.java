package com.projectx.student.handler;

import com.projectx.student.dto.Student;
import com.projectx.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class StudentHandler {
    private final StudentService studentService;

    @NonNull
    public Mono<ServerResponse> getStudent(ServerRequest serverRequest) {
        return studentService.getStudent(serverRequest.pathVariable("id"))
                .flatMap(student -> ok().bodyValue(student));
    }

    @NonNull
    public Mono<ServerResponse> createStudent(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Student.class)
                .flatMap(studentService::addStudent)
                .flatMap(result -> ok().bodyValue(result));
    }
}
