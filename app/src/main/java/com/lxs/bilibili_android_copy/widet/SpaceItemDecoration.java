package com.lxs.bilibili_android_copy.widet;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = space;
        } else {
            outRect.left = space;
            outRect.right = space;
        }

    }
}