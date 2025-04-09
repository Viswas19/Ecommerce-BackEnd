package com.example.project.entity;


import com.example.project.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is Required")
    private String name;
    @Column(unique = true)
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    private String password;
    @Column(name = "phone_number")
    @NotBlank(message = "Phone number is Required")
    private String phoneNumber;
    private UserRole role;
    @OneToMany(mappedBy = "user" , fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private Address address;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

}
