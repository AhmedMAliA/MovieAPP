package com.example.ahmed.mal_gp;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Ahmed on 4/2/2016.
 */
public class MovieDatabase {

    Realm realm;
    MovieData movieData;
    RealmResults<MovieData> results;
    Context context;

    public MovieDatabase(Context context){
        realm= Realm.getInstance(context);
        this.context = context;
    }

    public MovieDatabase() {

    }


    public void insertMovie(int id , String poster , String name){
        realm.beginTransaction();
        MovieData data = new MovieData(id , poster , name );
        realm.copyToRealm(data);
        realm.commitTransaction();

    }

    public ArrayList<MovieData> retrieveFavoriteMovies() {
        if(realm==null)
        {
            realm = Realm.getInstance(context);
        }
        ArrayList<MovieData> movieDataArrayList = new ArrayList<>();

        results = realm.where(MovieData.class).findAll();
        for (int i = 0; i<results.size() ; i++){

            movieDataArrayList.add(results.get(i));
        }
        return movieDataArrayList;
    }

    public boolean deleteRowById(int id) {

        results = realm.where(MovieData.class).equalTo("id", id).findAll();
        if (results.size() != 0) {
            realm.beginTransaction();
            //results.remove(0);
            movieData = results.get(0);
            movieData.removeFromRealm();
            results.clear();
            realm.commitTransaction();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfExist(int id) {

        results = realm.where(MovieData.class).equalTo("id", id).findAll();
        if (results.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

}



