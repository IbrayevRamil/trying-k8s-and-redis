package com.projectx.student.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreationOutput {
    private final boolean isCreated;
}
