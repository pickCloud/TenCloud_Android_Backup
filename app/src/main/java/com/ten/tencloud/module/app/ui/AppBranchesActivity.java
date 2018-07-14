package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.adapter.RvAppBranchAdapter;
import com.ten.tencloud.module.app.contract.AppBranchListContract;
import com.ten.tencloud.module.app.presenter.AppBranchListPresenter;
import com.ten.tencloud.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class AppBranchesActivity extends BaseActivity implements AppBranchListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

    private AppBranchListPresenter mAppBranchListPresenter;
    private String mReposName;
    private String mBranchName;
    private RvAppBranchAdapter mAppBranchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.layout_refresh);

        final RefreshBroadCastHandler broadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_BRANCH_CHANGE_ACTION);
        initTitleBar(true, "选择代码分支", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBranchName)) {
                    showMessage("请选择分支");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("branchName", mBranchName);
                broadCastHandler.sendBroadCastWithData(bundle);
                finish();
            }
        });
        mTvRight.setVisibility(View.GONE);

        mReposName = getIntent().getStringExtra("reposName");

        mAppBranchListPresenter = new AppBranchListPresenter();
        mAppBranchListPresenter.attachView(this);

        initView();

        mAppBranchListPresenter.getBranchList(mReposName, "tencloud://branches");
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAppBranchAdapter = new RvAppBranchAdapter(this);
        mRecyclerView.setAdapter(mAppBranchAdapter);
        mAppBranchAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<String>() {
            @Override
            public void onObjectItemClicked(String branchName, int position) {
                mAppBranchAdapter.setSelectPos(position);
                mBranchName = branchName;
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAppBranchListPresenter.getBranchList(mReposName, "tencloud://branches");
    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showBranchList(List<String> reposBeans) {
        mTvRight.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mAppBranchAdapter.setDatas(reposBeans);
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
    protected void onDestroy() {
        super.onDestroy();
        mAppBranchListPresenter.detachView();
    }
}
