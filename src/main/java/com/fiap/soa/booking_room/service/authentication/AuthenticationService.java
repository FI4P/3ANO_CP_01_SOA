package com.fiap.soa.booking_room.service.authentication;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fiap.soa.booking_room.domain.Employee;
import com.fiap.soa.booking_room.dto.authentication.AuthenticationRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeResponseDto;
import com.fiap.soa.booking_room.infrastructure.excpetion.BadCredentialsException;
import com.fiap.soa.booking_room.infrastructure.excpetion.DuplicatedEntityException;
import com.fiap.soa.booking_room.infrastructure.excpetion.EntityNotFound;
import com.fiap.soa.booking_room.mapper.EmployeeMapper;
import com.fiap.soa.booking_room.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class AuthenticationService  {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;

    @Value("${api.security.token.secret}")
    private String secret;


    public AuthenticationService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    public String signIn(AuthenticationRequestDto dto){

        Employee employee = employeeRepository.findByEmail(dto.email()).orElseThrow(() -> new EntityNotFound("Employee not found"));

        boolean passwordMatch = passwordEncoder.matches(dto.password(), employee.getPassword());

        if (!passwordMatch) {
            throw new BadCredentialsException("Invalid credentials!");
        }


        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create().withIssuer("fiapbooking")
                .withSubject(employee.getId().toString())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(1)))
                .sign(algorithm);

        return token;

    }

    public EmployeeResponseDto signUp(EmployeeRequestDto dto){
        Optional<Employee> duplicatedEmployee = employeeRepository.findByEmail(dto.email());

        duplicatedEmployee.ifPresent(employee -> {
            throw new DuplicatedEntityException("User already exists!");
        });

        Employee newEmployee = employeeMapper.toEntity(dto);

        String hashedPwd = passwordEncoder.encode(newEmployee.getPassword());
        newEmployee.setPassword(hashedPwd);

        newEmployee = employeeRepository.save(newEmployee);

        return employeeMapper.toResponse(newEmployee);
    }

}
