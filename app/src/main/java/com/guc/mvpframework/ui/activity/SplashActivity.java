package com.guc.mvpframework.ui.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guc.mvpframework.BuildConfig;
import com.guc.mvpframework.R;
import com.guc.mvpframework.ui.base.BaseActivity;
import com.guc.mvpframework.ui.base.BasePresenter;
import com.guc.mvpframework.widget.SplashView;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_view)
    SplashView mSplashView;
    @BindView(R.id.tv_splash_info)
    TextView mTvSplashInfo;
    @BindView(R.id.main_view)
    RelativeLayout mMainView;
    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();

        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/rm_albion.ttf");//根据路径得到Typeface
        mTvSplashInfo.setTypeface(tf);//设置字体
        startLoadingData();
    }

    private void startLoadingData() {
        // finish "loading data" in a random time between 1 and 3 seconds
        Random random = new Random();
        mHandler.postDelayed(this::onLoadingDataEnded, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded() {
        // start the splash animation
        mSplashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
                // log the animation start event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction) {
                // log animation update events
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd() {
                // log the animation end event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                mSplashView = null;
                goToMain();
            }
        });
    }

    public void goToMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, 0);
    }
}
