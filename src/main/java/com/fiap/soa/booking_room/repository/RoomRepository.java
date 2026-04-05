package com.fiap.soa.booking_room.repository;

import com.fiap.soa.booking_room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
