package com.lxs.bilibili_android_copy.store;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxs.bilibili_android_copy.ApiService.Api;
import com.lxs.bilibili_android_copy.action.MainFragmentAction;
import com.lxs.bilibili_android_copy.base.Action;
import com.lxs.bilibili_android_copy.base.Store;
import com.lxs.bilibili_android_copy.bean.AdLiveUrls;
import com.lxs.bilibili_android_copy.bean.Result;
import com.lxs.bilibili_android_copy.bean.VideoItemBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainFragmentStore extends Store {

    private List<String> urls = new ArrayList<>();
    private List<VideoItemBean> videoItemBeens = new ArrayList<>();
    private Gson gson = new Gson();

    @Override
    public StoreChangeEvent changeEvent(String operationType) {
        return new MainFragmentStoreEvent(operationType);
    }

    @Override
    public void onAction(Action action) {
        String operationType = action.getType();
        switch (operationType) {
            case MainFragmentAction.ACTION_AD_IMAGES:
                getNetLooperImages(operationType);
                break;
            case MainFragmentAction.VIDEO_ITEMS:
                getVideoItems(operationType);
                break;
            default:
        }
    }


    public List<String> getAdUrls() {
        return urls;
    }

    private void setAdUrls(List<AdLiveUrls> u) {
        if (u != null) {
            urls.clear();
            for (AdLiveUrls url : u) {
                urls.add(url.getUrl());
            }
        }
    }

    public List<VideoItemBean> getVideoItemBeens() {
        return videoItemBeens;
    }

    private void setVideoItemBeens(List<VideoItemBean> videoItemBeens) {
        this.videoItemBeens = videoItemBeens;
    }

    public void getNetLooperImages(final String operationType) {
        Api.createApi().getLooperImageUrls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result>() {
                    @Override
                    public void call(Result result) {
                        setAdUrls(transfromBean(result));
                        emitStoreChange(operationType);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("PPP", "------" + throwable.toString());
                    }
                });

    }

    public void getVideoItems(final String operationType) {
        videoItemBeens.clear();
        for (int i = 0; i < 4; i++) {
            VideoItemBean bean = new VideoItemBean();
            bean.setImageUrl("http://i1.hdslb.com/bfs/archive/3ca769a84ccd1ec35b551f0c117e7a1e593db8d4.jpg_160x100.jpg");
            bean.setPlayNum("2.8万");
            bean.setCommentNum("19万");
            bean.setTitle("【mint*(薄荷P)】恋爱少女成为一只小猫咪【鏡音鈴】");
            bean.setUrl("http://main.gslb.ku6.com/s1/NdL_edHRFRkOUcWdoiShYQ../1463033995225/727783ab2de42503d15106da03141de4/1470638758806/v282/95/53/912dc523552167ea0a464f1ae654b395-f4v-h264-aac-1210-32-90800.0-14178929-1463033625694-eb069deb7db6bf1ad2fbb5cc5b2c8654-1-00-00-00.f4v?rate=450");
            videoItemBeens.add(bean);
        }

        setVideoItemBeens(videoItemBeens);
        emitStoreChange(operationType);
    }

    private List<AdLiveUrls> transfromBean(Result result) {
        String jsonString = gson.toJson(result.getResults());
        Type type = new TypeToken<List<AdLiveUrls>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public class MainFragmentStoreEvent extends StoreChangeEvent {

        public MainFragmentStoreEvent(String operationType) {
            super(operationType);
        }
    }
}
