package com.krasnopolskyi.dto.request;

import com.krasnopolskyi.validation.group.Create;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TrainerDto {
    private Long id;

    @NotBlank(groups = Create.class, message = "First name can't be null")
    @Size(groups = Create.class, min = 2, max = 32, message = "First name must be between 2 and 32 characters")
    private String firstName;

    @NotBlank(groups = Create.class, message = "Last name can't be null")
    @Size(groups = Create.class, min = 2, max = 32, message = "Last name must be between 2 and 32 characters")
    private String lastName;

    @NotNull(message = "Specialization cannot be null")
    private Integer specialization;
}
