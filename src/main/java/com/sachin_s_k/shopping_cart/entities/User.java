package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,name = "name")
    private String name;
    @Column(nullable = false,name = "email")
    private String email;
    @Column(nullable = false,name = "password")
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
   @Builder.Default
private List<Address> addresses= new ArrayList<>();

    public void addAddress(Address address){
        addresses.add(address);
        address.setUser(this);
    }
    public void removeAddress(Address address){
        addresses.remove(address);
        address.setUser(null);
    }
    public void addTag(Tag tag) {
        this.tags.add(tag);

        if (tag.getUsers() == null) {
            tag.setUsers(new HashSet<>());
        }

        tag.getUsers().add(this);
    }
     @ManyToMany
     @Builder.Default
     @JoinTable(name = "user_tags", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags= new HashSet<>();
//    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
//
//    private Profile profile;
    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlist = new HashSet<>();


}
