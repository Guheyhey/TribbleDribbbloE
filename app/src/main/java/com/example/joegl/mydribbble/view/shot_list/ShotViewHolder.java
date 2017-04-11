package com.example.joegl.mydribbble.view.shot_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.view.base.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by joegl on 2017/4/10.
 */

public class ShotViewHolder extends BaseViewHolder {

    @BindView(R.id.shot_clickable_cover) public View cover;
    @BindView(R.id.shot_likes_count) public TextView likeCount;
    @BindView(R.id.shot_view_count) public TextView viewCount;
    @BindView(R.id.shot_bucket_count) public TextView bucketCount;
    @BindView(R.id.shot_image) public ImageView image;

    public ShotViewHolder(View itemView) {
        super(itemView);
    }
}
