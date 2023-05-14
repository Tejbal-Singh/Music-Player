package com.example.music_player;

public class Song {

    private String title;
    private String artist;
    private String coverImage;
    private String songURL;

    public Song(String title, String artist, String coverImage, String songURL) {
        this.title = title;
        this.artist = artist;
        this.coverImage = coverImage;
        this.songURL = songURL;
    }

    public Song() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }
}
