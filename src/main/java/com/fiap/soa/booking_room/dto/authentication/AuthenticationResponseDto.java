package com.fiap.soa.booking_room.dto.authentication;

import java.time.LocalDateTime;

public record AuthenticationResponseDto(
        String token,
        LocalDateTime timeStamp
) {
}
