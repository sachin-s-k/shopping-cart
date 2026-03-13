package com.sachin_s_k.shopping_cart.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "customer_id")
    @ManyToOne
    private User customer;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus orderStatus;
    @Column(name = "created_at",insertable = false,updatable = false)
     private LocalDateTime createdAt;
     @OneToMany(mappedBy = "order",cascade = CascadeType.PERSIST)
     private Set<OrderItem> items=new LinkedHashSet<>();
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    public static Order fromCart(Cart cart,User customer){
        var order= new Order();
        order.setCustomer(customer);
        order.setOrderStatus(PaymentStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getCartItems().forEach(cartItem -> {
            var orderItem=new OrderItem(
                    order,cartItem.getProduct(),
                    cartItem.getQuantity()
           );
            order.getItems().add(orderItem);

    });
        return  order;

}

 public boolean isPlacedBy(User user){
        return  this.customer.equals(user);
 }
}