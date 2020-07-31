package com.lab1.newsflix.payload;

import org.springframework.web.bind.annotation.RequestParam;

public class SubRequest {
    private Long id;
    private String token;
    private String plan;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getPlan() {
        return plan;
    }

}
