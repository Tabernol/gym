package com.krasnopolskyi.dto.response;


import java.time.LocalDate;

public record TraineeResponseDto(String firstName,
                                 String lastName,
                                 String username,
                                 LocalDate dateOfBirth,
                                 String address) {
}
