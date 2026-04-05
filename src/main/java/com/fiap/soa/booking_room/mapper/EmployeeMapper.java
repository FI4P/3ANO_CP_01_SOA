package com.fiap.soa.booking_room.mapper;

import com.fiap.soa.booking_room.domain.Employee;
import com.fiap.soa.booking_room.dto.employee.EmployeeRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDto dto) {
        return Employee.builder().firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(dto.password()).build();
    }

    public EmployeeResponseDto toResponse(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
    }

}
