package com.lxs.bilibili_android_copy.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lxs.bilibili_android_copy.IjkPlayer.media.IjkVideoView;
import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.base.BaseActivity;
import com.lxs.bilibili_android_copy.base.Store;
import com.lxs.bilibili_android_copy.utils.ScreenUtils;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class BVideoDetailActivity extends BaseActivity {
    @BindView(R.id.video_view)
    IjkVideoView videoView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int count = 0;

    private final int CONNECTION_TIMES = 5;

    @Override
    public int bindLayout() {
        return R.layout.activity_bl_video_detail;
    }

    @Override
    public void doBusiness(Context mContext) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
//        toolbar.setLayoutParams(params);

//        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//        param.setMargins(0, ScreenUtils.getStatusBarHeight(this), 0, 0);
//        toolbar.setLayoutParams(params);

//        setSupportActionBar(toolbar);

        initVideoView();
    }

    private void initVideoView() {
        final String path = "http://live.gslb.letv.com/gslb?stream_id=lb_hkmovie_1300&tag=live&ext=m3u8&sign=live_tv&platid=10&splatid=1009&format=letv&expect=1";
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView.setVideoURI(Uri.parse(path));

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                videoView.start();
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
                    new AlertDialog.Builder(BVideoDetailActivity.this)
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
                    videoView.setVideoURI(Uri.parse(path));
                }
                count++;
                return false;
            }
        });
    }

    @Override
    public void onViewUpdate(Object event) {

    }

    @Override
    public Store initStore() {
        return null;
    }
}
