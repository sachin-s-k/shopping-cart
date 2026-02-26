package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@Setter
@ToString
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    @ToString.Exclude
    private Set<User> users= new HashSet<>();
    public Tag(String name) {
        this.name = name;
    }

}
