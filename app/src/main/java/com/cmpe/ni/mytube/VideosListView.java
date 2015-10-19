package com.cmpe.ni.mytube;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

public class VideosListView extends ListView {

    public VideosListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VideosListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideosListView(Context context) {
        super(context);
    }

    public void setVideos(List<Video> videos, VideoClickListener listener){
        final VideosAdapter adapter = new VideosAdapter(getContext(), videos, listener);
        setAdapter(adapter);

        //my
        setMultiChoiceModeListener(new MultiChoiceModeListener() {
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
            public boolean onActionItemClicked (ActionMode mode, MenuItem item) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = adapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Video selecteditem = (Video) adapter.getItem(i);   //TODO check i
                                // Remove selected items following the ids
                                adapter.remove(selecteditem);
                                //removeFromYouTube();
                                //printList();
                                invalidate();
                                //getUserYouTubeFeed();
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
            public void onDestroyActionMode(ActionMode actionMode) {
                adapter.removeSelection();
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$");
                // Capture total checked items
                final int checkedCount = getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
               //toggleSelection(position);
            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }
}