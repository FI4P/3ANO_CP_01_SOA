package com.fiap.soa.booking_room.infrastructure.excpetion;

public class DuplicatedEntityException extends RuntimeException {
    public DuplicatedEntityException(String message) {
        super(message);
    }
}
