package com.lxs.bilibili_android_copy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.bean.VideoItemBean;
import com.lxs.bilibili_android_copy.ui.activity.BVideoDetailActivity;
import com.lxs.bilibili_android_copy.ui.activity.VideoDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoItemViewAdapter extends
        RecyclerView.Adapter<VideoItemViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<VideoItemBean> mDatas;
    private Context mContext;

    public VideoItemViewAdapter(Context context, List<VideoItemBean> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            ButterKnife.bind(this, arg0);
        }

        @BindView(R.id.video_image)
        ImageView video_image;

        @BindView(R.id.video_text)
        TextView video_text;

        @BindView(R.id.play_num)
        TextView play_num;

        @BindView(R.id.comment_num)
        TextView comment_num;

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.video_item,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoDetailActivity.class);
                intent.putExtra("imageUrl",mDatas.get(i).getImageUrl());
                mContext.startActivity(intent);
            }
        });
        viewHolder.comment_num.setText(mDatas.get(i).getCommentNum());
        viewHolder.play_num.setText(mDatas.get(i).getPlayNum());
        viewHolder.video_text.setText(mDatas.get(i).getTitle());
        Glide.with(mContext).load(mDatas.get(i).getImageUrl()).crossFade()
                .into(viewHolder.video_image);

    }

}
