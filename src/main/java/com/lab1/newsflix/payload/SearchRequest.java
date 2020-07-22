package com.lab1.newsflix.payload;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class SearchRequest {

    @NotBlank
    private Date dateFrom;

    @NotBlank
    private Date dateTo;

    @NotBlank
    private String category;

    @NotBlank
    private String newspaper;

    @NotBlank
    private String query;

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

}
