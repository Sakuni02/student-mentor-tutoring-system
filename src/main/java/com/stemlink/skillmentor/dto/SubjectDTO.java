package com.stemlink.skillmentor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectDTO {

    @NotNull(message = "Can not be null")
    @Size(min = 5,max = 20,message = "Subject must be at least 5 characters long")
    private String subjectName;

    @Size(max = 100,message = "Description must not exceed 100 characters")
    private String description;

    @NotNull
    private Long mentorId;
}
