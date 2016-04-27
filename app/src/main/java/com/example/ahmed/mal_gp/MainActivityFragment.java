package com.example.ahmed.mal_gp;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.zip.Inflater;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ArrayList<MovieData> movieData = new ArrayList<>();
    MovieAdapter adapter ;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getActivity());
       setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.myrecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        Toast.makeText(getActivity() , "in main Activity" , Toast.LENGTH_LONG).show();
        return  v;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getActivity().getPreferences(getContext().MODE_PRIVATE);
        String menuitem = pref.getString("shared","popular");
        if(menuitem.equals("popular")){
            sendjisonRequist(Constants.PopularityURL);
        }
        else if(menuitem.equals("mostrate")){
            sendjisonRequist(Constants.MostRateURL);
        }
        else if(menuitem.equals("favorite")){
            movieData = new MovieDatabase(getActivity()).retrieveFavoriteMovies();
            adapter=new MovieAdapter(getActivity(),movieData);
            recyclerView.setAdapter(adapter);
        }

    }

    private void sendjisonRequist (String url){
        movieData = new ArrayList<>();
        Log.d("url" , url);
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("json" , jsonObject.toString());
                movieData=Response(jsonObject);
                if(movieData !=null ){
                    if(getActivity() !=null){

                        adapter=new MovieAdapter(getActivity(),movieData);
                        recyclerView.setAdapter(adapter);
                    }
                }

            }},new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }

        });
        requestQueue.add(request);
    }


    public ArrayList<MovieData> Response (JSONObject jsonObject){


        if(jsonObject != null && jsonObject.length() != 0){

            try {

                JSONArray movies = jsonObject.getJSONArray(Constants.results);
                for(int i=0 ; i<movies.length() ; i++){
                    JSONObject object = movies.getJSONObject(i);
                    int id = object.getInt(Constants.id);
                    String poster_image = object.getString(Constants.poster_url);
                    String movie_name = object.getString(Constants.title);
                    //String backdrop_image = object.getString(Constants.backdrop_url);
                    //String summary = object.getString(Constants.summary);
                    //Double rate = object.getDouble(Constants.rate);

                    movieData.add(new MovieData(id,poster_image,movie_name));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieData;

        }
        else
            return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor=getActivity().getPreferences(getContext().MODE_PRIVATE).edit();
        int itemId = item.getItemId();
        if( itemId== R.id.pop){
            sendjisonRequist(Constants.PopularityURL);
            editor.putString("shared", "popular");
            editor.commit();
        }
        else if (itemId==R.id.mostrate){
            sendjisonRequist(Constants.MostRateURL);
            editor.putString("shared", "mostrate");
            editor.commit();
        }
        else if(itemId ==R.id.favorite){
            movieData = new MovieDatabase(getActivity()).retrieveFavoriteMovies();
            adapter=new MovieAdapter(getActivity(),movieData);
            recyclerView.setAdapter(adapter);
            editor.putString("shared", "favorite");
            editor.commit();
        }
        return super.onOptionsItemSelected(item);

    }
}
