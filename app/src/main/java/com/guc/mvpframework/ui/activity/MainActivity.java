package com.guc.mvpframework.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.guc.mvpframework.R;
import com.guc.mvpframework.ui.base.BaseActivity;
import com.guc.mvpframework.ui.presenter.NewsPresenter;
import com.guc.mvpframework.ui.view.IZhihuNewsView;
import com.guc.mvpframework.widget.TopStoriesViewPager;

import butterknife.BindView;

public class MainActivity extends BaseActivity<IZhihuNewsView, NewsPresenter> implements IZhihuNewsView {

    @BindView(R.id.vp_top_stories)
    TopStoriesViewPager mVpTopStories;
    @BindView(R.id.tv_top_title)
    TextView mTvTopTitle;
    @BindView(R.id.tv_tag)
    TextView mTvTag;
    @BindView(R.id.content_list)
    RecyclerView mContentList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getLatestNews();
        mSwipeRefresh.setEnabled(false);
    }

    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mContentList;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public void setDataRefresh(Boolean refresh) {

    }

    @Override
    public TopStoriesViewPager getTopStoriesViewPager() {
        return mVpTopStories;
    }

    @Override
    public TextView getTopTitleTextView() {
        return mTvTopTitle;
    }
}
