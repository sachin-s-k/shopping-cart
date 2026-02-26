package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    @ToString.Include(name = "user")
    public String getUserStatus() {
        return user == null ? "null" : "present";
    }
}