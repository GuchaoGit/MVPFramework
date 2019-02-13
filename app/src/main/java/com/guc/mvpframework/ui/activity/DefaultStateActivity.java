package com.guc.mvpframework.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guc.mvpframework.R;
import com.guc.mvpframework.ui.adapter.AdapterItem;
import com.guc.mvpframework.ui.base.BaseActivity;
import com.guc.mvpframework.ui.base.BasePresenter;
import com.guc.stateswitch.StateEmptyInterface;
import com.guc.stateswitch.StateErrorInterface;
import com.guc.stateswitch.StateLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.guc.stateswitch.StateLayout.State.CONTENT;
import static com.guc.stateswitch.StateLayout.State.EMPTY;
import static com.guc.stateswitch.StateLayout.State.ERROR;
import static com.guc.stateswitch.StateLayout.State.ING;

public class DefaultStateActivity extends BaseActivity {

    @BindView(R.id.state_layout)
    StateLayout mStateLayout;

    @BindView(R.id.content_list)
    RecyclerView mContentList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private AdapterItem mAdapter;
    private Handler mMainHandler;

    public static void startUp(Context context) {
        context.startActivity(new Intent(context, DefaultStateActivity.class));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_default_state;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainHandler = new Handler(Looper.getMainLooper());
        initView();
        initEvent();
    }

    private void initEvent() {
        mStateLayout.setEmptyStateRetryListener(new StateEmptyInterface.OnRetryListener() {
            @Override
            public void onRetry() {
                mSwipeRefresh.setRefreshing(true);
                refresh();
            }
        });

        mStateLayout.setErrorStateRetryListener(new StateErrorInterface.OnRetryListener() {
            @Override
            public void onRetry() {
                mSwipeRefresh.setRefreshing(true);
                refresh();
            }
        });

    }

    private void initView() {
        mContentList.setLayoutManager(new LinearLayoutManager(this));
        mContentList.setAdapter(mAdapter = new AdapterItem());
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
                refresh();
            }
        });
    }

    private void refresh(){
        mStateLayout.switchState(ING);
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
                Random random = new Random();
                int randNum = Math.abs(random.nextInt()) % 3;
                if (randNum == 0) {
                    //模拟获取数据失败
                    mStateLayout.switchState(EMPTY);
                } else if (randNum == 1) {
                    //模拟数据为空
                    mStateLayout.switchState(ERROR);
                } else {
                    //模拟获取数据成功
                    mStateLayout.switchState(CONTENT);
                    mAdapter.setData(obtainDataList());
                }
            }
        }, 1000);
    }
    private List<String> obtainDataList() {
        List<String> dataList = new ArrayList<>();
        for (int index = 0; index < 20; index++) {
            dataList.add("item " + index);
        }
        return dataList;
    }
}
