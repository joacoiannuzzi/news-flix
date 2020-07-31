package com.lab1.newsflix.controller;

import com.lab1.newsflix.exception.ResourceNotFoundException;
import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.PaymentResponse;
import com.lab1.newsflix.payload.SubRequest;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/subscription")
    public String subscriptionPage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "subscription";
    }


    @PostMapping("/create-subscription")
    public @ResponseBody
    PaymentResponse createSubscription(@RequestBody SubRequest subRequest) {

        if (subRequest.getTokenId() == null || subRequest.getPlanId().isEmpty()) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please try again later.");
        }

        User user = userRepository.findById(subRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", subRequest.getUserId()));

        String customerId = stripeService.createCustomer(user.getEmail(), subRequest.getTokenId());

        if (customerId == null) {
            return new PaymentResponse(false, "An error accurred while trying to create customer");
        }

        String subscriptionId = stripeService.createSubscription(customerId, subRequest.getPlanId());

        if (subscriptionId == null) {
            return new PaymentResponse(false, "An error accurred while trying to create subscription");
        }

        return new PaymentResponse(true, "Success! your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription/{userId}")
    public @ResponseBody PaymentResponse cancelSubscription(@PathVariable Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        boolean subscriptionStatus = stripeService.cancelSubscription(user.getSubscriptionID());

        if (!subscriptionStatus) {
            return new PaymentResponse(false, "Faild to cancel subscription. Please try again later");
        }

        return new PaymentResponse(true, "Subscription cancelled successfully");
    }



}
