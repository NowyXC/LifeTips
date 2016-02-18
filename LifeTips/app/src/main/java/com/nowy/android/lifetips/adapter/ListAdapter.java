package com.nowy.android.lifetips.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nowy.android.basiclib.utils.Tips;
import com.nowy.android.lifetips.R;
import com.nowy.android.lifetips.ui.listener.RecyclerViewItemListener;

import java.util.List;

/**
 * Created by Nowy on 2016/2/13.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas;
    public ListAdapter(List<String> datas){
        this.datas = datas;
    }
    private RecyclerViewItemListener mRecyclerViewItemListener;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tab_list_item, parent, false);

        return new MyViewHolder(itemView,mRecyclerViewItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder{
        private RecyclerViewItemListener mRecyclerViewItemListener;
        private TextView tvPraise,tvCollect;
        public MyViewHolder(View itemView,RecyclerViewItemListener mRecyclerViewItemListener) {
            super(itemView);
            this.mRecyclerViewItemListener = mRecyclerViewItemListener;
            tvPraise = (TextView) itemView.findViewById(R.id.tvPraise);
            tvCollect = (TextView) itemView.findViewById(R.id.tvCollect);


            tvCollect.setOnClickListener(clickListener);
            tvPraise.setOnClickListener(clickListener);
            itemView.setOnClickListener(clickListener);

        }



        OnClickListener  clickListener = new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v == itemView){
                    if(mRecyclerViewItemListener!=null)
                        mRecyclerViewItemListener.onItemClickListener(v,getAdapterPosition());
                }else{
                    switch (v.getId()){
                        case R.id.tvPraise:
                            Tips.showShort(itemView.getContext(),"点赞");
                            break;
                        case R.id.tvCollect:
                            Tips.showShort(itemView.getContext(),"收藏");
                            break;
                    }
                }

            }
        };
    }


    public void setRecyclerViewItemListener(RecyclerViewItemListener mRecyclerViewItemListener) {
        this.mRecyclerViewItemListener = mRecyclerViewItemListener;
    }
}
