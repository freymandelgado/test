package com.leantech.test.domain.service.imp;

import com.google.gson.Gson;
import com.leantech.test.domain.model.Employee;
import com.leantech.test.domain.model.Person;
import com.leantech.test.domain.model.Position;
import com.leantech.test.domain.model.Status;
import com.leantech.test.domain.service.EmployeeService;
import com.leantech.test.repository.EmployeeRepository;
import com.leantech.test.repository.PersonRepository;
import com.leantech.test.repository.PositionRepository;
import com.leantech.test.repository.dto.EmployeeEntity;
import com.leantech.test.repository.dto.PersonEntity;
import com.leantech.test.repository.dto.PositionEntity;
import com.leantech.test.rest.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final PositionRepository positionRepository;

    @Override
    public Employee createEmployee(EmployeeDto dto) throws Exception {

        log.info("Creating employee");

        var person = personRepository.save(PersonEntity.builder().name(dto.getName()).last_name(dto.getLastName()).address(dto.getAddress()).cellphone(dto.getCellphone()).city_name(dto.getCityName()).build());
        var position = positionRepository.save(PositionEntity.builder().name(dto.getPositionName()).build());
        var employee = employeeRepository.save(EmployeeEntity.builder().salary(dto.getSalary()).status(Status.ACTIVE.getStatus()).build());

        return mapEmployee(employee, position, person);
    }

    private Employee mapEmployee(EmployeeEntity employee, PositionEntity position, PersonEntity person) {
        return Employee.builder()
                .id(employee.getId().toString())
                .person(Person.builder()
                        .id(person.getId().toString())
                        .name(person.getName())
                        .lastName(person.getLast_name())
                        .address(person.getAddress())
                        .cellphone(person.getCellphone())
                        .cityName(person.getCity_name())
                        .build())
                .position(Position.builder()
                        .id(position.getId().toString())
                        .name(position.getName())
                        .build())
                .salary(employee.getSalary())
                .build();
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeDto dto) throws Exception {
        log.info("Updating employee with id " + dto.getId());

        var optionalEmployeeEntity = employeeRepository.findById(Long.parseLong(dto.getId()));

        EmployeeEntity employeeUpdated = optionalEmployeeEntity.orElseThrow(() -> new Exception("EmployeeEntity with id " + dto.getId() + " doesn't exist"));
        employeeUpdated.setSalary(dto.getSalary().orElse(employeeUpdated.getSalary()));

        var optionalPersonEntity = personRepository.findById(Long.parseLong(dto.getId()));

        PersonEntity personUpdated = optionalPersonEntity.orElseThrow(() -> new Exception("EmployeeEntity with id " + dto.getId() + " doesn't exist"));
        personUpdated.setName(dto.getName().orElse(personUpdated.getName()));
        personUpdated.setLast_name(dto.getLastName().orElse(personUpdated.getLast_name()));
        personUpdated.setCellphone(dto.getCellphone().orElse(personUpdated.getCellphone()));
        personUpdated.setAddress(dto.getAddress().orElse(personUpdated.getAddress()));
        personUpdated.setCity_name(dto.getCityName().orElse(personUpdated.getCity_name()));

        var optionalPositionEntity = positionRepository.findById(Long.parseLong(dto.getId()));

        PositionEntity positionUpdated = optionalPositionEntity.orElseThrow(() -> new Exception("PersonEntity with id " + employeeUpdated.getPosition() + " doesn't exist"));
        positionUpdated.setName(dto.getPositionName().orElse(positionUpdated.getName()));

        employeeUpdated.setPerson(personUpdated);
        employeeUpdated.setPosition(positionUpdated);

        var employee = employeeRepository.save(employeeUpdated);
        var person = personRepository.save(personUpdated);
        var position = positionRepository.save(positionUpdated);
        return mapEmployee(employee, position, person);
    }

    @Override
    public boolean deleteEmployee(Long employeeId) throws Exception {
        log.info("Deleting employee with id " + employeeId);
        var optionalEmployeeEntity = employeeRepository.findById(employeeId);

        EmployeeEntity employeeUpdated = optionalEmployeeEntity.orElseThrow(() -> new Exception("EmployeeEntity with id " + employeeId + " doesn't exist"));
        employeeUpdated.setStatus(Status.UNACTIVE.getStatus());

        var employee = employeeRepository.save(employeeUpdated);
        return employee.getStatus() == Status.UNACTIVE.getStatus() ? true : false;
    }

    @Override
    public List<Employee> getAllEmployees(Optional<String> positionName, Optional<String> name) {
        log.info("getting all employees ");

        String status = Status.ACTIVE.getStatus();
        List<EmployeeEntity> employeeEntities;
        if (positionName.isPresent()) {
            employeeEntities = employeeRepository.findAllByStatusAndPositionName(status, positionName.get());
        } else {
            employeeEntities = employeeRepository.findAllByStatus(status);
        }

        return employeeEntities.stream().map(employeeEntity -> {
            Optional<PersonEntity> optionalPersonEntity = personRepository.findById(employeeEntity.getId());
            Optional<PositionEntity> optionalPositionEntity = positionRepository.findById(employeeEntity.getId());
            PersonEntity personEntity = null;
            PositionEntity positionEntity = null;
            try {
                personEntity = optionalPersonEntity.orElseThrow(() -> new Exception("PersonEntity with id " + employeeEntity.getId() + " doesn't exist"));
                positionEntity = optionalPositionEntity.orElseThrow(() -> new Exception("PositionEntity with id " + employeeEntity.getId() + " doesn't exist"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mapEmployee(employeeEntity, positionEntity, personEntity);
        }).collect(Collectors.toList());
    }

    @Override
    public List<PositionDto> getAllPositions() {
        log.info("getting all Positions ");
        Gson gson = new Gson();

        String status = Status.ACTIVE.getStatus();

        return positionRepository.findAll().stream().map(positionEntity -> {
            List<EmployeeEntity> employeeEntities = employeeRepository.findAllByStatusAndId(status, positionEntity.getId());
            List<EmployeeEntity> employeeEntitiesWithPerson = employeeEntities.stream().map(employeeEntity -> {
                try {
                    PersonEntity personEntity = personRepository.findById(employeeEntity.getId()).orElseThrow(() -> new Exception("PersonEntity with id " + employeeEntity.getId() + " doesn't exist"));
                    employeeEntity.setPerson(personEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return employeeEntity;
            }).collect(Collectors.toList());

            log.info(gson.toJson(employeeEntitiesWithPerson));
            return mapPositions(employeeEntitiesWithPerson, positionEntity);
        }).collect(Collectors.toList());
    }

    private PositionDto mapPositions(List<EmployeeEntity> employees, PositionEntity position) {
        log.info("mapPositions");
        return PositionDto.builder()
                .id(position.getId())
                .name(position.getName())
                .employees(employees.stream().map(employeeEntity -> EmployeeBySalaryDto.builder()
                        .id(employeeEntity.getId())
                        .salary(employeeEntity.getSalary())
                        .person(PersonDto.builder()
                                .name(employeeEntity.getPerson().getName())
                                .lastName(employeeEntity.getPerson().getLast_name())
                                .address(employeeEntity.getPerson().getAddress())
                                .cellphone(employeeEntity.getPerson().getCellphone())
                                .cityName(employeeEntity.getPerson().getCity_name())
                                .build()
                        )
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
