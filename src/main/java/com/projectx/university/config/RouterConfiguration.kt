package com.projectx.university.config

import com.projectx.university.handler.StudentHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterConfiguration {
    @Bean
    fun studentRouter(studentHandler: StudentHandler): RouterFunction<ServerResponse> =
            route()
                    .GET("/api/student/{id}", studentHandler::getStudent)
                    .POST("/api/student/", studentHandler::addStudent)
                    .build()
}