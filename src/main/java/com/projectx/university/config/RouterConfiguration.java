package com.projectx.university.config;

import com.projectx.university.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {
    @Bean
    public RouterFunction<ServerResponse> changeTrackingRouter(StudentHandler studentHandler) {
        return route()
                .GET("/api/student/{id}", studentHandler::getStudent)
                .POST("/api/student/", studentHandler::addStudent)
                .build();
    }
}
