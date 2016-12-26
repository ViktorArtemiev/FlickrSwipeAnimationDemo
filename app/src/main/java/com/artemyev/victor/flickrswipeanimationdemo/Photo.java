package com.artemyev.victor.flickrswipeanimationdemo;

/**
 * Created by Victor Artemyev on 26/12/2016.
 */

public class Photo {

    private final String mTitle;
    private final String mUrl;


    public Photo(String title, String url) {
        mTitle = title;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
