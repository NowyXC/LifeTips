package com.nowy.android.lifetips.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nowy.android.basiclib.base.BaseFragment;
import com.nowy.android.basiclib.utils.Tips;
import com.nowy.android.lifetips.R;
import com.nowy.android.lifetips.adapter.ListAdapter;
import com.nowy.android.lifetips.ui.DetailActivity;
import com.nowy.android.lifetips.ui.listener.RecyclerViewItemListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nowy on 2016/2/4.
 */
public class TabFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static TabFragment newInstance(Bundle bundle){
        TabFragment tabFragment = new TabFragment();
        if(bundle!=null){
            tabFragment.setArguments(bundle);
        }

        return tabFragment;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.tab_list,container,false);
        initViews(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpWindowTrisience();
        initData(getArguments());
    }

    private void initViews(View view){
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(scrollListener);

    }



    private void initData(Bundle bundle){
        if(bundle!=null){

        }else {

        }

        List<String> datas = new ArrayList<>();
        for(int i=0;i<50;i++){
            datas.add("数据"+i);
        }

        ListAdapter adapter = new ListAdapter(datas);
        mRecyclerView.setAdapter(adapter);
        adapter.setRecyclerViewItemListener(mRecyclerViewItemListener);

    }



    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 5000);
        }
    };


    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener(){
        boolean isSildeToLast = false ;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                //获取最后一个完全显示的item
                int lastVisiableItem = layoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if(lastVisiableItem == (totalItemCount -1) && isSildeToLast){
                    Tips.showShort(getActivity(),"加载更多");
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //dy纵向滑动
            if(dy>0){
                isSildeToLast = true;
            }else{
                isSildeToLast = false;
            }

        }
    };


    private void setUpWindowTrisience() {
        TransitionSet mtransitionset=new TransitionSet();
        mtransitionset.addTransition(new ChangeBounds());
        mtransitionset.addTransition(new ChangeImageTransform());
//        mtransitionset.addTransition(new Fade());
        mtransitionset.setDuration(250);
        getActivity().getWindow().setEnterTransition(mtransitionset);
        getActivity().getWindow().setExitTransition(mtransitionset);
        getActivity().getWindow().setSharedElementEnterTransition(mtransitionset);
        getActivity().getWindow().setSharedElementExitTransition(mtransitionset);
        getActivity().getWindow().setAllowEnterTransitionOverlap(true);

    }

    RecyclerViewItemListener mRecyclerViewItemListener = new RecyclerViewItemListener(){
        @Override
        public void onItemClickListener(View view, int position) {
            Tips.showShort(getActivity(), "点击了第" + position + "个");
            //getActivity().getWindow().setExitTransition(new Slide());
            Intent intent =new Intent(getActivity(), DetailActivity.class);
            View shareView = view.findViewById(R.id.ivImg);
//            getFragmentManager().beginTransaction()
//                    .addSharedElement(shareView,"image").commit();
        // 这里指定了共享的视图元素
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), shareView, "image");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            //startActivity(intent);
        }
    };


}
