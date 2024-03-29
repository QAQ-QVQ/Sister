package com.yu.drysister1.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yu.drysister1.Bean.Sister;
import com.yu.drysister1.R;
import com.yu.drysister1.Utils.SisterUtils;

import java.util.ArrayList;

import static com.yu.drysister1.Activity.MainActivity.posion;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.myViewHolder> {
    private Context mContext;
    private Sister sister;
    private boolean flag;

    // 事件回调监听
    private PageAdapter.OnItemClickListener onItemClickListener;
    public PageAdapter(Context Context,Sister sister){
        this.mContext = Context;
        this.sister = sister;
    }
    public PageAdapter(Context Context){
        this.mContext = Context;
    }

    //② 创建ViewHolder
    public static class myViewHolder extends RecyclerView.ViewHolder{
        public final ImageView context;
        public RelativeLayout loadingErr;
        public myViewHolder(View v) {
            super(v);
            context =  v.findViewById(R.id.imageViewItem);
            loadingErr = v.findViewById(R.id.loading_err);
        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int position) {

        Glide.with(mContext).load(SisterUtils.sister.getResults().get(position).getUrl())
                .placeholder(R.drawable.icon)
                .listener(new RequestListener<Drawable>() {
                    //加载图片失败
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // TODO: 2019/6/25 移除加载失败的图片
//                        posion.add(position);
//                        SisterUtils.sister.getResults().remove(position);
                        flag = false;
                        return true;
                    }
                    //加载图片成功
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        flag = true;
                        return false;
                    }
                })
                .into(myViewHolder.context);

        //加载成功的点击
        myViewHolder.context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(position,flag);
                }
            }
        });
        myViewHolder.context.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemLongClick(position,flag);
                }
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return SisterUtils.sister.getResults().size();
    }

    // ① 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(int position,boolean flag);
        void onItemLongClick(int position,boolean flag);
    }

    // ② 定义一个设置点击监听器的方法
    public void setOnItemClickListener(PageAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
