package com.lab1.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE")
public class Favorite {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column (name="userID")
    private long userid;

    @Column (name="ArticleID")
    private String url; //Usamos url que es el key de Article


    public Long getId() {
        return id;
    }

    public void setUserid(long userid){
        this.userid=userid;}

    public long getUserid(){return userid;}

    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl(){return url;}
}
