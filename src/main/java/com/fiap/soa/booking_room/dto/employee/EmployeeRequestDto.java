package com.fiap.soa.booking_room.dto.employee;

public record EmployeeRequestDto(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
