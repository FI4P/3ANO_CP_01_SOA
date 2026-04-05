package com.fiap.soa.booking_room.dto.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingRequestDTO(
        @NotNull(message = "ID da sala é obrigatório")
        Long roomId,

        @NotNull(message = "ID do funcionário é obrigatório")
        Long employeeId,

        @NotNull(message = "Data é obrigatória")
        @FutureOrPresent(message = "Data não pode ser no passado")
        LocalDate date,

        @NotNull(message = "Horário de início é obrigatório")
        LocalTime startTime,

        @NotNull(message = "Horário de fim é obrigatório")
        LocalTime endTime,

        @NotBlank(message = "Finalidade é obrigatória")
        String purpose
) {}
