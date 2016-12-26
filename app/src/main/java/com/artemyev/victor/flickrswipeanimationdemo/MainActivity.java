package com.artemyev.victor.flickrswipeanimationdemo;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Victor Artemyev on 26/12/2016.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int BOTTOM_MARGIN = 160;

    ViewSwitcher mViewSwitcher;
    SwipePlaceHolderView mSwipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipe_view);

        Point displaySize = getDisplaySize();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(displaySize.x)
                        .setViewHeight(displaySize.y - getBottomMargin())
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20));

        findViewById(R.id.reject_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.accept_button).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        new DownloadPhotosTask().execute();
    }

    private int getBottomMargin() {
        return (int) (BOTTOM_MARGIN * Resources.getSystem().getDisplayMetrics().density);
    }

    public Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    private class DownloadPhotosTask extends AsyncTask<Void, Integer, List<Photo>> {

        @Override protected List<Photo> doInBackground(Void... params) {
            DataSource dataSource = new DataSource();
            try {
                return dataSource.getCatsPhoto();
            } catch (Exception e) {
                Log.e(TAG, "doInBackground", e);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                return Collections.emptyList();
            }
        }

        @Override protected void onPostExecute(List<Photo> photos) {
            Log.d(TAG, photos.toString());
            for (Photo photo : photos) {
                mSwipeView.addView(new PhotoCard(photo));
            }
            mViewSwitcher.setDisplayedChild(1);
        }
    }
}
