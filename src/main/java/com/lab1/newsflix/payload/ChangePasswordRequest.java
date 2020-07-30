package com.lab1.newsflix.payload;

public class ChangePasswordRequest {

    private Long userId;

    private String password;

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
