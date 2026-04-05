package com.fiap.soa.booking_room.dto.room;
import com.fiap.soa.booking_room.domain.Room;

public record RoomResponseDTO(
        Long id,
        String name,
        Integer capacity,
        String location,
        Room.RoomStatus status
) {}