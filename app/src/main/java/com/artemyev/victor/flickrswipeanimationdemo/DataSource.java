package com.artemyev.victor.flickrswipeanimationdemo;

import android.support.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Victor Artemyev on 26/12/2016.
 */

public class DataSource {

    private static final String GET_PHOTOS =
            "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
                    "&text=cats&api_key=4d55467d6fdc0e2bd608b9fefc758115" +
                    "&format=json&extras=url_m&sort=interestingness-desc";

    private final OkHttpClient mOkHttpClient;

    public DataSource() {
        mOkHttpClient = buildClient();
    }

    public List<Photo> getCatsPhoto() throws Exception {
        String response = createGET(GET_PHOTOS);
        return responseToPhotos(response);
    }

    private List<Photo> responseToPhotos(String response) throws Exception {
        String jsonString = response.substring(14, response.length() - 1);
        JSONObject root = new JSONObject(jsonString);
        JSONObject photos = root.getJSONObject("photos");
        JSONArray photoArray = photos.getJSONArray("photo");
        List<Photo> result = new ArrayList<>(photoArray.length());
        for (int i = 0; i < photoArray.length(); i++) {
            JSONObject jsonObject = photoArray.getJSONObject(i);
            Photo photo = jsonToPhoto(jsonObject);
            if (photo != null) {
                result.add(photo);
            }
        }

        return result;
    }

    @Nullable private Photo jsonToPhoto(JSONObject json) throws JSONException {
        if (json.has("url_m")) {
            String url = json.getString("url_m");
            String title = json.getString("title");
            return new Photo(title, url);
        } else {
            return null;
        }
    }

    private OkHttpClient buildClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }

    private String createGET(String url) throws Exception {
        Request request = buildGETRequest(url);
        Response response = mOkHttpClient.newCall(request).execute();
        ResponseBody body = response.body();
        return body.string();
    }

    private Request buildGETRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
