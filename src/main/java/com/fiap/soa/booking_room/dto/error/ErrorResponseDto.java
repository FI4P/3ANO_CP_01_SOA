package com.fiap.soa.booking_room.dto.error;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        int status,
        String messsage,
        LocalDateTime timestamp
) {
}
