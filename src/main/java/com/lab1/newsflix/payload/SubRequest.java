package com.lab1.newsflix.payload;

import org.springframework.web.bind.annotation.RequestParam;

public class SubRequest {
    private String email;
    private String token;
    private String plan;
    private String coupon;

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getPlan() {
        return plan;
    }

    public String getCoupon() {
        return coupon;
    }
}
