package com.example.ahmed.mal_gp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed on 3/24/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    ArrayList<MovieData> movieData;
    public LayoutInflater layoutInflater;
    ItemClick itemClick ;

    public MovieAdapter(Context context,ArrayList<MovieData> movieData){
        this.context=context;
        this.movieData=movieData;
        layoutInflater=LayoutInflater.from(context);
        itemClick= (ItemClick) context;
    }


    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(layoutInflater.inflate(R.layout.moviedesign,parent,false));
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        holder.title.setText(movieData.get(position).getTitle());
        Log.d("title" , movieData.get(position).getTitle());
        Picasso.with(context).load(Uri.parse(Constants.baseimageuri+movieData.get(position).getImageurl())).into(holder.poster);
        Log.d("ahmed",movieData.get(position).getImageurl()+Constants.baseimageuri);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView poster;
        TextView title;
        public ViewHolder(final View itemView) {
            super(itemView);
            poster= (ImageView) itemView.findViewById(R.id.movieimage);
            title= (TextView) itemView.findViewById(R.id.movieName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent i = new Intent(context,DetailActivity.class);
                    i.putExtra("id",movieData.get(getPosition()).getId());
                    context.startActivity(i);*/
                    itemClick.onMovieItemClicked(movieData.get(getPosition()).getId());

                }
            });

        }
    }


    public interface ItemClick {

        void onMovieItemClicked(int id);

    }


}
