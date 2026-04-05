package com.fiap.soa.booking_room.infrastructure.excpetion;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
