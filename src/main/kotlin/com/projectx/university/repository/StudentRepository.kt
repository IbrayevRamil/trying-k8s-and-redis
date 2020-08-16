package com.projectx.university.repository

import com.projectx.university.dto.Student
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class StudentRepository(private val redisTemplate: ReactiveRedisOperations<String, Student>) {
    fun addStudent(student: Student): Mono<Boolean> =
            redisTemplate.opsForValue().set(student.id, student)

    fun getStudent(studentId: String): Mono<Student> =
            redisTemplate.opsForValue().get(studentId)
}