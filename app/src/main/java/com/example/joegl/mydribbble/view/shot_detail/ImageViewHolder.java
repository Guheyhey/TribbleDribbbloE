package com.example.joegl.mydribbble.view.shot_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by joegl on 2017/4/15.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    SimpleDraweeView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        image = (SimpleDraweeView) itemView;
    }
}
