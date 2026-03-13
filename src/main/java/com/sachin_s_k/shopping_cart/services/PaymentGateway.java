package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutsession(Order order);
    Optional<PaymentResult> parseWebhookEvent(WebhookRequest request);
}
