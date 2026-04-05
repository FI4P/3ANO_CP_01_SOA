package com.fiap.soa.booking_room.dto.room;

import com.fiap.soa.booking_room.domain.Room.RoomStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RoomRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull @Positive(message = "Capacidade deve ser positiva")
        Integer capacity,

        @NotBlank(message = "Localização é obrigatória")
        String location,

        @NotNull(message = "Status é obrigatório")
        RoomStatus status
) {}
