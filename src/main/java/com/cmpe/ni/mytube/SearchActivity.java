package com.cmpe.ni.mytube;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import com.cmpe.ni.mytube.FavoriteActivity;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements VideoClickListener  {
    String videoToSearch = "";
    SearchView searchView;
    VideosAdapterSearch listviewadapter;
    final private String DEVKEY = "AIzaSyBk1K3GI4ogDKBk5qHL3L5R5ZUCOWbHI4I";

    private VideosListView listView;
    List<Video> videosList = new ArrayList<Video>();
    private VideoClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        listView = (VideosListView) findViewById(R.id.videosListViewSearch);
        listviewadapter = new VideosAdapterSearch(this, videosList, this);
        SearchView searchView = (SearchView)findViewById(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                videoToSearch = query.toLowerCase();
                getSearchedYouTubeFeed();
                return true;
                //TODO check the output
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        //return super.onCreateOptionsMenu(menu);


        //get the searched videos
        //getSearchedYouTubeFeed();
    }

    // This is the XML onClick listener to retreive a users video feed
    public void getSearchedYouTubeFeed(){
        new Thread(new SearchVideos(responseHandler, "nissaie16", videoToSearch)).start();
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
    protected void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }
    @Override
    public void StartPlayback(Video video) {

    }


}



