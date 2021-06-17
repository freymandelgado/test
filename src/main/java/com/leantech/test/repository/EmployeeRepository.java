package com.leantech.test.repository;

import com.leantech.test.repository.dto.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAllByStatus(String status);
    List<EmployeeEntity> findAllByStatusAndId(String status, Long id);
    List<EmployeeEntity> findAllByStatusAndPositionName(String status, String positionName);
}
