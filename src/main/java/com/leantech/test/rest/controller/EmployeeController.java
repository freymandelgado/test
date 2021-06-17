package com.leantech.test.rest.controller;

import com.leantech.test.domain.model.Employee;
import com.leantech.test.domain.service.EmployeeService;
import com.leantech.test.rest.dto.EmployeeDto;
import com.leantech.test.rest.dto.PositionDto;
import com.leantech.test.rest.dto.UpdateEmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public String status(){
        return "All good";
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee createEmployee(@RequestBody @Valid EmployeeDto request) throws Exception {
        return employeeService.createEmployee(request);
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateEmployee(@RequestBody @Valid UpdateEmployeeDto request) throws Exception {
        return employeeService.updateEmployee(request);
    }

    @PatchMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteEmployee(@RequestParam String idEmployee) throws Exception {
        System.out.println(idEmployee);
        return employeeService.deleteEmployee(Long.parseLong(idEmployee));
    }

    @GetMapping(path = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployees(@RequestParam Optional<String> positionName, @RequestParam Optional<String> name) throws Exception {
        return employeeService.getAllEmployees(positionName, name);
    }

    @GetMapping(path = "/positions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PositionDto> getAllPositions() throws Exception {
        return employeeService.getAllPositions();
    }


}
