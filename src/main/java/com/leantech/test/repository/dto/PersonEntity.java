package com.leantech.test.repository.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "Id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String cellphone;
    @Column(nullable = false)
    private String city_name;
}
