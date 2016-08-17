package com.lxs.bilibili_android_copy.ui.activity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.lxs.bilibili_android_copy.IjkPlayer.media.IjkVideoView;
import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.base.BaseActivity;
import com.lxs.bilibili_android_copy.base.Store;
import com.lxs.bilibili_android_copy.utils.DensityUtils;
import com.lxs.bilibili_android_copy.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.video_view)
    IjkVideoView videoView;

    @BindView(R.id.iv_fullscreen)
    ImageView ivFullScreen;

//    @BindView(R.id.ly_system_parent)
//    LinearLayout lySystemParent;

    @BindView(R.id.ly_floor)
    LinearLayout lyFloor;

    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //视频播放前显示的图片
    @BindView(R.id.video_image)
    ImageView videoImageIv;

    private int count = 0;

    private final int CONNECTION_TIMES = 5;

    @Override
    public int bindLayout() {
        return R.layout.activity_video_detail;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void doBusiness(Context mContext) {

        setSupportActionBar(toolbar);

//        toolbar.setTitle("111111111111111111111");


        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Glide.with(mContext).load(getIntent().getStringExtra("imageUrl")).crossFade()
                .into(videoImageIv);


        final String path = "http://live.gslb.letv.com/gslb?stream_id=lb_hkmovie_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1";
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView.setVideoURI(Uri.parse(path));

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                rlLoading.setVisibility(View.GONE);
//                videoView.start();
            }
        });

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        break;
                }
                return false;
            }
        });

        videoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                videoView.stopPlayback();
                videoView.release(true);
                videoView.setVideoURI(Uri.parse(path));
            }
        });

        videoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                if (count > CONNECTION_TIMES) {
                    new AlertDialog.Builder(VideoDetailActivity.this)
                            .setMessage("视频暂时不能播放")
                            .setPositiveButton("知道了",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            /* If we get here, there is no onError listener, so
                                             * at least inform them that the video is over.
                                             */
                                        }
                                    })
                            .setCancelable(false)
                            .show();
                } else {
                    videoView.stopPlayback();
                    videoView.release(true);
//                    videoView.stopBackgroundPlay();
//                    IjkMediaPlayer.native_profileEnd();
//
//                    IjkMediaPlayer.loadLibrariesOnce(null);
//                    IjkMediaPlayer.native_profileBegin("libijkplayer.so");

                    videoView.setVideoURI(Uri.parse(path));
                }
                System.out.println("replay------" + count++);
                return false;
            }
        });
    }

    @Override
    public void onViewUpdate(Object event) {

    }

    private boolean isShowToolbar = true;

    @OnClick({R.id.video_rl})
    void setOnclickEnvent(View view) {
        switch (view.getId()) {
            case R.id.video_rl:
                if (isShowToolbar) {
                    isShowToolbar = false;
//                    getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
//                    ivFullScreen.setBackgroundResource(R.drawable.bg_live_cancel_fullscreen_seletor);
//                    hideToolbar(lySystemParent);
                    hideFloor(lyFloor);
                } else {
                    isShowToolbar = true;
//                    getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
//                    ivFullScreen.setBackgroundResource(R.drawable.bg_live_cancel_fullscreen_seletor);
//                    showToolbar(lySystemParent);
                    showFloor(lyFloor);
                }
                break;
        }
    }

    public void hideToolbar(final View v) {
        ValueAnimator animator;
//        animator = ValueAnimator.ofFloat(0, -(DensityUtils.dp2px(this, 50) + ScreenUtils.getStatusBarHeight(this)));
        animator = ValueAnimator.ofFloat(0, -(DensityUtils.dp2px(this, 50)));
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void showToolbar(final View v) {
        ValueAnimator animator;
        if (Build.VERSION.SDK_INT >= 19) {
            animator = ValueAnimator.ofFloat(-(DensityUtils.dp2px(this, 50)), 0);
//            animator = ValueAnimator.ofFloat(-(DensityUtils.dp2px(this, 50) + ScreenUtils.getStatusHeight(this)), 0);
        } else {
            animator = ValueAnimator.ofFloat(-DensityUtils.dp2px(this, 50), 0);
        }
        animator.setTarget(v);
        animator.setDuration(500).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void hideFloor(final View v) {
        ValueAnimator animator;
        if (Build.VERSION.SDK_INT >= 19) {
//            animator = ValueAnimator.ofFloat(0, (DensityUtils.dp2px(this, 50) + ScreenUtils.getStatusHeight(this)));
            animator = ValueAnimator.ofFloat(0, (DensityUtils.dp2px(this, 50)));
        } else {
            animator = ValueAnimator.ofFloat(0, DensityUtils.dp2px(this, 50));
        }

        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    public void showFloor(final View v) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(DensityUtils.dp2px(this, 50), 0);
        animator.setTarget(v);
        animator.setDuration(400).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    @Override
    public Store initStore() {
        return null;
    }
}
