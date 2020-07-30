package com.lab1.newsflix.service;

import com.lab1.newsflix.model.Payment;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.PaymentRepository;
import com.lab1.newsflix.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private String API_SECRET_KEY = "sk_test_51HALsjEoLsnpTFC2cyk79Sy7YY49Sr7TYMcdADNEprX9P16L6f8VHexNbv8lPYa5MpHqjcleBmDCxU1xluKzHwea00L3BgcxW1";

    @Autowired
    PaymentRepository paymentRepository;

    public String createCharge(User user, String token, int amount) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Charge for " + user.getId());
            chargeParams.put("source", token); // ^ obtained with Stripe.js

            //create a charge
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();

            paymentRepository.save(new Payment(user, token, amount));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public String createSubscription(User user, String plan) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);


            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", user.getId());
            params.put("items", items);

            Subscription sub = Subscription.create(params);
            id = sub.getId();

            paymentRepository.save(new Payment(user, plan));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public boolean cancelSubscription(String subscriptionId) {
        boolean status;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Subscription sub = Subscription.retrieve(subscriptionId);
            sub.cancel();
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            status = false;
        }
        return status;
    }


}