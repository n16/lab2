package com.cmpe.ni.mytube;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class VideosAdapterSearch extends BaseAdapter {
    private SparseBooleanArray mSelectedItemsIds;
    List<Video> videos;
    private LayoutInflater mInflater;
    private VideoClickListener listener;

    public VideosAdapterSearch(Context context, List<Video> videos, VideoClickListener listener) {
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
            convertView = mInflater.inflate(R.layout.search_item, null);
        }
        ViewThumbnail thumb = (ViewThumbnail) convertView.findViewById(R.id.userVideoThumbImageView);

        TextView title = (TextView) convertView.findViewById(R.id.userVideoTitleTextViewSearch);


        final RatingBar cb = (RatingBar) convertView.findViewById(R.id.ratingStar);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videos.get(position).getValue() == 1)
                    cb.performClick();
                else
                    cb.performClick();
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