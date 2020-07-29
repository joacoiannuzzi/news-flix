package com.lab1.newsflix.payload;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class SearchRequest {

    private Date dateFrom;

    private Date dateTo;

    private String category;

    private String newspaper;

    private String query;

    private boolean moreFavorited;

    private boolean moreShared;

    public SearchRequest(Date dateFrom, Date dateTo, String newspaper, String category, String query) {
        this.category = category;
        this.newspaper = newspaper;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.query = query;
    }

    public Date getDateFrom() {
        return dateFrom;
    }


    public Date getDateTo() {
        return dateTo;
    }


    public String getCategory() {
        return category;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public String getQuery() {
        return query;
    }

    public boolean isMoreFavorited() {
        return moreFavorited;
    }

    public boolean isMoreShared() {
        return moreShared;
    }
}
