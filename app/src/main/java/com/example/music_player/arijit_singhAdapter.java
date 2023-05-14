package com.example.music_player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class arijit_singhAdapter extends RecyclerView.Adapter<arijit_singhAdapter.ViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<Song> songs;

    ArrayList<String> arrayListUrl = new ArrayList<>();
    ArrayList<String> arrayListSong = new ArrayList<>();
    ArrayList<String> arrayListArtist = new ArrayList<>();
    ArrayList<String> arrayListImage = new ArrayList<>();

    public arijit_singhAdapter(Context context,List<Song> songs) {
        this.songs = songs;
        this.context = context;
    }

    public arijit_singhAdapter() {
    }

    @NonNull
    @Override
    public arijit_singhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.songs_playlist_formate,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull arijit_singhAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.songTitle.setText(songs.get(position).getTitle());
        holder.songArtists.setText(songs.get(position).getArtist());
        holder.coverImageUrl.setText(songs.get(position).getCoverImage());
        holder.songUrl.setText(songs.get(position).getSongURL());
        Picasso.get().load(songs.get(position).getCoverImage()).into(holder.songCoverImage);

        if(!(arrayListUrl.contains(songs.get(position).getSongURL()))){
            arrayListUrl.add(songs.get(position).getSongURL());
        }
        if(!(arrayListSong.contains(songs.get(position).getTitle()))){
            arrayListSong.add(songs.get(position).getTitle());
        }
        if(!(arrayListArtist.contains(songs.get(position).getArtist()))){
            arrayListArtist.add(songs.get(position).getArtist());
        }
        if(!(arrayListImage.contains(songs.get(position).getCoverImage()))){
            arrayListImage.add(songs.get(position).getCoverImage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,song_playing_Activity.class);
                intent.putExtra("song",holder.songTitle.getText().toString());
                intent.putExtra("artists",holder.songArtists.getText().toString());
                intent.putExtra("cover_image",holder.coverImageUrl.getText().toString());
                intent.putExtra("url",holder.songUrl.getText().toString());

                intent.putExtra("arrayListUrl",arrayListUrl);
                intent.putExtra("arrayListArtist",arrayListArtist);
                intent.putExtra("arrayListImage",arrayListImage);
                intent.putExtra("arrayListSong",arrayListSong);
                intent.putExtra("position",String.valueOf(position));

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle,songArtists,coverImageUrl,songUrl;
        ImageView songCoverImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          songTitle = (TextView)itemView.findViewById(R.id.songTitle);
          songArtists = (TextView) itemView.findViewById(R.id.songArtist);
          songCoverImage = (ImageView) itemView.findViewById(R.id.coverImage);
          coverImageUrl = (TextView) itemView.findViewById(R.id.coverImageUrl);
          songUrl = (TextView) itemView.findViewById(R.id.songUrl);

        }
    }
}
