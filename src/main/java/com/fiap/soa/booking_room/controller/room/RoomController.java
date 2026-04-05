package com.fiap.soa.booking_room.controller.room;
import com.fiap.soa.booking_room.dto.room.RoomRequestDTO;
import com.fiap.soa.booking_room.dto.room.RoomResponseDTO;
import com.fiap.soa.booking_room.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@Valid @RequestBody RoomRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }
}