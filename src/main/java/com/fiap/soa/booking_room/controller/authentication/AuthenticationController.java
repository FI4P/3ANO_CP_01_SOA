package com.fiap.soa.booking_room.controller.authentication;


import com.fiap.soa.booking_room.dto.authentication.AuthenticationRequestDto;
import com.fiap.soa.booking_room.dto.authentication.AuthenticationResponseDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeResponseDto;
import com.fiap.soa.booking_room.service.authentication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto body) {
            String token = authenticationService.signIn(body);
            return ResponseEntity.ok(new AuthenticationResponseDto(token, LocalDateTime.now()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<EmployeeResponseDto> create(@RequestBody EmployeeRequestDto body) {
        EmployeeResponseDto employee = authenticationService.signUp(body);

        URI uri = URI.create("/api/v1/employee" + employee.id());

        return ResponseEntity.created(uri).body(employee);
    }

}
