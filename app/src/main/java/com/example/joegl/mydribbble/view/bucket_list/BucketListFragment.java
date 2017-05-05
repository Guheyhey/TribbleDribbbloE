package com.example.joegl.mydribbble.view.bucket_list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joegl.mydribbble.R;
import com.example.joegl.mydribbble.dribbble.Dribbble;
import com.example.joegl.mydribbble.model.Bucket;
import com.example.joegl.mydribbble.view.base.SpaceItemDecoration;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joegl on 2017/4/13.
 */

public class BucketListFragment extends Fragment {

    public static final int REQ_CODE_NEW_BUCKET = 100;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;

    private BucketListAdapter adapter;

    public static BucketListFragment newInstance() {
        return new BucketListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fab_recycler_view, container, false);
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

        adapter = new BucketListAdapter(new ArrayList<Bucket>(), new BucketListAdapter.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                AsyncTaskCompat.executeParallel(
                        new LoadBucketTask(adapter.getDataCount() / Dribbble.COUNT_PER_PAGE + 1));
            }
        });

        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewBucketDialogFragment dialogFragment = NewBucketDialogFragment.newInstance();
                dialogFragment.setTargetFragment(BucketListFragment.this, REQ_CODE_NEW_BUCKET);
                dialogFragment.show(getFragmentManager(), NewBucketDialogFragment.TAG);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_NEW_BUCKET && resultCode == Activity.RESULT_OK) {
            String bucketName = data.getStringExtra(NewBucketDialogFragment.KEY_BUCKET_NAME);
            String bucketDescription = data.getStringExtra(NewBucketDialogFragment.KEY_BUCKET_DESCRIPTION);
            if (!TextUtils.isEmpty(bucketName)) {
                AsyncTaskCompat.executeParallel(new NewBucketTask(bucketName, bucketDescription));
            }
        }
    }

//    private List<Bucket> fakeData() {
//        List<Bucket> bucketList = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 2; i++) {
//            Bucket bucket = new Bucket();
//            bucket.name = "Bucket" + i;
//            bucket.shots_count = random.nextInt(10);
//            bucketList.add(bucket);
//        }
//
//        return bucketList;
//    }
    private class LoadBucketTask extends AsyncTask<Void, Void, List<Bucket>> {

        int page;

        public LoadBucketTask(int page) {
            this.page = page;
        }

        @Override
        protected List<Bucket> doInBackground(Void... params) {
            // this method is executed on non-UI thread
            try {
                return Dribbble.getUserBuckets(page);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Bucket> buckets) {
            // this method is executed on UI thread!!!!
            if (buckets != null) {
                adapter.append(buckets);
                adapter.setShowLoading(buckets.size() == Dribbble.COUNT_PER_PAGE);
            } else {
                Snackbar.make(getView(), "Error!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private class NewBucketTask extends AsyncTask<Void, Void, Bucket> {

        private String name;
        private String description;

        private NewBucketTask(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @Override
        protected Bucket doInBackground(Void... params) {
            try {
                return Dribbble.newBucket(name, description);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bucket newBucket) {
            // this method is executed on UI thread!!!!
            if (newBucket != null) {
                adapter.prepend(Collections.singletonList(newBucket));
            } else {
                Snackbar.make(getView(), "Error!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
