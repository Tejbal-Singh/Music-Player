package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class song_playing_Activity extends AppCompatActivity {

    private static final String CHANNEL_ID = "My Channel";
    private static final Integer NOTIFICATION_ID = 100;

    TextView songName,artistName,pass,due;
    ImageView songImage;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Handler handler;
    String out,out2;
    Integer difference;
    ImageView pause,play;

    CircleImageView prev,next;

    ArrayList<String> arrayListUrl;
    ArrayList<String> arrayListSong;
    ArrayList<String> arrayListImage;
    ArrayList<String> arrayListArtist;
    Integer position;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playing);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.blue));
        }


        songName = findViewById(R.id.songTitle);
        artistName = findViewById(R.id.songArtist);
        songImage = findViewById(R.id.songImage);
        pause = findViewById(R.id.pause);
        play = findViewById(R.id.play);

        prev = (CircleImageView) findViewById(R.id.prev);
        next = (CircleImageView) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arrayListUrl.size() == position+1){
                    position = 0;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                }else{
                    position = position+1;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                }

            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position == 0){
                    position = arrayListUrl.size()-1;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                }else{
                    position = position-1;
                    init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                }

            }
        });

        seekBar = (SeekBar)findViewById(R.id.seek_bar);
        pass = (TextView)findViewById(R.id.tv_pass);
        due = (TextView)findViewById(R.id.tv_due);

        mediaPlayer = new MediaPlayer();
        handler = new Handler();

        Intent intent = getIntent();
        String song = intent.getStringExtra("song");
        String artists = intent.getStringExtra("artists");
        String coverImage = intent.getStringExtra("cover_image");
        String url = intent.getStringExtra("url");

        arrayListUrl = intent.getStringArrayListExtra("arrayListUrl");
        arrayListSong = intent.getStringArrayListExtra("arrayListSong");
        arrayListArtist = intent.getStringArrayListExtra("arrayListArtist");
        arrayListImage = intent.getStringArrayListExtra("arrayListImage");
        position = Integer.parseInt(intent.getStringExtra("position"));


        init(song,artists,coverImage,url);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void init(String song, String artists, String coverImage, String url) {
        songName.setText(song);
        artistName.setText(artists);

        Glide.with(this)
                .load(coverImage)
                .override(300,400)
                .into(songImage);

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        mediaPlayer.start();
        initializeSeekBar();


    }

    private void initializeSeekBar() {

        seekBar.setMax(mediaPlayer.getDuration()/1000);

        song_playing_Activity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    int mCurrectPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrectPosition);
                    out = String.format("%02d:%02d",seekBar.getProgress() / 60,seekBar.getProgress() % 60);
                    pass.setText(out);

                    difference = mediaPlayer.getDuration()/1000 - mediaPlayer.getCurrentPosition()/1000;
                    out2 = String.format("%02d:%02d",difference / 60 , difference % 60);
                    due.setText(out2);

                    if(difference == 0){
                        if(arrayListUrl.size() == position+1){
                            position = 0;
                            init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                        }else{
                            position = position+1;
                            init(arrayListSong.get(position),arrayListArtist.get(position),arrayListImage.get(position),arrayListUrl.get(position));
                        }
                    }
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void play() {

        mediaPlayer.start();
        play.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
    }

    private void pause() {

        mediaPlayer.pause();
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}