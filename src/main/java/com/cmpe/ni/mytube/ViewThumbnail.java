package com.cmpe.ni.mytube;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ViewThumbnail extends LinearLayout {

    private Context theContext;
    private Drawable mDrawable;
    private ProgressBar mSpinner;
    private ImageView thumbnail;

    public ViewThumbnail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewThumbnail(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        theContext = context;

        thumbnail = new ImageView(theContext);
        thumbnail.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        thumbnail.setVisibility(View.GONE);

        mSpinner = new ProgressBar(theContext);
        mSpinner.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        mSpinner.setIndeterminate(true);

        addView(mSpinner);
        addView(thumbnail);
    }

    public void setThumbnail(final String imageUrl) {
        mDrawable = null;
        mSpinner.setVisibility(View.VISIBLE);
        thumbnail.setVisibility(View.GONE);
        new Thread() {
            public void run() {
                try {
                    mDrawable = getThumbnail(imageUrl);
                    thumbnailHandler.sendEmptyMessage(RESULT_OK);
                } catch (MalformedURLException e) {
                    thumbnailHandler.sendEmptyMessage(RESULT_CANCELED);
                } catch (IOException e) {
                    thumbnailHandler.sendEmptyMessage(RESULT_CANCELED);
                }
            };
        }.start();
    }

    private final Handler thumbnailHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_OK:
                    thumbnail.setImageDrawable(mDrawable);
                    thumbnail.setVisibility(View.VISIBLE);
                    mSpinner.setVisibility(View.GONE);
                    break;
                case RESULT_CANCELED:
                default:
                    break;
            }
            return true;
        }
    });

    private static Drawable getThumbnail(final String url)
            throws IOException {
        return Drawable.createFromStream(((java.io.InputStream)
                new java.net.URL(url).getContent()), "name");
    }

}