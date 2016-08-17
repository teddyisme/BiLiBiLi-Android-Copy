package com.lxs.bilibili_android_copy.widet.LoopPaper;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by lixs on 2015/12/8.
 */
public class DisallowParentTouchViewPager extends ViewPager {

  private ViewGroup parent;

  public DisallowParentTouchViewPager(Context context) {
    super(context);
  }

  public DisallowParentTouchViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setNestParent(ViewGroup parent) {
    this.parent = parent;
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (parent != null) {
      parent.requestDisallowInterceptTouchEvent(true);
    }
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (parent != null) {
      parent.requestDisallowInterceptTouchEvent(true);
    }
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (parent != null) {
      parent.requestDisallowInterceptTouchEvent(true);
    }
    return super.onTouchEvent(ev);
  }
}