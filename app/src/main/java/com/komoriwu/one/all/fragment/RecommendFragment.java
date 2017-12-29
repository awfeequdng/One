package com.komoriwu.one.all.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.komoriwu.one.R;
import com.komoriwu.one.all.detail.VideoCardActivity;
import com.komoriwu.one.all.fragment.adapter.RecommendAdapter;
import com.komoriwu.one.all.fragment.mvp.RecommendPresenter;
import com.komoriwu.one.all.listener.OnItemClickListener;
import com.komoriwu.one.model.bean.FindBean;
import com.komoriwu.one.model.bean.ItemListBean;
import com.komoriwu.one.utils.Constants;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.HashMap;

/**
 * Created by KomoriWu
 * on 2017-12-28.
 */

public class RecommendFragment extends CommonBaseFragment<RecommendPresenter> implements
        OnItemClickListener {
    private RecommendAdapter mRecommendAdapter;
    private String mPage;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initRefreshLayout() {
        super.initRefreshLayout();
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                presenter.loadList();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                HashMap<String, String> stringHashMap = new HashMap<>();
                stringHashMap.put(Constants.PAGE, mPage);
                presenter.loadMoreList(stringHashMap);
            }
        });
    }

    public void initRecyclerView() {
        super.initRecyclerView();
        mRecommendAdapter = new RecommendAdapter(getActivity());
        recyclerView.setAdapter(mRecommendAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mRecommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void refreshData(FindBean findBean) {
        super.refreshData(findBean);
        mRecommendAdapter.refreshList(findBean.getItemList());
        if (isLoadMore) {
            mPage = findBean.getNextPageUrl().split("=")[1];
        }
    }

    @Override
    public void showMoreDate(FindBean findBean) {
        super.showMoreDate(findBean);
        mRecommendAdapter.addItemListBeanXES(findBean.getItemList());
        if (isLoadMore) {
            mPage = findBean.getNextPageUrl().split("=")[1];
        }
    }

    @Override
    public void onVideoCardItemClick(ItemListBean itemListBeanX) {
        Intent intent = new Intent(getActivity(), VideoCardActivity.class);
        intent.putExtra(Constants.ITEM_LIST_BEAN_X, itemListBeanX);
        startActivity(intent);
    }

    @Override
    public int currentItem() {
        return 1;
    }
}