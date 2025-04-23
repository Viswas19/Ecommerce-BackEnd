package com.example.project.entity;


import com.example.project.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "Name must not start or end with spaces")
    private String name;
    @Column(unique = true)
    @Pattern(regexp = "^[^\\s]+$", message = "Email must not contain spaces")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required")
    @Pattern(regexp = "^[^\\s].*[^\\s]$", message = "Password must not start or end with spaces")
    private String password;
    @Column(name = "phone_number")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must start with 6, 7, 8, or 9 and should have 10 digits and It Should Contain Only Numbers from 1 to 9")
    private String phoneNumber;
    private UserRole role;
    @OneToMany(mappedBy = "user" , fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private Address address;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

}
