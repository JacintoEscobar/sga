package com.jasi.sga.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUpdateCourseRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
