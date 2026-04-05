package com.fiap.soa.booking_room.service.room;
import com.fiap.soa.booking_room.domain.Room;
import com.fiap.soa.booking_room.dto.room.RoomRequestDTO;
import com.fiap.soa.booking_room.dto.room.RoomResponseDTO;
import com.fiap.soa.booking_room.infrastructure.excpetion.BusinessException;
import com.fiap.soa.booking_room.mapper.RoomMapper;
import com.fiap.soa.booking_room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomResponseDTO create(RoomRequestDTO dto) {
        Room room = roomMapper.toEntity(dto);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    public List<RoomResponseDTO> findAll() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    public RoomResponseDTO findById(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::toResponse)
                .orElseThrow(() -> new BusinessException(
                        "Sala não encontrada com id: " + id, HttpStatus.NOT_FOUND));
    }

    // Método interno usado pelo BookingService
    public Room getRoomEntityById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Sala não encontrada com id: " + id, HttpStatus.NOT_FOUND));
    }
}
