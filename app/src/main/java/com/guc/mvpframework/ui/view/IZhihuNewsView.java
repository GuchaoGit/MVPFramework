package com.guc.mvpframework.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.guc.mvpframework.widget.TopStoriesViewPager;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public interface IZhihuNewsView {
    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
    void setDataRefresh(Boolean refresh);

    TopStoriesViewPager getTopStoriesViewPager();
    TextView getTopTitleTextView();
}
