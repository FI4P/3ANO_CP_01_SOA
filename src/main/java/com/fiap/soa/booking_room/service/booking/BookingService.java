package com.fiap.soa.booking_room.service.booking;

import com.fiap.soa.booking_room.domain.Booking;
import com.fiap.soa.booking_room.domain.Employee;
import com.fiap.soa.booking_room.domain.Room;
import com.fiap.soa.booking_room.dto.booking.BookingRequestDTO;
import com.fiap.soa.booking_room.dto.booking.BookingResponseDTO;
import com.fiap.soa.booking_room.infrastructure.excpetion.BusinessException;
import com.fiap.soa.booking_room.mapper.BookingMapper;
import com.fiap.soa.booking_room.repository.BookingRepository;
import com.fiap.soa.booking_room.repository.EmployeeRepository;
import com.fiap.soa.booking_room.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final EmployeeRepository employeeRepository;
    private final BookingMapper bookingMapper;

    public BookingResponseDTO create(BookingRequestDTO dto) {
        if (!dto.endTime().isAfter(dto.startTime())) {
            throw new BusinessException(
                    "Horário de fim deve ser maior que o horário de início",
                    HttpStatus.BAD_REQUEST);
        }

        Room room = roomService.getRoomEntityById(dto.roomId());

        if (room.getStatus() == Room.RoomStatus.INACTIVE) {
            throw new BusinessException(
                    "Não é possível reservar uma sala inativa",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Busca o employee — 404 se não existir
        Employee employee = employeeRepository.findById(dto.employeeId())
                .orElseThrow(() -> new BusinessException(
                        "Funcionário não encontrado com id: " + dto.employeeId(),
                        HttpStatus.NOT_FOUND));

        boolean conflict = bookingRepository.existsConflict(
                room.getId(), dto.date(), dto.startTime(), dto.endTime());
        if (conflict) {
            throw new BusinessException(
                    "Já existe reserva para esta sala no horário informado",
                    HttpStatus.CONFLICT);
        }

        Booking booking = bookingMapper.toEntity(dto, room, employee);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    public List<BookingResponseDTO> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    public BookingResponseDTO findById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toResponse)
                .orElseThrow(() -> new BusinessException(
                        "Reserva não encontrada com id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    public BookingResponseDTO cancel(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Reserva não encontrada com id: " + id,
                        HttpStatus.NOT_FOUND));

        if (booking.getStatus() == Booking.BookingStatus.CANCELADA) {
            throw new BusinessException(
                    "Reserva já está cancelada",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        booking.setStatus(Booking.BookingStatus.CANCELADA);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }
}