package com.fiap.soa.booking_room.service.employee;


import com.fiap.soa.booking_room.domain.Employee;
import com.fiap.soa.booking_room.dto.employee.EmployeeRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeResponseDto;
import com.fiap.soa.booking_room.infrastructure.excpetion.DuplicatedEntityException;
import com.fiap.soa.booking_room.mapper.EmployeeMapper;
import com.fiap.soa.booking_room.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public EmployeeResponseDto findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found!"));
        return employeeMapper.toResponse(employee);
    }


    public void update(EmployeeRequestDto dto,  Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found!"));

        employee.setFirstName(dto.firstName() != null ? dto.firstName() : dto.firstName());
        employee.setLastName(dto.lastName() != null ? dto.lastName() : dto.lastName());

        if (dto.password() != null) {
            employee.setPassword(passwordEncoder.encode(dto.password()));
        }

        employeeRepository.save(employee);

    }



}
