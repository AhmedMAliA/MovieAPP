package com.example.ahmed.mal_gp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Ahmed on 4/3/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {

    ArrayList<VideoData> videoDataArrayList;
    Context context;
    LayoutInflater layoutInflater;
    public VideoAdapter(Context context , ArrayList<VideoData> data){
        this.context = context;
        videoDataArrayList = data;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public VideoAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.trailer , parent ,false);
        viewHolder holder =new viewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.viewHolder holder, int position) {

        holder.trailer_name.setText(videoDataArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return videoDataArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView trailer_name;
        public viewHolder(final View itemView) {
            super(itemView);
            trailer_name = (TextView) itemView.findViewById(R.id.trailer_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, PlayTrailer.class);
                    i.putExtra("key",videoDataArrayList.get(getPosition()).getKey());
                    context.startActivity(i);
                }
            });
        }



    }
}
