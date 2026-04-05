package com.fiap.soa.booking_room.dto.authentication;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}
