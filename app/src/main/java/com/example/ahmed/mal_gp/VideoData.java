package com.example.ahmed.mal_gp;

/**
 * Created by Ahmed on 4/3/2016.
 */
public class VideoData {

    private String Key;
    private String Name;

    public VideoData(String key , String name){
        this.Key = key;
        this.Name = name;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
