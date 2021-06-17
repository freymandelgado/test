package com.leantech.test.repository.dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "positions")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long id;
    @Column(nullable = false)
    private String name;

    @Override
    public String toString() {
        return "PositionEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
