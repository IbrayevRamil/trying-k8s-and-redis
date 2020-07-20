package com.projectx.student.config;

import com.projectx.student.dto.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    ReactiveRedisOperations<String, Student> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        final RedisSerializer<Student> serializer = new Jackson2JsonRedisSerializer<>(Student.class);
        final RedisSerializationContextBuilder<String, Student> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        final RedisSerializationContext<String, Student> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
