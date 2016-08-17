package com.lxs.bilibili_android_copy.ApiService;

import com.lxs.bilibili_android_copy.bean.Result;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

public interface IApi {

    @GET("1/classes/ad_live_urls")
    Observable<Result> getLooperImageUrls();

}