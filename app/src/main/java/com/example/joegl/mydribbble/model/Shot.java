package com.example.joegl.mydribbble.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Map;

/**
 * Created by joegl on 2017/4/10.
 */

public class Shot {

    public final static String IMAGE_NORMAL = "normal";
    public final static String IMAGE_HIDPI = "hidpi";

    public String id;
    public String title;
    public String description;
    public String html_url;

    public int width;
    public int height;
    public Map<String, String> images;
    public boolean animated;

    public int views_count;
    public int likes_count;
    public int buckets_count;

    public Date created_at;

    public User user;

    public boolean liked;
    public boolean bucketed;

    @NonNull
    public String getImageUrl() {
        if (images == null) {
            return null;
        } else if (animated) {
            return images.get(IMAGE_NORMAL);
        }
        return images.containsKey(IMAGE_HIDPI)
                ? images.get(IMAGE_HIDPI)
                : images.get(IMAGE_NORMAL);
    }

}
