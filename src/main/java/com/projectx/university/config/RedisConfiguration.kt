package com.projectx.university.config

import com.projectx.university.dto.Student
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder
import org.springframework.data.redis.serializer.RedisSerializationContext.newSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {
    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisOperations<String, Student> {
        val serializer: RedisSerializer<Student> = Jackson2JsonRedisSerializer<Student>(Student::class.java)
        val builder: RedisSerializationContextBuilder<String, Student> = newSerializationContext(StringRedisSerializer())
        val context: RedisSerializationContext<String, Student> = builder
                .value(serializer)
                .build()
        return ReactiveRedisTemplate<String, Student>(factory, context)
    }
}