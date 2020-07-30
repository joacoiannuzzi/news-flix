package com.lab1.newsflix.payload;


public class PaymentRequest {

    private Long userId;

    private String tokenId;

    private int amount;

    private String plan;


    public PaymentRequest(){

    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getAmount(){return amount;}

    public void setAmount(int amount){this.amount=amount;}


}
