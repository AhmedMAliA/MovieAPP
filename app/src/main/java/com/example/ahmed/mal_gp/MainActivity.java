package com.example.ahmed.mal_gp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClick {

    boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(findViewById(R.id.movie_detail_container)!=null) {
            state = true;
        }else{
            MainActivityFragment movie =new MainActivityFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,movie).commit();
        }


       /* ImageView imageView= (ImageView) findViewById(R.id.test);
        Picasso.with(MainActivity.this).load("http://image.tmdb.org/t/p/w342/w93GAiq860UjmgR6tU9h2T24vaV.jpg").into(imageView);*/


    }

    @Override
    public void onMovieItemClicked(int id) {
        Bundle bundle=new Bundle();
        bundle.putInt("id",id);
        if(state){
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container,movieDetail).commit();
        }
        else{
            Intent i = new Intent(this,DetailActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        }


    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
