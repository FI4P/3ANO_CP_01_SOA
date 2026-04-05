package com.fiap.soa.booking_room.controller.employee;


import com.fiap.soa.booking_room.dto.employee.EmployeeRequestDto;
import com.fiap.soa.booking_room.dto.employee.EmployeeResponseDto;
import com.fiap.soa.booking_room.service.employee.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<EmployeeResponseDto> findById(@RequestAttribute("id") String id) {
        Long employeeId = Long.parseLong(id);
        EmployeeResponseDto employeeResponseDto = employeeService.findById(employeeId);
        return ResponseEntity.ok(employeeResponseDto);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EmployeeRequestDto employeeRequestDto, @RequestAttribute("id") String id) {
        employeeService.update(employeeRequestDto, Long.parseLong(id));
        return ResponseEntity.ok().build();
    }
}
