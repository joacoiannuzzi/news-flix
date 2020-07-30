package com.lab1.newsflix.controller;

import com.lab1.newsflix.model.User;
import com.lab1.newsflix.payload.PaymentRequest;
import com.lab1.newsflix.payload.PaymentResponse;
import com.lab1.newsflix.repository.PaymentRepository;
import com.lab1.newsflix.repository.UserRepository;
import com.lab1.newsflix.service.StripeService;
import com.stripe.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/payment")
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
    PaymentResponse createSubscription(PaymentRequest paymentRequest) {

        //validate data
        if (paymentRequest.getTokenId() == null || paymentRequest.getPlan().getActive()) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please, try again later.");
        }


        try {
           User user = userRepository.getOne(paymentRequest.getUserId());
            //create subscription
            String subscriptionId = stripeService.createSubscription(paymentRequest).getId();
            if (subscriptionId == null) {
                return new PaymentResponse(false, "An error occurred while trying to create a subscription.");
            }

            user.setIsActive(true);
            userRepository.save(user);

            return new PaymentResponse(true, "Success! Your subscription id is " + subscriptionId);

        }catch (Exception e){
            return new PaymentResponse(false, "An error occurred while getting user data");
        }







    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody
    PaymentResponse cancelSubscription(String subscriptionId, Long userID) {

        boolean status = stripeService.cancelSubscription(subscriptionId, userID);
        if (!status) {
            return new PaymentResponse(false, "Failed to cancel the subscription. Please, try later.");
        }

        User user = userRepository.getOne(userID);
        user.setIsActive(false);
        userRepository.save(user);
        return new PaymentResponse(true, "Subscription cancelled successfully for user:"+user.getId());
    }


    @PostMapping("/create-charge")
    public @ResponseBody
    PaymentResponse createCharge(PaymentRequest paymentRequest) {
        //validate data
        if (paymentRequest.getTokenId() == null) {
            return new PaymentResponse(false, "Stripe payment token is missing. Please, try again later.");
        }

        //create charge

        User user = userRepository.getOne(paymentRequest.getUserId());
        paymentRequest.setAmount(999); //49.99usd
        String chargeId = stripeService.createCharge(paymentRequest).getId();
        if (chargeId == null) {
            return new PaymentResponse(false, "An error occurred while trying to create a charge.");
        }

        user.setIsActive(true);
        userRepository.save(user);

        // You may want to store charge id along with order information

        return new PaymentResponse(true, "Success! Your charge id is " + chargeId);
    }
}
