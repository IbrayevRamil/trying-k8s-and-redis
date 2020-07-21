package com.projectx.university.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreationOutput {
    private final boolean isCreated;
}
