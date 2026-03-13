package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.dtos.OrderDto;
import com.sachin_s_k.shopping_cart.exception.OrderNotFoundException;
import com.sachin_s_k.shopping_cart.mappers.OrderMapper;
import com.sachin_s_k.shopping_cart.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class OrderService {
    private final   OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;
    public OrderDto getOrder(Long orderId) {
   var order=  orderRepository.gerOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
 var user=  authService.getCurrentUser();
 if(!order.isPlacedBy(user)){
     throw new AccessDeniedException("You don't have access to this order");

 }

return orderMapper.toDto(order);



    }
}
