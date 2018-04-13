package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.bean.ReposErrorBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.module.app.adapter.RvReposAdapter;
import com.ten.tencloud.module.app.contract.ReposListContract;
import com.ten.tencloud.module.app.presenter.ReposListPresenter;

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
    @BindView(R.id.web_view)
    WebView webView;

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
        mTvRight.setVisibility(View.GONE);

        mReposListPresenter = new ReposListPresenter();
        mReposListPresenter.attachView(this);

        initView();

        mReposListPresenter.getReposList("https://github.com/AIUnicorn");
//        mReposListPresenter.getReposList("http://cd.10.com/");
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

    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }

    @Override
    public void showReposList(List<ReposBean> reposBeans) {
        mTvRight.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mRvReposAdapter.setDatas(reposBeans);
    }

    //http://47.75.159.100 换成这个可以
    @Override
    public void showError(JesException e) {
        super.showError(e);
        mTvRight.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(e.getJson())) {
            KLog.e(e.getJson());
            ReposErrorBean reposErrorBean = TenApp.getInstance().getGsonInstance().fromJson(e.getJson(), ReposErrorBean.class);
            if (reposErrorBean.getData() != null && !TextUtils.isEmpty(reposErrorBean.getData().getUrl()))
                goWebView(reposErrorBean.getData().getUrl());
        }
    }

    private void goWebView(String url) {
        if (TextUtils.isEmpty(url)) return;

        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(url);

        WebSettings setting = webView.getSettings();
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                KLog.e(url);
                hideLoading();
                if (url.equals("https://github.com/AIUnicorn?token=true")) {
                    mReposListPresenter.getReposList("https://github.com/AIUnicorn");
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                changeTitle(title);
            }

        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        mReposListPresenter.getReposList("https://github.com/AIUnicorn");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReposListPresenter.detachView();
    }

}
