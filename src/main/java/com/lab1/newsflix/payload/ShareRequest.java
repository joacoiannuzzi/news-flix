package com.lab1.newsflix.payload;

import javax.validation.constraints.NotNull;

public class ShareRequest {

    @NotNull
    private Long articleId1;
    private Long articleId2;



    public Long getArticleId1() {
        return articleId1;
    }

    public Long getArticleId2() {
        return articleId2;
    }
}
