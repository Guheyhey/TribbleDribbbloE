package com.example.joegl.mydribbble.view.shot_detail;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.model.Shot;
import com.example.joegl.mydribbble.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joegl on 2017/4/15.
 */

public class ShotFragment extends Fragment {

    public static final String KEY_SHOT = "shot";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static ShotFragment newInstance(@NonNull Bundle args) {
        ShotFragment fragment = new ShotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        Shot shot = ModelUtils.toObject(getArguments().getString(KEY_SHOT),
                new TypeToken<Shot>(){});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        recyclerView.setAdapter(new ShotAdapter(shot));
    }
}
