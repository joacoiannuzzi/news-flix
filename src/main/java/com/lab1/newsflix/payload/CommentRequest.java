package com.lab1.newsflix.payload;

import javax.validation.constraints.Size;

public class CommentRequest {

    private Long userId;

    private Long articleId;

    @Size(min = 1, max = 200)
    private String body;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
