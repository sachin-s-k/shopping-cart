package com.sachin_s_k.shopping_cart.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class WebhookRequest {
    private Map<String,String> headers;
    private String payload;
}
