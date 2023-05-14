package com.example.music_player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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


public class HomeFragment extends Fragment {

    RecyclerView glideLayoutRecyclerView;

    List<Song> songs;
    private static String JSON_URL = "https://run.mocky.io/v3/f42fde67-c25e-45b8-9672-c5da06e874d1";
    //    Adapter adapter;
    GridLayoutAdapter adapter;

    Toolbar homeToolbar;
    LinearLayout honey_singh_playlist;
    LinearLayout arijit_singh_playlist;
    LinearLayout jubin_nautiyal_playlist;
    LinearLayout neha_kakkar_playlist;
    LinearLayout badshah_playlist;
    LinearLayout shreya_ghoshal_playlist;
    LinearLayout guru_randhawa_playlist;
    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeToolbar = view.findViewById(R.id.home_toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(homeToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        honey_singh_playlist = view.findViewById(R.id.honey_singh_playlist);

        honey_singh_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent honeyPlaylist = new Intent(getActivity(),honey_singh_songs_playlistActivity.class);
                startActivity(honeyPlaylist);
            }
        });

        arijit_singh_playlist = view.findViewById(R.id.arijit_singh_playlist);

        arijit_singh_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent arijitPlaylist = new Intent(getActivity(),arijit_singh_songs_playlistActivity.class);
                startActivity(arijitPlaylist);
            }
        });

        jubin_nautiyal_playlist = view.findViewById(R.id.jubin_nautiyal_song);

        jubin_nautiyal_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jubinPlaylist = new Intent(getActivity(),jubin_nautiyal_song_playlistActivity.class);
                startActivity(jubinPlaylist);
            }
        });

        neha_kakkar_playlist = view.findViewById(R.id.neha_kakkar_song);

        neha_kakkar_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nehaPlaylist = new Intent(getActivity(),neha_kakkar_song_playlistActivity.class);
                startActivity(nehaPlaylist);
            }
        });

        badshah_playlist = view.findViewById(R.id.badshah_songs);

        badshah_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent badshahPlaylist = new Intent(getActivity(),badshah_song_playlistActivity.class);
                startActivity(badshahPlaylist);
            }
        });

        shreya_ghoshal_playlist = view.findViewById(R.id.shreya_ghoshal_song);

        shreya_ghoshal_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shreyaPlaylist = new Intent(getActivity(),shreya_ghosal_song_playlistActivity.class);
                startActivity(shreyaPlaylist);
            }
        });

        guru_randhawa_playlist = view.findViewById(R.id.guru_randhawa_song);

        guru_randhawa_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guruPlaylist = new Intent(getActivity(),guru_randhawa_song_playlistActivity.class);
                startActivity(guruPlaylist);
            }
        });


        glideLayoutRecyclerView = view.findViewById(R.id.glideLayoutRecyclerView);
        songs = new ArrayList<>();

        extractSongs();


        return view;
    }

    private void extractSongs() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
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

                adapter = new GridLayoutAdapter(getContext(),songs);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
                glideLayoutRecyclerView.setLayoutManager(gridLayoutManager);
                glideLayoutRecyclerView.setAdapter(adapter);
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