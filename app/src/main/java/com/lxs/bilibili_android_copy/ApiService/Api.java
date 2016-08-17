package com.lxs.bilibili_android_copy.ApiService;

import com.lxs.bilibili_android_copy.App;
import com.lxs.bilibili_android_copy.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public Api() {
    }

    public static IApi createApi() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(genericClient())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(IApi.class);
    }

    public static OkHttpClient genericClient() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
