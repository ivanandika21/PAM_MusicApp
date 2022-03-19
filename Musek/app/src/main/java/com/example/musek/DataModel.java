package com.example.musek;

import java.io.Serializable;

public class DataModel implements Serializable {
    String path;
    String title;
    String duration;
    String artist;
    String albumart;

    public DataModel(String path, String title, String duration, String artist) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
