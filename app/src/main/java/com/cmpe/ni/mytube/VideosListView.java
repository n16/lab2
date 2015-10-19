package com.cmpe.ni.mytube;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
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
        VideosAdapter adapter = new VideosAdapter(getContext(), videos, listener);
        setAdapter(adapter);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }
}