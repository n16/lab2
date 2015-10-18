package com.cmpe.ni.mytube;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity  {
    VideosAdapter listviewadapter;

    private VideosListView listView;
    List<Video> videosList = new ArrayList<Video>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_main);
        listView = (VideosListView) findViewById(R.id.videosListView);
        listviewadapter = new VideosAdapter(this, videosList);

        //get the videos
        getUserYouTubeFeed();

       /*
        // Capture ListView item click
        listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked (ActionMode mode, MenuItem item) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listviewadapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Video selecteditem = (Video)listviewadapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                //listviewadapter.remove(selecteditem);
                                //removeFromYouTube();
                                printList();
                                listView.invalidate();
                                getUserYouTubeFeed();
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listviewadapter.removeSelection();
            }
        }
        );*/

    }

    public void printList() {
        for (int i = 0; i < videosList.size(); i++) {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$"+videosList.get(i).getTitle());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final CheckBox cb = (CheckBox) findViewById(R.id.checkBox);

        SparseBooleanArray selected = listviewadapter
                .getSelectedIds();
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                //TODO read the list
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        listView.setVideos(lib.getVideos());
    }

    @Override
    protected void onStop() {
        // Make sure we null our handler when the activity has stopped
        // because who cares if we get a callback once the activity has stopped? not me!
        responseHandler = null;
        super.onStop();
    }


}