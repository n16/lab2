package com.cmpe.ni.mytube;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements VideoClickListener  {
    VideosAdapter listviewadapter;
    final private String DEVKEY = "AIzaSyBk1K3GI4ogDKBk5qHL3L5R5ZUCOWbHI4I";

    private VideosListView listView;
    List<Video> videosList = new ArrayList<Video>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_main, container, false);

        listView = (VideosListView) view.findViewById(R.id.videosListView);
        listviewadapter = new VideosAdapter(getActivity(), videosList, this);

        getUserYouTubeFeed();

        return view;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu items for use in the action bar
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        final CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
//
//        SparseBooleanArray selected = listviewadapter
//                .getSelectedIds();
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                //TODO read the list
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    // This is the XML onClick listener to retreive a users video feed
    public void getUserYouTubeFeed(){
        new Thread(new GetVideos(responseHandler, "nissaie16")).start();
    }

    // This is the handler that receives the response when the YouTube task has finished
    Handler responseHandler = new Handler() {
        public void handleMessage(Message msg) {
            populateListWithVideos(msg);
        };
    };

    /**
     * This method retrieves the Library of videos from the task and passes them to our ListView
     * @param msg
     */
    private void populateListWithVideos(Message msg) {
        // Retreive the videos are task found from the data bundle sent back
        Library lib = (Library) msg.getData().get(GetVideos.LIBRARY);
        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
        // we can just call our custom method with the list of items we want to display
        listView.setVideos(lib.getVideos(), this);
    }

    @Override
    public void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }


    @Override
    public void StartPlayback(Video video) {
        try {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), DEVKEY, video.getId());
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast t = Toast.makeText(getActivity(),
                    "Could not start playback of video, please ensure you have the YouTube app installed",
                    Toast.LENGTH_LONG);
            t.show();
        } catch (Exception e) {
            Toast t = Toast.makeText(getActivity(),
                    "Couldn't start video: " + e.getMessage(),
                    Toast.LENGTH_LONG);
            t.show();
        }
    }
}