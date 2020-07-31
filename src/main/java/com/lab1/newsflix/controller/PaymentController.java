package com.lab1.newsflix.controller;

import com.lab1.newsflix.payload.PaymentResponse;
import com.lab1.newsflix.payload.SubRequest;
import com.lab1.newsflix.service.StripeService;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    @Autowired
    private StripeService stripeService;


    @GetMapping("/subscription")
    public String subscriptionPage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "subscription";
    }


    @PostMapping("/create-subscription")
    public @ResponseBody
    PaymentResponse createSubscription(@RequestBody SubRequest subRequest) {

        if (subRequest.getToken() == null || subRequest.getPlan().isEmpty()) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please try again later.");
        }

        String customerId = stripeService.createCustomer(subRequest.getEmail(), subRequest.getToken());

        if (customerId == null) {
            return new PaymentResponse(false, "An error accurred while trying to create customer");
        }

        String subscriptionId = stripeService.createSubscription(customerId, subRequest.getPlan(), subRequest.getCoupon());

        if (subscriptionId == null) {
            return new PaymentResponse(false, "An error accurred while trying to create subscription");
        }



        return new PaymentResponse(true, "Success! your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody PaymentResponse cancelSubscription(String subscriptionId) {

        boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);

        if (!subscriptionStatus) {
            return new PaymentResponse(false, "Faild to cancel subscription. Please try again later");
        }

        return new PaymentResponse(true, "Subscription cancelled successfully");
    }

    @PostMapping("/coupon-validator")
    public @ResponseBody
    PaymentResponse couponValidator(@Valid @RequestBody String coup) {

        Coupon coupon = stripeService.retriveCoupon(coup);

        if (coupon != null && coupon.getValid()) {
            String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
                    : coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
            return new PaymentResponse(true, details);
        }
        return new PaymentResponse(false, "This coupon code is not available. This may be because it has expired or has "
                + "already been applied to your account.");
    }


}
