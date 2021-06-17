package com.leantech.test.domain.service;

import com.leantech.test.domain.model.Employee;
import com.leantech.test.rest.dto.EmployeeDto;
import com.leantech.test.rest.dto.PositionDto;
import com.leantech.test.rest.dto.UpdateEmployeeDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee createEmployee(EmployeeDto dto) throws Exception;

    public Employee updateEmployee(UpdateEmployeeDto dto) throws Exception;

    public boolean deleteEmployee(Long employeeId) throws Exception;

    public List<Employee> getAllEmployees(Optional<String> positionName, Optional<String> name);

    public List<PositionDto> getAllPositions();
}
