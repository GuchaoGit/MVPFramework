package com.guc.mvpframework.api;

import com.guc.mvpframework.bean.NewsTimeLine;
import com.guc.mvpframework.bean.SplashImage;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by guc on 2018/9/5.
 * 描述：Api接口类
 */
public interface ZhihuApi {
    @GET("start-image/1080*1920")
    Observable<SplashImage> getSplashImage();

    //    @POST("news/latest")
//    @FormUrlEncoded()
//    Observable<NewsTimeLine> getLatestNews(@FieldMap Map<String,String> body);
    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews(@QueryMap Map<String, String> body);
}
