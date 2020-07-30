package com.lab1.newsflix.controller;

import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.PaymentResponse;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.service.StripeService;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PaymentController {


    private String API_PUBLIC_KEY = "pk_test_51HALsjEoLsnpTFC2ZgjgHXNbGo50Y3399ybtTZNg1mYTlnu6QTUUEkuyoNlE2GTxCJbwOq5N4tvYRmc6IvHUMH5K00z4T8BqLU";

    @Autowired
    private StripeService stripeService;

    @Autowired
    UserRepository userRepository;



    @GetMapping("/subscription")
    public String subscriptionPage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "subscription";
    }

    @GetMapping("/charge")
    public String chargePage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "charge";
    }

    /*========== REST APIs for Handling Payment ===================*/

    @PostMapping("/create-subscription")
    public @ResponseBody
    PaymentResponse createSubscription(User user, String token, String plan) {
        //validate data
        if (token == null || plan.isEmpty()) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please, try again later.");
        }


        if (user == null) {
            return new PaymentResponse(false, "An error occurred while getting user data");
        }

        //create subscription
        String subscriptionId = stripeService.createSubscription(user, plan);
        if (subscriptionId == null) {
            return new PaymentResponse(false, "An error occurred while trying to create a subscription.");
        }

        // Ideally you should store customerId and subscriptionId along with customer object here.
        // These values are required to update or cancel the subscription at later stage.


        user.setIsActive(true);
        userRepository.save(user);
        return new PaymentResponse(true, "Success! Your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody
    PaymentResponse cancelSubscription(User user,String subscriptionId) {
        boolean status = stripeService.cancelSubscription(subscriptionId);
        if (!status) {
            return new PaymentResponse(false, "Failed to cancel the subscription. Please, try later.");
        }
        user.setIsActive(false);
        userRepository.save(user);
        return new PaymentResponse(true, "Subscription cancelled successfully for user:"+user.getId());
    }


    @PostMapping("/create-charge")
    public @ResponseBody
    PaymentResponse createCharge(User user, String token) {
        //validate data
        if (token == null) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please, try again later.");
        }

        //create charge
        String chargeId = stripeService.createCharge(user, token, 999); //$9.99 USD
        if (chargeId == null) {
            return new PaymentResponse(false, "An error occurred while trying to create a charge.");
        }

        user.setIsActive(true);
        userRepository.save(user);

        // You may want to store charge id along with order information

        return new PaymentResponse(true, "Success! Your charge id is " + chargeId);
    }
}
