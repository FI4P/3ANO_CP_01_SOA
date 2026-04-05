package com.fiap.soa.booking_room.infrastructure.excpetion;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String message) {
        super(message);
    }
}
