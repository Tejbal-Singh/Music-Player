package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class guru_randhawa_song_playlistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Song> songs;
    private static String JSON_URL = "https://run.mocky.io/v3/610dff42-6da6-493b-beee-6ba6c90a3c5c";
    //    Adapter adapter;
    arijit_singhAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_randhawa_song_playlist);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        }

        recyclerView = findViewById(R.id.guru_randhawa_songs_playlist);
        songs = new ArrayList<>();

        extractSongs();
    }

    private void extractSongs() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject songObject = response.getJSONObject(i);

                        Song song = new Song();
                        song.setTitle(songObject.getString("song").toString());
                        song.setArtist(songObject.getString("artists").toString());
                        song.setCoverImage(songObject.getString("cover_image"));
                        song.setSongURL(songObject.getString("url"));

                        songs.add(song);


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new arijit_singhAdapter(getApplicationContext(),songs);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorResponse" + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

}