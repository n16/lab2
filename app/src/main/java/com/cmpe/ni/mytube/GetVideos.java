package com.cmpe.ni.mytube;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;

public class GetVideos implements Runnable {
    public static final String LIBRARY = "Library";
    private final Handler replyTo;
    private final String username;

    public GetVideos(Handler replyTo, String username) {
        this.replyTo = replyTo;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpUriRequest request = new HttpGet("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL6VpGVvm-JOgWvsLMH78alYkiro25w4EO&key=AIzaSyBk1K3GI4ogDKBk5qHL3L5R5ZUCOWbHI4I&v=2&alt=json");
            //HttpUriRequest request = new HttpGet("https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PL6VpGVvm-JOgWvsLMH78alYkiro25w4EO");

            HttpResponse response = client.execute(request);
            String jsonString = GetString.convertToString(response.getEntity().getContent());
            System.out.println("This jsonString: "+ jsonString);
            // Create a JSON object that we can use from the String
            JSONObject json = new JSONObject(jsonString);
            System.out.println("This json: "+ json);
            JSONArray jsonArray = json.getJSONArray("items");
            System.out.println("This jsonArray: "+ jsonArray.toString());
            List<Video> videos = new ArrayList<Video>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("This Object: "+ jsonObject.toString());

                // The title of the video
                String title = jsonObject.getJSONObject("snippet").getString("title");
                System.out.println("This title: "+ title);

                String id = jsonObject.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                String playlist = jsonObject.getJSONObject("snippet").getString("playlistId");

                String thumbUrl = jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("standard").getString("url");
                videos.add(new Video(title, thumbUrl, 1, id, playlist));

            }
            Library lib = new Library(username, videos);
            System.out.println("**");
            Bundle data = new Bundle();
            System.out.println("***");
            data.putSerializable(LIBRARY, lib);
            System.out.println("****");
            Message msg = Message.obtain();
            System.out.println("*****");
            msg.setData(data);
            System.out.println("******");
            replyTo.sendMessage(msg);
            System.out.println("*******");

        } catch (Exception e) {
            System.out.println("ERROR getting URL ******************************");
        }

    }
}