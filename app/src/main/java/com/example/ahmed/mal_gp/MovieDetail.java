package com.example.ahmed.mal_gp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.mal_gp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    RequestQueue requestQueue;
    MovieData movieData ;


    ImageView poster,interested;
    ImageView backdrop;
    TextView title,rate,summary,date,review;
    int id;

    RecyclerView recyclerView;

    int movie_id;
    String poster_image;
    String movie_name;

    ArrayList<VideoData> datas;
    String url = "http://api.themoviedb.org/3/movie/";




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());

        if(getArguments() != null){
            id=getArguments().getInt("id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_movie_detail, container, false);

        poster= (ImageView) view.findViewById(R.id.poster_image);
        backdrop = (ImageView) view.findViewById(R.id.smallimage);
        title = (TextView) view.findViewById(R.id.title);
        rate = (TextView) view.findViewById(R.id.rate);
        summary = (TextView) view.findViewById(R.id.summary);
        date= (TextView) view.findViewById(R.id.date);


         recyclerView  = (RecyclerView) view.findViewById(R.id.list_video);
        review = (TextView) view.findViewById(R.id.review);

        interested = (ImageView) view.findViewById(R.id.interested);
        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(interested.getDrawable()==getActivity().getResources().getDrawable(android.R.drawable.btn_star_big_off)) {
                    interested.setImageResource(android.R.drawable.btn_star_big_on);
                }
                else
                    interested.setImageResource(android.R.drawable.btn_star_big_off);*/
                if(new MovieDatabase(getActivity()).checkIfExist(id)){
                    new MovieDatabase(getActivity()).deleteRowById(id);
                    interested.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(getActivity(),"This Movie Not Favorite Any More",Toast.LENGTH_LONG).show();
                }

                else {
                    interested.setImageResource(android.R.drawable.btn_star_big_on);

                    MovieDatabase database = new MovieDatabase(getActivity());
                    database.insertMovie(movie_id, poster_image, movie_name);
                }

            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sendjisonRequist(Constants.movieuri + id + "?api_key=392302ba16f8654836907c24c55d8fb1");

        ArrayList<MovieData> data=new ArrayList<>();
        data=new MovieDatabase(getActivity()).retrieveFavoriteMovies();
        Log.d("movies =", data.toString());
        for(int i=0 ; i<data.size() ; i++){
            if(data.get(i).getId() == id){
                interested.setImageResource(android.R.drawable.btn_star_big_on);
            }
        }

        sendJsonRequestForTrailer(url + id + "/videos?api_key=" + Constants.key);
        sendJsonRequestForReview(url + id+ "/reviews?api_key="+ Constants.key);


    }

    private void sendjisonRequist(String url) {
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                movieData = Response(jsonObject);
                title.setText(movieData.getTitle());
                rate.setText(movieData.getRate()+"");
                date.setText(movieData.getDate());
                summary.setText(movieData.getSummary());
                Picasso.with(getActivity()).load(Uri.parse(Constants.baseimageuri+movieData.getImageurl())).into(backdrop);
                Picasso.with(getActivity()).load(Uri.parse(Constants.baseimageuri+movieData.getBackdrop_imageurl())).into(poster);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

        });
        requestQueue.add(request);
    }

    public MovieData Response(JSONObject jsonObject) {


        if (jsonObject != null && jsonObject.length() != 0) {

            try {

                 movie_id = jsonObject.getInt(Constants.id);
                 poster_image = jsonObject.getString(Constants.poster_url);
                 movie_name = jsonObject.getString(Constants.title);
                String backdrop_image = jsonObject.getString(Constants.backdrop_url);
                String summary = jsonObject.getString(Constants.summary);
                Double rate = jsonObject.getDouble(Constants.rate);
                String date= jsonObject.getString(Constants.date);

               // Log.d("summary",summary);


                movieData= new MovieData(id, poster_image, movie_name);
                movieData.setBackdrop_imageurl(backdrop_image);
                movieData.setRate(rate);
                movieData.setSummary(summary);
                movieData.setDate(date);
                return movieData;

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
            return null;
    }




    private void sendJsonRequestForTrailer(String req){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET ,req,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                datas = trailerReponse(jsonObject);
                if (datas != null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    VideoAdapter adapter =new VideoAdapter(getActivity() , datas);
                    recyclerView.setAdapter(adapter);

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(request);
    }

    public ArrayList<VideoData> trailerReponse(JSONObject jsonObject) {

        ArrayList<VideoData> arrayList = new ArrayList<>();
        if (jsonObject == null || jsonObject.length() == 0) {
            return null;
        } else {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(Constants.results);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String Key = object.getString(Constants.Video_Key);
                    String Name = object.getString(Constants.Video_Name);

                    arrayList.add(new VideoData(Key , Name));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return arrayList;
    }



    private void sendJsonRequestForReview(String req){
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET ,req,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                if (jsonObject != null){
                    readReview(jsonObject);

                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(request);
    }

    public void readReview(JSONObject jsonObject){

        if (jsonObject == null || jsonObject.length() == 0) {
            return ;
        } else {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(Constants.results);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    review.append(jsonObject1.getString("author"));
                    review.append(jsonObject1.getString("content"));
                    review.append("\n");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}


