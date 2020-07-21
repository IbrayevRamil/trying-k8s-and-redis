package com.projectx.university.handler;

import com.projectx.university.dto.Student;
import com.projectx.university.service.StudentService;
import com.projectx.university.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class StudentHandler {
    private final StudentService studentService;
    private final ValidationService validationService;

    @NonNull
    public Mono<ServerResponse> getStudent(ServerRequest serverRequest) {
        return studentService.getStudent(serverRequest.pathVariable("id"))
                .flatMap(student -> ok().bodyValue(student))
                .switchIfEmpty(notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> addStudent(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Student.class)
                .map(validationService::validateInput)
                .flatMap(studentService::addStudent)
                .flatMap(result -> ok().bodyValue(result));
    }
}
