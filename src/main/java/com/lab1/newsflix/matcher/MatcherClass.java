package com.lab1.newsflix.matcher;

public class MatcherClass {
    private double Score;
    private String url1;
    private String url2;

    public MatcherClass(String url1, String url2, double Score){
        this.Score=Score;
        this.url1=url1;
        this.url2=url2;
    }

    public double getScore() {
        return Score;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }
}
