package com.leantech.test.repository.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table( name = "employees")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;
    @OneToOne
    @JoinColumn(name = "position_id", foreignKey = @ForeignKey(name = "FK_positions"))
    private PositionEntity position;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    private String status;
}
