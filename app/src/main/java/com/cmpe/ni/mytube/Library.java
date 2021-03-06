package com.cmpe.ni.mytube;

import java.io.Serializable;
import java.util.List;

public class Library implements Serializable{
    private String user;
    private List<Video> videos;

    public Library(String user, List<Video> videos) {
        this.user = user;
        this.videos = videos;
    }
    public String getUser() {
        return user;
    }

    public List<Video> getVideos() {
        return videos;
    }
}