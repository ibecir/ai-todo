package ba.edu.ibu.aitodo.api.impl.paypal;

import ba.edu.ibu.aitodo.core.api.paymentprocessor.PaymentProcessor;

public class PaypalPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(float amount) {
        // Connect to the PayPal API and perform a payment
        System.out.println("Paid with the PayPal API.");
    }
}
