package com.example.joegl.mydribbble.view.shot_list;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.model.Shot;
import com.example.joegl.mydribbble.view.base.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joegl on 2017/4/10.
 */

public class ShotListFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static ShotListFragment newInstance() {
        return new ShotListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        ShotListAdapter adapter = new ShotListAdapter(fakeData());
        recyclerView.setAdapter(adapter);

    }

    private List<Shot> fakeData() {
        List<Shot> shotList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Shot shot = new Shot();
            shot.views_count = random.nextInt(10000);
            shot.buckets_count = random.nextInt(50);
            shot.likes_count = random.nextInt(200);
            shotList.add(shot);
        }
        return shotList;
    }
}
