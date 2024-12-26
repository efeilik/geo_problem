package com.weatherapp.geo_spring.model;

import com.weatherapp.geo_spring.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String address;

    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING) // Enum'ı String olarak sakla
    private Role role;
}
