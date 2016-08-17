package com.lxs.bilibili_android_copy.base;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.utils.PersistTool;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import derson.com.multipletheme.colorUi.util.ColorUiUtil;

public abstract class BaseActivity
        extends AppCompatActivity implements IBaseActivity {

    protected ProgressDialog progressDialog;
    protected Context mContext;

    private View mContextView = null;

    public Dispatcher dispatcher;
    private Store store;

    protected final static int NIGHT_THEME = 1;
    protected final static int DAY_THEME = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initStatusBar();

        loadUi();
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        dispatcher = Dispatcher.get();

        mContext = this;
        initDialog();

        initView(mContextView);

        doBusiness(this);

    }

    private void initStatusBar() {
        //读取SharedPreferences中的配置并应用相应的皮肤
        if (PersistTool.getInt("theme", 0) == DAY_THEME) {
            changeTheme(DAY_THEME);
            setStatusColor(R.color.background_night_two);
        } else {
            changeTheme(NIGHT_THEME);
            setStatusColor(R.color.background_night_two);
        }
    }

    public void changeTheme() {
        if (PersistTool.getInt("theme", 0) == DAY_THEME) {
            changeTheme(NIGHT_THEME);
            PersistTool.saveInt("theme", NIGHT_THEME);
        } else {
            changeTheme(DAY_THEME);
            PersistTool.saveInt("theme", DAY_THEME);
        }
        changeColor();
    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//    window.setFlags(
//      WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//      WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 根据传入的参数调换皮肤,设置状态栏颜色
     *
     * @param type 需要更换的皮肤
     */
    public void changeTheme(int type) {
        if (type == DAY_THEME) {
            setTheme(R.style.day);
            setStatusColor(R.color.colorPrimary);
        } else {
            setTheme(R.style.night);
            setStatusColor(R.color.background_night);
        }
    }

    /**
     * 设置通知栏颜色
     *
     * @param color
     */
    protected void setStatusColor(int color) {
        if (color != -1) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(ContextCompat.getColor(this, color));//通知栏所需颜色
        }
    }

    public int getThemType() {
        return PersistTool.getInt("theme", 0);
    }

    protected void changeColor() {
        final View rootView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= 14) {
            rootView.setDrawingCacheEnabled(true);
            rootView.buildDrawingCache(true);
            final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
            rootView.setDrawingCacheEnabled(false);
            if (null != localBitmap && rootView instanceof ViewGroup) {
                final View localView2 = new View(getApplicationContext());
                localView2.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ((ViewGroup) rootView).addView(localView2, params);
                localView2.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ColorUiUtil.changeTheme(rootView, getTheme());
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((ViewGroup) rootView).removeView(localView2);
                        localBitmap.recycle();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }
        } else {
            ColorUiUtil.changeTheme(rootView, getTheme());
        }
    }

    private void loadUi() {
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);
    }

    @Override
    public void initView(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (store != null) {
            dispatcher.unregister(this, store);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        store = initStore();
        if (store != null) {
            dispatcher.register(this, store);
            store.onStart();
        }
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.show_loading_msg));
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void resume() {

    }


    @Subscribe
    public void onEventMainThread(Object event) {
        onViewUpdate(event);
    }
}
