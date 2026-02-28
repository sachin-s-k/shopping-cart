package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private byte id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Product> products= new HashSet<>();

    public Category(byte id) {
        this.id=id;
    }

    public void addProduct(Product product){
        products.add(product);
        product.setCategory(this);
    }
}
