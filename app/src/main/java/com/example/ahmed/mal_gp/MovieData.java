package com.example.ahmed.mal_gp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ahmed on 3/24/2016.
 */
public class MovieData extends RealmObject {

    @PrimaryKey
    private int id;
     private String imageurl;
     private String backdrop_imageurl;
    private String title;
    private  Double rate;
    private String date;
    private String summary;

    public  MovieData(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieData(int id,String imageurl , String title){
        this.id=id;
        this.imageurl=imageurl;
        this.title=title;

    }

    public void setBackdrop_imageurl(String backdrop_imageurl) {
        this.backdrop_imageurl = backdrop_imageurl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



    public String getImageurl() {
        return imageurl;
    }

    public String getBackdrop_imageurl() {
        return backdrop_imageurl;
    }

    public String getTitle() {
        return title;
    }

    public Double getRate() {
        return rate;
    }

    public String getSummary() {
        return summary;
    }

    public int getId() {
        return id;
    }
}
