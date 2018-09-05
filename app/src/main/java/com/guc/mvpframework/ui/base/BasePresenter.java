package com.guc.mvpframework.ui.base;

import com.guc.mvpframework.api.ApiFactory;
import com.guc.mvpframework.api.ZhihuApi;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public abstract class BasePresenter<V> {
    protected Reference<V> mViewRef;
    public static final ZhihuApi zhihuApi = ApiFactory.getZhihuApiSingleton();

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
