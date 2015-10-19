package com.cmpe.ni.mytube;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VideosAdapter extends BaseAdapter {
    private SparseBooleanArray mSelectedItemsIds;
    public List<Video> videos;
    public List<Video> toBeRemovedList = new ArrayList<Video>();
    private LayoutInflater mInflater;
    private VideoClickListener listener;

    public VideosAdapter(Context context, List<Video> videos, VideoClickListener listener) {
        this.videos = videos;
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView rank;
        TextView country;
        TextView population;
        ImageView flag;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item, null);
        }
        ViewThumbnail thumb = (ViewThumbnail) convertView.findViewById(R.id.userVideoThumbImageView);

        TextView title = (TextView) convertView.findViewById(R.id.userVideoTitleTextView);


        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videos.get(position).getValue() == 1) {
                    cb.setChecked(true);

                    //my
                    Video country = videos.get(position);
                    System.out.println("Remove");
                    System.out.println(country);
                    //remove(country);
                    //collect all videos to be removed
                    toBeRemovedList.add(country);
                    System.out.println("REMOVED");
                }
                else
                    cb.setChecked(false);
            }
        });

        final Video video = videos.get(position);

        thumb.setThumbnail(video.getThumbUrl());
        title.setText(video.getTitle());
//        cb.setChecked(false);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.StartPlayback(video);
                }
            }
        });

        return convertView;
    }

    public List<Video> returnNewVideoList() {
        return toBeRemovedList;

    }

    public void setNewVideoList(List<Video> list) {
        videos = list;
    }

    public List<Video>currentVideos() {
        return videos;
    }

    public void remove(Video i) {
        videos.remove(i);
        notifyDataSetChanged();
        //TODO refresh
    }


    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

}