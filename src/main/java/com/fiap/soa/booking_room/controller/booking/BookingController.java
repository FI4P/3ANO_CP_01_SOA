package com.fiap.soa.booking_room.controller.booking;

import com.fiap.soa.booking_room.dto.booking.BookingRequestDTO;
import com.fiap.soa.booking_room.dto.booking.BookingResponseDTO;
import com.fiap.soa.booking_room.service.booking.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> create(@Valid @RequestBody BookingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }
}