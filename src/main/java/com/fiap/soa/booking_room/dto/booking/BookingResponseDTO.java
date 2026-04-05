package com.fiap.soa.booking_room.dto.booking;

import com.fiap.soa.booking_room.domain.Booking;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponseDTO(
        Long id,
        Long roomId,
        String roomName,
        Long employeeId,
        String employeeName,
        String employeeEmail,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String purpose,
        Booking.BookingStatus status
) {}