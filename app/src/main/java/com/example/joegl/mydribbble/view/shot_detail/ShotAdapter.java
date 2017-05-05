package com.example.joegl.mydribbble.view.shot_detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.model.Shot;
import com.example.joegl.mydribbble.utils.ImageUtils;

/**
 * Created by joegl on 2017/4/15.
 */

public class ShotAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_SHOT_IMAGE = 0;
    private static final int VIEW_TYPE_SHOT_INFO = 1;

    private final ShotFragment shotFragment;
    private final Shot shot;


    public ShotAdapter(@NonNull ShotFragment shotFragment,
                       @NonNull Shot shot) {
        this.shotFragment = shotFragment;
        this.shot = shot;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_SHOT_IMAGE:
                view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.shot_item_image, parent, false);
                return new ShotImageViewHolder(view);

            case VIEW_TYPE_SHOT_INFO:
                view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.shot_item_info, parent, false);
                return new ShotDetailViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_SHOT_IMAGE:
                ImageUtils.loadShotImage(shot, ((ShotImageViewHolder) holder).image);
                break;
            case VIEW_TYPE_SHOT_INFO:
                final ShotDetailViewHolder shotDetailViewHolder = (ShotDetailViewHolder) holder;
                shotDetailViewHolder.title.setText(shot.title);
                shotDetailViewHolder.authorName.setText(shot.user.name);

                shotDetailViewHolder.description.setText(Html.fromHtml(
                        shot.description == null ? "" : shot.description));
                shotDetailViewHolder.description.setMovementMethod(LinkMovementMethod.getInstance());

                shotDetailViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
                shotDetailViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
                shotDetailViewHolder.viewCount.setText(String.valueOf(shot.views_count));


                ImageUtils.loadUserPicture(getContext(),
                                           shotDetailViewHolder.authorPicture,
                                           shot.user.avatar_url);

                shotDetailViewHolder.likeCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Like count clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                shotDetailViewHolder.bucketCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Bucket count clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                shotDetailViewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shotFragment.like(shot.id, !shot.liked);
                    }
                });
                shotDetailViewHolder.bucketButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shotFragment.bucket();
                    }
                });
                shotDetailViewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shotFragment.share();
                    }
                });

                Drawable bucketDrawable = shot.bucketed
                        ? ContextCompat.getDrawable(getContext(), R.drawable.ic_inbox_purple_600_18dp)
                        : ContextCompat.getDrawable(getContext(), R.drawable.ic_inbox_black_18dp);

                Drawable likeDrawable = shot.liked
                        ? ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_dribbble_18dp)
                        : ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_black_18dp);
                shotDetailViewHolder.likeButton.setImageDrawable(likeDrawable);

                shotDetailViewHolder.bucketButton.setImageDrawable(bucketDrawable);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SHOT_IMAGE;
        } else {
            return VIEW_TYPE_SHOT_INFO;
        }
    }



    @NonNull
    private Context getContext() {
        return shotFragment.getContext();
    }
}
