package com.guc.mvpframework.ui.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.guc.mvpframework.R;
import com.guc.mvpframework.bean.NewsTimeLine;
import com.guc.mvpframework.bean.TopStories;
import com.guc.mvpframework.ui.adapter.AdapterStories;
import com.guc.mvpframework.ui.base.BasePresenter;
import com.guc.mvpframework.ui.view.IZhihuNewsView;
import com.guc.mvpframework.widget.TopStoriesViewPager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class NewsPresenter extends BasePresenter <IZhihuNewsView>{
    private Context context;
    private IZhihuNewsView mNewsView;
    private TopStoriesViewPager mSVPTopStories;
    private TextView mTvTopTitle;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private NewsTimeLine timeLine;

    private AdapterStories mStoriesAdapter;

    public NewsPresenter(Context context) {
        this.context = context;
    }

    public void getLatestNews() {
        mNewsView = getView();
        if (mNewsView != null) {
            mRecyclerView = mNewsView.getRecyclerView();
            layoutManager = mNewsView.getLayoutManager();
            mRecyclerView.setLayoutManager(layoutManager);
            mTvTopTitle = mNewsView.getTopTitleTextView();
            mSVPTopStories = mNewsView.getTopStoriesViewPager();
            zhihuApi.getLatestNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine, context, mNewsView, mRecyclerView);
                    },this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
    String time;
    private boolean isLoadMore = false; // 是否加载过更多
    private void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context, IZhihuNewsView iZhihuNewsView, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (time == null) {
                return;
            }
            else {
                timeLine.getStories().addAll(newsTimeLine.getStories());
            }
            mSVPTopStories.init(timeLine.getTop_stories(), mTvTopTitle, new TopStoriesViewPager.ViewPagerClickListenner() {
                @Override
                public void onClick(TopStories item) {

                }
            });
//            adapter.notifyDataSetChanged();
        } else {
            timeLine = newsTimeLine;
            mSVPTopStories.init(timeLine.getTop_stories(), mTvTopTitle, new TopStoriesViewPager.ViewPagerClickListenner() {
                @Override
                public void onClick(TopStories item) {

                }
            });
            mStoriesAdapter = new AdapterStories(context, timeLine.getStories());
            recyclerView.setAdapter(mStoriesAdapter);
            mStoriesAdapter.notifyDataSetChanged();
        }
        iZhihuNewsView.setDataRefresh(false);
        time = newsTimeLine.getDate();
    }
}
