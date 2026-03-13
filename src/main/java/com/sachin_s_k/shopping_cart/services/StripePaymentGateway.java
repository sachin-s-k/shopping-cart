package com.sachin_s_k.shopping_cart.services;

import com.sachin_s_k.shopping_cart.dtos.CheckoutResponse;
import com.sachin_s_k.shopping_cart.entities.Order;
import com.sachin_s_k.shopping_cart.entities.OrderItem;
import com.sachin_s_k.shopping_cart.entities.PaymentStatus;
import com.sachin_s_k.shopping_cart.exception.PaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;
    @Value("${stripe.webhookSecretKey}")
    private String stripeWebhookSecretKey;

    @Override
    public CheckoutSession createCheckoutsession(Order order) {
        try{
        var builder = SessionCreateParams.builder().
                setMode(SessionCreateParams.Mode.PAYMENT).
                setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId()).
                setCancelUrl(websiteUrl + "/checkout-cancel").putMetadata("order_id",order.getId().toString());

        order.getItems().forEach(orderItem -> {

            var lineItem = createLineItem(orderItem);

            builder.addLineItem(lineItem);
        });
        var session = Session.create(builder.build());


        return new CheckoutSession(session.getUrl());


    }catch(StripeException ex)

    {


        throw new PaymentException();

    }



}

    @Override
    public Optional<PaymentResult> parseWebhookEvent(WebhookRequest request) {
        try {
            System.out.println("=====================>");
            var event=  Webhook.constructEvent(request.getPayload(), request.getHeaders().get("Stripe-Signature"),stripeWebhookSecretKey);
            System.out.println(event.getType()+"-------------->");
            return switch (event.getType()){
                case "payment_intent.succeeded"->
                        Optional.of(new PaymentResult(extractOrderId(event),PaymentStatus.PAID));

                case "payment_intent.payment_failed"-> Optional.of(new PaymentResult(extractOrderId(event),PaymentStatus.FAILED));

                default  ->Optional.empty();

            };

        } catch (SignatureVerificationException e) {
         throw  new PaymentException("Invalid signature");
        }

    }

    private Long  extractOrderId(Event event ){
        var stripeObject= event.getDataObjectDeserializer().getObject().orElseThrow(()->new PaymentException("Could not deserialize Stripe event.Check the SDK and api version"));

        var paymentIntent= (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }


    private  SessionCreateParams.LineItem createLineItem(OrderItem orderItem) {
        var lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity((long) orderItem.getQuantity())
                .setPriceData(
                        createPriceData(orderItem)
                )
                .build();
        return lineItem;
    }

    private  SessionCreateParams.LineItem.PriceData createPriceData(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("inr")
                .setUnitAmount(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(100)).longValue())
                .setProductData(createProductData(orderItem))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(orderItem.getProduct().getName())
                .build();
    }



}
