package com.krasnopolskyi.dto.request;

import com.krasnopolskyi.validation.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TraineeDto {
    private Long id;

    @NotBlank(groups = Create.class, message = "First can't be null")
    @Size(groups = Create.class, min = 2, max = 32, message = "First name must be between 2 and 32 characters")
    private String firstName;

    @NotBlank(groups = Create.class, message = "First can't be null")
    @Size(groups = Create.class, min = 2, max = 32, message = "Last name must be between 2 and 32 characters")
    private String lastName;

    private LocalDate dateOfBirth;

    @Size(groups = Create.class, min = 2, max = 256, message = "Address must be between less than 256 characters")
    private String address;
}
