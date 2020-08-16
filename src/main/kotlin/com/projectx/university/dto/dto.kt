package com.projectx.university.dto

import javax.validation.constraints.NotNull

data class Student(@field:NotNull val id: String,
                   @field:NotNull val name: String)

data class CreationOutput(val isCreated: Boolean)