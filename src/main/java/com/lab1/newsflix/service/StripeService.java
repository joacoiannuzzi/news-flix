package com.lab1.newsflix.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.lab1.newsflix.exception.ResourceNotFoundException;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;

@Service
public class StripeService {

    @Value("${stripe.key.secret}")
    private String API_SECRET_KEY;

    @Autowired
    UserRepository userRepository;

    public StripeService() {

    }

    public String createCustomer(User user, String token) {

        String id = user.getCustomerID();
        if (id == null || id.isEmpty()) {


            try {
                Stripe.apiKey = API_SECRET_KEY;
                Map<String, Object> customerParams = new HashMap<>();
                customerParams.put("description", "Customer for " + user.getEmail());
                customerParams.put("email", user.getEmail());
                // obtained with stripe.js
                customerParams.put("source", token);

                Customer customer = Customer.create(customerParams);
                id = customer.getId();


                user.setCustomerID(id);
                userRepository.save(user);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return id;
        }
        return user.getCustomerID(); //No need to create new customerID if it already has.

    }

    public String createSubscription(String customerId, String plan) {

        String subscriptionId = null;

        try {
            Stripe.apiKey = API_SECRET_KEY;

            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);


            Subscription subscription = Subscription.create(params);

            subscriptionId = subscription.getId();

            User user = userRepository.findByCustomerID(customerId).orElseThrow(() -> new ResourceNotFoundException("User", "customerID", customerId));
            user.setIsActive(true);
            user.setSubscriptionID(subscriptionId);
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriptionId;
    }

    public boolean cancelSubscription(User user) {

        boolean subscriptionStatus;

        try {

            Subscription subscription = Subscription.retrieve(user.getSubscriptionID());
            subscription.cancel();
            user.setIsActive(false);
            userRepository.save(user);
            subscriptionStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            subscriptionStatus = false;
        }
        return subscriptionStatus;
    }



}
