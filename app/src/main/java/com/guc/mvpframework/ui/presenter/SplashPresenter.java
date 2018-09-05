package com.guc.mvpframework.ui.presenter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guc.mvpframework.R;
import com.guc.mvpframework.bean.SplashImage;
import com.guc.mvpframework.ui.base.BasePresenter;
import com.guc.mvpframework.ui.view.ISplashView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class SplashPresenter extends BasePresenter<ISplashView> {
    private Context context;
    private ISplashView splashView;
    private ImageView coverImg;
    public SplashPresenter(Context context) {
        this.context = context;
    }

    public void getSplashImage() {
        splashView = getView();
        if (splashView != null) {
            coverImg = splashView.getCoverImg();

            zhihuApi.getSplashImage()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(splashImage -> {
                        disPlayImage(splashImage,coverImg);
                    },this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    private void disPlayImage(SplashImage splashImage, ImageView iv){
        Glide.with(context).load(splashImage.getImg()).centerCrop().into(iv);
    }

    public void destroyImg(){
        Glide.clear(coverImg);
    }
}
