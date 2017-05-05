package com.example.joegl.mydribbble.view.shot_list;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.dribbble.Dribbble;
import com.example.joegl.mydribbble.model.Shot;
import com.example.joegl.mydribbble.model.User;
import com.example.joegl.mydribbble.view.base.SpaceItemDecoration;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
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

    private static final int COUNT_PER_PAGE = 12;

    private ShotListAdapter adapter;

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

        adapter = new ShotListAdapter(new ArrayList<Shot>(), new ShotListAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // this method will be called when the RecyclerView is displayed
                // page starts from 1
                AsyncTaskCompat.executeParallel(new LoadShotTask(adapter.getDataCount() / COUNT_PER_PAGE + 1));
            }
        });

//        final Handler handler = new Handler();
//        adapter = new ShotListAdapter(fakeData(0), new ShotListAdapter.LoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    List<Shot> moreData = fakeData(adapter.getDataCount() / COUNT_PER_PAGE);
//                                    adapter.append(moreData);
//                                    adapter.setShowLoading(moreData.size() >= COUNT_PER_PAGE);
//                                }
//                            });
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        });
        recyclerView.setAdapter(adapter);
    }

    private class LoadShotTask extends AsyncTask<Void, Void, List<Shot>> {

        int page;

        public LoadShotTask(int page) {
            this.page = page;
        }

        @Override
        protected List<Shot> doInBackground(Void... params) {
            // this method is executed on non-UI thread
            try {
                return Dribbble.getShots(page);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Shot> shots) {
            // this method is executed on UI thread!!!!
            if (shots != null) {
                adapter.append(shots);
            } else {
                Snackbar.make(getView(), "Error!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

//    private List<Shot> fakeData(int page) {
//        List<Shot> shotList = new ArrayList<>();
//        Random random = new Random();
//
//        int count = page < 2 ? COUNT_PER_PAGE : 10;
//
//        for (int i = 0; i < count; i++) {
//            Shot shot = new Shot();
//            shot.views_count = random.nextInt(10000);
//            shot.buckets_count = random.nextInt(50);
//            shot.likes_count = random.nextInt(200);
//            shot.description = makeDescription();
//
//            shot.user = new User();
//            shot.user.name = shot.title + " author";
//            shotList.add(shot);
//        }
//        return shotList;
//    }

//    private static final String[] words = {
//            "bottle", "bowl", "brick", "building", "bunny", "cake", "car", "cat", "cup",
//            "desk", "dog", "duck", "elephant", "engineer", "fork", "glass", "griffon", "hat", "key",
//            "knife", "lawyer", "llama", "manual", "meat", "monitor", "mouse", "tangerine", "paper",
//            "pear", "pen", "pencil", "phone", "physicist", "planet", "potato", "road", "salad",
//            "shoe", "slipper", "soup", "spoon", "star", "steak", "table", "terminal", "treehouse",
//            "truck", "watermelon", "window"
//    };
//
//    private static String makeDescription() {
//        return TextUtils.join(" ", words);
//    }
}
