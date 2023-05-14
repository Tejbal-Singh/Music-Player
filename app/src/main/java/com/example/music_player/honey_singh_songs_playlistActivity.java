package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class honey_singh_songs_playlistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Song> songs;
    private static String JSON_URLHONEY = "https://run.mocky.io/v3/9fb446f7-b8d2-44c2-aa43-224107065abd";
    //    Adapter adapter;
    arijit_singhAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honey_singh_songs_playlist);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        }


        recyclerView =(RecyclerView) findViewById(R.id.honey_singh_songsPlaylist);
        songs = new ArrayList<>();

        extractSongs();

    }

    private void extractSongs() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URLHONEY, null, new Response.Listener<JSONArray>() {
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