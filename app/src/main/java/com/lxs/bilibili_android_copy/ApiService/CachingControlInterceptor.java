package com.lxs.bilibili_android_copy.ApiService;

import com.lxs.bilibili_android_copy.App;
import com.lxs.bilibili_android_copy.BuildConfig;
import com.lxs.bilibili_android_copy.utils.NetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CachingControlInterceptor {
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7;

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("X-Bmob-Application-Id", BuildConfig.BMOBAPPLICATIONID)
                    .addHeader("X-Bmob-REST-API-Key", BuildConfig.BMOBRESTAPIKEY)
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(request);
        }
    };


    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isConnected(App.getContext())) {
                request = chain.request();
                if (!NetUtils.isConnected(App.getContext())) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtils.isConnected(App.getContext())) {
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                            .addHeader("X-Bmob-Application-Id", BuildConfig.BMOBAPPLICATIONID)
                            .addHeader("X-Bmob-REST-API-Key", BuildConfig.BMOBRESTAPIKEY)
                            .addHeader("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder().header("Cache-Control",
                            "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT).removeHeader("Pragma").build();
                }
            }
            return chain.proceed(request);
        }
    };


    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!NetUtils.isConnected(App.getContext())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isConnected(App.getContext())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .addHeader("X-Bmob-Application-Id", BuildConfig.BMOBAPPLICATIONID)
                        .addHeader("X-Bmob-REST-API-Key", BuildConfig.BMOBRESTAPIKEY)
                        .addHeader("Content-Type", "application/json")
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .addHeader("X-Bmob-Application-Id", BuildConfig.BMOBAPPLICATIONID)
                        .addHeader("X-Bmob-REST-API-Key", BuildConfig.BMOBRESTAPIKEY)
                        .addHeader("Content-Type", "application/json")
                        .build();
            }
        }
    };
}