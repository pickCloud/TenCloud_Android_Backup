package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.adapter.RvAppReposAdapter;
import com.ten.tencloud.module.app.contract.AppReposListContract;
import com.ten.tencloud.module.app.presenter.AppReposListPresenter;
import com.ten.tencloud.utils.Utils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppRepositoryActivity extends BaseActivity implements AppReposListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    private RvAppReposAdapter mRvAppReposAdapter;
    private String mReposName;
    private String mReposUrl;
    private String mReposHttpUrl;
    private AppReposListPresenter mAppReposListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.layout_refresh);

        final RefreshBroadCastHandler broadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.IMAGE_SOURCE_CHANGE_ACTION);
        initTitleBar(true, "绑定github代码仓库", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ObjectUtils.isEmpty(mReposUrl)){
                    mAppReposListPresenter.githubClear();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", mReposUrl);
                bundle.putString("name", mReposName);
                bundle.putString("httpUrl", mReposHttpUrl);
                broadCastHandler.sendBroadCastWithData(bundle);
                finish();
            }
        });
        mTvRight.setVisibility(View.GONE);
        mAppReposListPresenter = new AppReposListPresenter();
        mAppReposListPresenter.attachView(this);

        initView();

        mAppReposListPresenter.getReposList("tencloud://repository");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAppReposListPresenter.getReposList("tencloud://repository");
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRvAppReposAdapter = new RvAppReposAdapter(this);
        mRecyclerView.setAdapter(mRvAppReposAdapter);
        mRvAppReposAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ReposBean>() {
            @Override
            public void onObjectItemClicked(ReposBean reposBean, int position) {

                mRvAppReposAdapter.setSelectPos(position);
                mReposName = reposBean.getRepos_name();
                mReposUrl = reposBean.getRepos_url();
                mReposHttpUrl = reposBean.getHttp_url();
            }
        });

    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showReposList(List<ReposBean> reposBeans) {
        reposBeans.add(0, new ReposBean("不绑定", ""));
        mTvRight.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mRvAppReposAdapter.setDatas(reposBeans);
    }

    @Override
    public void goAuth(String url) {
        mTvRight.setVisibility(View.GONE);
        Intent intent = new Intent(mContext, WebviewActivity.class);
        intent.putExtra(IntentKey.URL, url);
        startActivity(intent);
//        Utils.openInBrowser(this, url);
    }

    @Override
    public void githubClearSuc() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppReposListPresenter.detachView();
    }

}
