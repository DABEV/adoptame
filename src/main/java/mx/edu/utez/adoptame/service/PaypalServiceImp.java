package mx.edu.utez.adoptame.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.adoptame.config.PaypalPaymentIntent;
import mx.edu.utez.adoptame.config.PaypalPaymentMethod;

@Service
public class PaypalServiceImp implements PaypalService {

    private Payment payment = null; 

    @Autowired
    private APIContext apiContext;

    @Override
    public Payment creaPago(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl) {
        try {
        
            Amount amount = new Amount();
            amount.setCurrency(currency);
            total = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
            amount.setTotal(String.format("%.2f", total));

            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod(method.toString().toLowerCase());

            payment = new Payment();
            payment.setIntent(intent.toString().toLowerCase());
            payment.setPayer(payer);
            payment.setTransactions(transactions);

            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);

            payment = payment.create(apiContext);
        
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            payment = null;
        }

        return payment;
    }

    @Override
    public Payment ejecutaPago(String paymentId, String payerId) {
        
        try {
            payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
        
            payment = payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            payment = null;
        }

        return payment;
    }
    
}
