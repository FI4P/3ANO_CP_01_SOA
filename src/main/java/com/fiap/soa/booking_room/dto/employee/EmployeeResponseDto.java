package com.fiap.soa.booking_room.dto.employee;


import lombok.Builder;

@Builder
public record EmployeeResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
