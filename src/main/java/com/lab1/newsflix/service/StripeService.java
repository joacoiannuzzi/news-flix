package com.lab1.newsflix.service;

import com.lab1.newsflix.model.Payment;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.PaymentRequest;
import com.lab1.newsflix.repository.PaymentRepository;
import com.lab1.newsflix.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private String API_SECRET_KEY = "sk_test_51HALsjEoLsnpTFC2cyk79Sy7YY49Sr7TYMcdADNEprX9P16L6f8VHexNbv8lPYa5MpHqjcleBmDCxU1xluKzHwea00L3BgcxW1";

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    public Charge createCharge(PaymentRequest paymentRequest) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", paymentRequest.getAmount());
            chargeParams.put("currency", "usd");
            chargeParams.put("description", "Charge for " + paymentRequest.getUserId());
            chargeParams.put("source", paymentRequest.getTokenId()); // ^ obtained with Stripe.js

            //create a charge
            Charge charge = Charge.create(chargeParams);
            id = charge.getId();

            paymentRepository.save(new Payment(userRepository.getOne(paymentRequest.getUserId()),paymentRequest.getTokenId(),paymentRequest.getAmount(),id,paymentRequest.getPlan().toString()));
            return charge;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public Subscription createSubscription(PaymentRequest paymentRequest) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> item = new HashMap<>();
            item.put("plan", paymentRequest.getPlan());  //Plan "1 month"

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", paymentRequest.getUserId());
            params.put("items", items);

            Subscription sub = Subscription.create(params);
            id = sub.getId();

            paymentRepository.save(new Payment(userRepository.getOne(paymentRequest.getUserId()),paymentRequest.getPlan().toString(),id));
            return sub;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public boolean cancelSubscription(String subscriptionId, Long userID) {
        boolean status;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Subscription sub = Subscription.retrieve(subscriptionId);
            sub.cancel();
            User user = userRepository.getOne(userID);
            user.setIsActive(false);
            userRepository.save(user);
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            status = false;
        }
        return status;
    }



}