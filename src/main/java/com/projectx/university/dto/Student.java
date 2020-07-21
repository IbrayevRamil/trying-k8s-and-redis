package com.projectx.university.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
public class Student {
    @NotEmpty
    private final String id;
    @NotEmpty
    private final String name;
}
