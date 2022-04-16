package mx.edu.utez.adoptame.service;

import com.paypal.api.payments.Payment;

import mx.edu.utez.adoptame.config.PaypalPaymentIntent;
import mx.edu.utez.adoptame.config.PaypalPaymentMethod;

public interface PaypalService {
    public Payment creaPago(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl);
    public Payment ejecutaPago (String paymentId, String payerId);
}
