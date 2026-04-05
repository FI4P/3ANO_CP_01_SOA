package com.fiap.soa.booking_room.mapper;

import com.fiap.soa.booking_room.domain.Booking;
import com.fiap.soa.booking_room.domain.Employee;
import com.fiap.soa.booking_room.domain.Room;
import com.fiap.soa.booking_room.dto.booking.BookingRequestDTO;
import com.fiap.soa.booking_room.dto.booking.BookingResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public Booking toEntity(BookingRequestDTO dto, Room room, Employee employee) {
        return Booking.builder()
                .room(room)
                .employee(employee)
                .date(dto.date())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .purpose(dto.purpose())
                .status(Booking.BookingStatus.CONFIRMADA)
                .build();
    }

    public BookingResponseDTO toResponse(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getEmployee().getId(),
                booking.getEmployee().getFirstName() + " " + booking.getEmployee().getLastName(),
                booking.getEmployee().getEmail(),
                booking.getDate(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getPurpose(),
                booking.getStatus()
        );
    }
}