package com.guc.mvpframework.api;

import com.guc.mvpframework.bean.NewsTimeLine;
import com.guc.mvpframework.bean.SplashImage;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by guc on 2018/9/5.
 * 描述：Api接口类
 */
public interface ZhihuApi {
    @GET("start-image/1080*1920")
    Observable<SplashImage> getSplashImage();

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();
}
