package com.example.ahmed.mal_gp;

import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Ahmed on 4/2/2016.
 */
public class TestMe extends InstrumentationTestCase {

    public void testSize() throws Exception {

        ArrayList<MovieData> data=new ArrayList<>();
        data = new MovieDatabase(getInstrumentation().getTargetContext().getApplicationContext()).retrieveFavoriteMovies();
        assertEquals("FavoriteMovies Not Equals 7",data.size(),7);

    }
}
