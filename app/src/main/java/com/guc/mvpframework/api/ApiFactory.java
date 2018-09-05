package com.guc.mvpframework.api;

/**
 * Created by guc on 2018/9/5.
 * 描述：获取api实例（单例）
 */
public class ApiFactory {
    protected static final Object monitor = new Object();
    static ZhihuApi zhihuApiSingleton = null;

    public static ZhihuApi getZhihuApiSingleton() {
        synchronized (monitor) {
            if (zhihuApiSingleton == null) {
                zhihuApiSingleton = new ApiRetrofit().getZhihuApiService();
            }
            return zhihuApiSingleton;
        }
    }
}
