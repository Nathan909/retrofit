package com.example.administrator.retrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bean.DataInfo;

/**
 * Created by Administrator on 2016/6/25.
 */
public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<DataInfo.Info> list;

    public MyAdapter(Context context, ArrayList<DataInfo.Info> list) {
        this.context = context;
        this.list = list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (DataInfo.Info) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, DataInfo.Info data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        ViewHodler vh = new ViewHodler(view);
        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return vh;
    }

    /**
     * 持有者绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHodler mHodler = (ViewHodler) holder;

        mHodler.textview.setText("this is item " + position);
        Glide.with(context)//引入Glide图载框架
                .load(list.get(position).url)
                .centerCrop()
                .placeholder(R.color.app_primary_color)//占位图
                .crossFade()//淡入淡出动画
                .into(mHodler.imageView);
        Log.i("aaaa", list.get(position)._id);
        mHodler.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        private TextView textview;
        private ImageView imageView;

        public ViewHodler(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.textview);
            imageView = (ImageView) itemView.findViewById(R.id.image);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context,"click"+getPosition(),Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
