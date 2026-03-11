package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDate dateCreated;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public CartItem getItem(Long productId) {
        return cartItems.stream().filter(cartItemOne -> cartItemOne.getProduct().getId().equals(productId)).findFirst().orElse(null);

    }

    public CartItem addItem(Product product) {
        var cartItem = getItem(product.getId());

        if (cartItem != null) {

            cartItem.setQuantity(cartItem.getQuantity() + 1);
          return  cartItem;
        } else {
            System.out.println("entered");
            var newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setCart(this);
            cartItems.add(newCartItem);
            return cartItem;
        }

    }
    public void removeItem(Long productId) {
        var cartItem = getItem(productId);
        if (cartItem != null) {
            cartItems.remove(cartItem);
            cartItem.setCart(null);
        }


    }
    public void clearItems(){
        cartItems.clear();
    }

    public boolean isEmpty(){
        return  cartItems.isEmpty();
    }
}
