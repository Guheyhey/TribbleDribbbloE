package com.example.joegl.mydribbble.view.shot_detail;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.joegl.mydribbble.view.base.SingleFragmentActivity;

/**
 * Created by joegl on 2017/4/15.
 */

public class ShotActivity extends SingleFragmentActivity {

    public static final String KEY_SHOT_TITLE = "shot_title";

    @NonNull
    @Override
    protected Fragment newFragment() {
        return ShotFragment.newInstance(getIntent().getExtras());
    }

    @NonNull
    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(KEY_SHOT_TITLE);
    }
}
