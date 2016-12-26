package com.artemyev.victor.flickrswipeanimationdemo;

import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by Victor Artemyev on 26/12/2016.
 */

@Layout(R.layout.item_photo)
public class PhotoCard {

    @View(R.id.photo_image_view)
    private ImageView mPhotoImageView;

    @View(R.id.title_text_view)
    private TextView mTitleTextView;

    private final Photo mPhoto;

    public PhotoCard(Photo photo) {
        mPhoto = photo;
    }

    @Resolve
    private void onResolved(){
        Glide.with(mPhotoImageView.getContext())
                .load(mPhoto.getUrl())
                .into(mPhotoImageView);
        mTitleTextView.setText(mPhoto.getTitle());
    }
}
