package com.lxs.bilibili_android_copy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

/**
 * 轮播图适配器
 * Created by 00245338 on 2016/7/28.
 */
//轮播图适配器
public class GalleryPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> imageViewIds;

    public GalleryPagerAdapter(Context context, List<String> imageViewIds) {
        this.mContext = context;
        this.imageViewIds = imageViewIds;
    }

    @Override
    public int getCount() {
        return imageViewIds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(mContext);
        if (imageViewIds.size() > 0) {
            updateLooperView(imageViewIds.get(position), item);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        return item;
    }

    private void updateLooperView(String url, ImageView imageView) {
        Glide.with(mContext).load(url).crossFade()
                .into(imageView);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}