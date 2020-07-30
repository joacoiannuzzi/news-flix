package com.lab1.newsflix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lab1.newsflix.payload.PaymentResponse;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    private String token;

    private int amount;

    private Date date;

    private String plan;

    private String subscriptionId;

    private String chargeId;


    public Payment(User user, String token, int amount,String chargeId,String plan){
        this.user=user;
        this.token=token;
        this.amount=amount;
        this.plan = plan;
        this.date=new Date();
    }

    public Payment(User user, String plan,String subscriptionId){
        this.user=user;
        this.plan=plan;
        this.subscriptionId=subscriptionId;
        this.date=new Date();
    }

    public String getPlan() {
        return plan;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public Payment(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }
}
