package com.projectx.university.repository;

import com.projectx.university.dto.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final ReactiveRedisOperations<String, Student> redisTemplate;

    public Mono<Boolean> addStudent(Student student) {
        return redisTemplate.opsForValue().set(student.getId(), student);
    }

    public Mono<Student> getStudent(String studentId) {
        return redisTemplate.opsForValue().get(studentId);
    }
}

