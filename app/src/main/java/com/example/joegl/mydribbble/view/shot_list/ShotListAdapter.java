package com.example.joegl.mydribbble.view.shot_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.model.Shot;
import com.example.joegl.mydribbble.utils.ImageUtils;
import com.example.joegl.mydribbble.utils.ModelUtils;
import com.example.joegl.mydribbble.view.base.BaseViewHolder;
import com.example.joegl.mydribbble.view.base.InfiniteAdapter;
import com.example.joegl.mydribbble.view.shot_detail.ShotActivity;
import com.example.joegl.mydribbble.view.shot_detail.ShotFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by joegl on 2017/4/10.
 */

public class ShotListAdapter extends InfiniteAdapter<Shot> {

    private final ShotListFragment shotListFragment;

    public ShotListAdapter(@NonNull ShotListFragment shotListFragment,
                           @NonNull List<Shot> data,
                           @NonNull LoadMoreListener loadMoreListener) {
        super(shotListFragment.getContext(), data, loadMoreListener);
        this.shotListFragment = shotListFragment;
    }

    @Override
    protected BaseViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.list_item_shot, parent, false);
        return new ShotViewHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(BaseViewHolder holder, int position) {
        ShotViewHolder shotViewHolder = (ShotViewHolder) holder;

        final Shot shot = getData().get(position);
        shotViewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShotActivity.class);
                intent.putExtra(ShotFragment.KEY_SHOT,
                        ModelUtils.toString(shot, new TypeToken<Shot>(){}));
                intent.putExtra(ShotActivity.KEY_SHOT_TITLE, shot.title);
                shotListFragment.startActivityForResult(intent, ShotListFragment.REQ_CODE_SHOT);
            }
        });

        shotViewHolder.likeCount.setText(String.valueOf(shot.likes_count));
        shotViewHolder.bucketCount.setText(String.valueOf(shot.buckets_count));
        shotViewHolder.viewCount.setText(String.valueOf(shot.views_count));

        ImageUtils.loadShotImage(shot, shotViewHolder.image);
    }
}
