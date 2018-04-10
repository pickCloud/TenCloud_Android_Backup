package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.module.app.adapter.RvReposAdapter;
import com.ten.tencloud.module.app.contract.ReposListContract;
import com.ten.tencloud.module.app.presenter.ReposListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class RepositoryActivity extends BaseActivity implements ReposListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    private RvReposAdapter mRvReposAdapter;
    private String mReposName;
    private String mReposUrl;
    private ReposListPresenter mReposListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.layout_refresh);

        final RefreshBroadCastHandler broadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.IMAGE_SOURCE_CHANGE_ACTION);
        initTitleBar(true, "绑定github代码仓库", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("url", mReposUrl);
                bundle.putString("name", mReposName);
                broadCastHandler.sendBroadCastWithData(bundle);
                finish();
            }
        });

        mReposListPresenter = new ReposListPresenter();
        mReposListPresenter.attachView(this);

        initView();
        initData();

        mReposListPresenter.getReposList("https://github.com/AIUnicorn");
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRvReposAdapter = new RvReposAdapter(this);
        mRecyclerView.setAdapter(mRvReposAdapter);
        mRvReposAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ReposBean>() {
            @Override
            public void onObjectItemClicked(ReposBean reposBean, int position) {
                mRvReposAdapter.setSelectPos(position);
                mReposName = reposBean.getRepos_name();
                mReposUrl = reposBean.getRepos_url();
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }

    private void initData() {
        ArrayList<ReposBean> beans = new ArrayList<>();
        beans.add(new ReposBean("不绑定", null));
        for (int i = 0; i < 5; i++) {
            beans.add(new ReposBean("phyhhon-" + i, "https://github.com/AIUnicorn/TenCloud_Android" + i));
        }
        mRvReposAdapter.setDatas(beans);
    }


    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showReposList(List<ReposBean> reposBeans) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReposListPresenter.detachView();
    }
}
