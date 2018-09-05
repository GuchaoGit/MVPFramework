package com.guc.mvpframework.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guc.mvpframework.R;
import com.guc.mvpframework.bean.Stories;
import com.guc.mvpframework.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guc on 2018/9/5.
 * 描述：
 */
public class AdapterStories extends RecyclerView.Adapter {
    private  Context context;
    private List<Stories> mDataList;

    public AdapterStories(Context context,List<Stories> mDataList){
        this.context = context;
        this.mDataList = mDataList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = View.inflate(parent.getContext(),R.layout.item_zhihu_stories,null);
        return new StoriesViewHolder(rootView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StoriesViewHolder storiesViewHolder = (StoriesViewHolder) holder;
        storiesViewHolder.bindItem(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList==null?0:mDataList.size();
    }

    /**
     * Stories
     */
    class StoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_stories)
        CardView card_stories;
        @BindView(R.id.tv_stories_title)
        TextView tv_stories_title;
        @BindView(R.id.iv_stories_img)
        ImageView iv_stories_img;

        public StoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ScreenUtil screenUtil = ScreenUtil.instance(context);
            int screenWidth = screenUtil.getScreenWidth();
            card_stories.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        }

        public void bindItem(Stories stories) {
            tv_stories_title.setText(stories.getTitle());
            String[] images = stories.getImages();
            Glide.with(context).load(images[0]).centerCrop().into(iv_stories_img);
        }
    }
}
