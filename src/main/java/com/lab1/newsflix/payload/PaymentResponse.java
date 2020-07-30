package com.lab1.newsflix.payload;

public class PaymentResponse {

    private boolean status;
    private String details;

    public PaymentResponse() {
        this.status = true; //default to true
    }

    public PaymentResponse(boolean status) {
        this.status = status;
    }

    public PaymentResponse(boolean status, String details) {
        this.status = status;
        this.details = details;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", details='" + details + '\'' +
                '}';
    }
}
