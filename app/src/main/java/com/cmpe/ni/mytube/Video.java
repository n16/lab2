package com.cmpe.ni.mytube;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String thumbUrl;
    private int value;
    private String id;
    private String playlist;
    private String publishedAt;

    public Video(String title, String thumbUrl, int value, String id, String playlist, String publishedAt) {
        super();
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.value = value;
        this.id = id;
        this.playlist = playlist;
        this.publishedAt = publishedAt;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + id + "&list=" + playlist;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public int getValue(){
        return this.value;
    }

    public String getId(){
        return id;
    }

    public String getPlaylist(){
        return playlist;
    }

    public String getPublishedAt() { return publishedAt; }
}