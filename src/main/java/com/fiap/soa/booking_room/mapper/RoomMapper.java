package com.fiap.soa.booking_room.mapper;

import com.fiap.soa.booking_room.domain.Room;
import com.fiap.soa.booking_room.dto.room.RoomRequestDTO;
import com.fiap.soa.booking_room.dto.room.RoomResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequestDTO dto) {
        return Room.builder()
                .name(dto.name())
                .capacity(dto.capacity())
                .location(dto.location())
                .status(dto.status())
                .build();
    }

    public RoomResponseDTO toResponse(Room room) {
        return new RoomResponseDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getLocation(),
                room.getStatus()
        );
    }
}