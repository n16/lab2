package com.cmpe.ni.mytube;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements VideoClickListener  {
    String videoToSearch = "";
    SearchView searchView;
    VideosAdapterSearch listviewadapter;
    final private String DEVKEY = "AIzaSyBk1K3GI4ogDKBk5qHL3L5R5ZUCOWbHI4I";

    private VideosListView listView;
    List<Video> videosList = new ArrayList<Video>();
    private VideoClickListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_main, container, false);

        listView = (VideosListView)view.findViewById(R.id.videosListViewSearch);
        listviewadapter = new VideosAdapterSearch(getActivity(), videosList, this);
        SearchView searchView = (SearchView)view.findViewById(R.id.search);
        SearchManager searchManager = (SearchManager)getActivity()
                .getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
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

        return view;
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
    public void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }
    @Override
    public void StartPlayback(Video video) {

    }


}



