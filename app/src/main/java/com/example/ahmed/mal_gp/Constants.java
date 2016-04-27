package com.example.ahmed.mal_gp;

/**
 * Created by Ahmed on 3/24/2016.
 */
public class Constants {

    static String key ="392302ba16f8654836907c24c55d8fb1";
    static String Api_key = "http://api.themoviedb.org/3/discover/movie?api_key=";
    static String Popularity ="&sort_by=popularity.desc";
    static String mostRate = "&sort_by=vote_average.desc";


    public static final String baseimageuri ="http://image.tmdb.org/t/p/w342";
    public static final String movieuri ="http://api.themoviedb.org/3/movie/";

    public static final String PopularityURL = Api_key+key+Popularity;
    public static final String MostRateURL = Api_key+key+mostRate;
    public static final String results = "results";
    public static final String summary = "overview";
    public static final String id = "id";
    public static final String title = "title";
    public static final String date = "release_date";
    public static final String backdrop_url = "backdrop_path";
    public static final String poster_url = "poster_path";
    public static final String rate = "popularity";

    public static final String Video_Key = "key";
    public static final String Video_Name = "name";

    public static final String YOUTUBE_API_KEY = "AIzaSyDMqZMx0UhO1za2xHDFn5KfBo_Wtlv5tKQ";







}
