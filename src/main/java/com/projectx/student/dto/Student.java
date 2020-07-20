package com.projectx.student.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Student {
    private final String id;
    private final String name;
}
