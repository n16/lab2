package com.cmpe.ni.mytube;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String url;
    private String thumbUrl;
    private int value;

    public Video(String title, String url, String thumbUrl, int value) {
        super();
        this.title = title;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.value = value;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public int getValue(){
        return this.value;
    }
}