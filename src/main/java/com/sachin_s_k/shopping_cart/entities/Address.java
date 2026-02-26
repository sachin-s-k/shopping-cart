package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@ToString
@Setter
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "zip",nullable = false)
    private String zip;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "state",nullable = false)
    private String state;
    @ManyToOne(   fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @ToString.Exclude
    private User user;

}
