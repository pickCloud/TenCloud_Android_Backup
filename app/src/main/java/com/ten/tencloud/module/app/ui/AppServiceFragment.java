package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceAdapter;
import com.ten.tencloud.module.app.contract.AppLabelSelectContract;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;
import com.ten.tencloud.module.app.presenter.AppLabelSelectPresenter;
import com.ten.tencloud.module.app.presenter.AppListPresenter;
import com.ten.tencloud.module.app.presenter.AppServiceHomePresenter;
import com.ten.tencloud.widget.decoration.Hor16Ver8ItemDecoration;
import com.ten.tencloud.widget.decoration.ServiceItemDecoration;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceFragment extends BaseFragment implements AppListContract.View, AppLabelSelectContract.View {

    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private RefreshBroadCastHandler mAppHandler;
    private ArrayList<LabelBean> mLabelBeans;
    private RvAppAdapter mAppAdapter;

    private AppFilterDialog mAppFilterDialog;
    private AppListPresenter mAppListPresenter;
    private AppLabelSelectPresenter mAppLabelSelectPresenter;
    private Integer label = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, R.layout.activity_app_service_app_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppListPresenter = new AppListPresenter();
        mAppListPresenter.attachView(this);

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAppListPresenter.getAppListByPage(false, label);
            }
        });

        mAppLabelSelectPresenter = new AppLabelSelectPresenter();
        mAppLabelSelectPresenter.attachView(this);

        initView();

    }

    private void initView() {
        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAppListPresenter.getAppListByPage(false, label);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mAppListPresenter.getAppListByPage(true, label);

            }
        });
        mRefresh.autoRefresh();

        mRvApp.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAppAdapter = new RvAppAdapter(getActivity());
        mAppAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<AppBean>() {
            @Override
            public void onObjectItemClicked(AppBean appBean, int position) {
//                startActivity(new Intent(getActivity(), AppDetailActivity.class).putExtra("id", appBean.getId()));
                startActivity(new Intent(getActivity(), AppMainPageDetailsActivity.class).putExtra("id", appBean.getId()));
            }
        });
        mRvApp.setAdapter(mAppAdapter);

        mAppFilterDialog = new AppFilterDialog(getActivity());
        mAppFilterDialog.setAppFilterListener(new AppFilterDialog.AppFilterListener() {
            @Override
            public void getFilterData() {

            }

            @Override
            public void onOkClick(Integer id) {
                label = id;
                mRefresh.autoRefresh();

            }

        });
    }

    @OnClick({R.id.tv_filter, R.id.empty_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                if (ObjectUtils.isEmpty(mLabelBeans)){
                    mAppLabelSelectPresenter.getLabelList(1);

                }
                else {
                    List<ProviderBean> providerBeans = new ArrayList<>();
                    for (LabelBean labelBean:mLabelBeans){
                        ProviderBean bean = new ProviderBean();
                        bean.setProvider(labelBean.getName());
                        bean.setId(labelBean.getId());
                        providerBeans.add(bean);
                    }
                    mAppFilterDialog.show();
                    mAppFilterDialog.setData(providerBeans);
                }

                break;
            case R.id.empty_view:
                ActivityUtils.startActivity(AppAddActivity.class);
                break;
        }
    }

    @Override
    public void showEmpty(boolean isLoadMore) {
        if (isLoadMore) {
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();
        } else {
            mAppAdapter.clear();
            mRefresh.finishRefresh();
            mEmptyView.setVisibility(View.VISIBLE);
            mRvApp.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAppList(List<AppBean> msg, boolean isLoadMore) {
        mEmptyView.setVisibility(View.GONE);
        mRvApp.setVisibility(View.VISIBLE);
        if (isLoadMore) {
            mAppAdapter.addData(msg);
            mRefresh.finishLoadmore();
        } else {
            mAppAdapter.setDatas(msg);
            mRefresh.finishRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAppHandler.unregisterReceiver();
        mAppHandler = null;
        mAppListPresenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLabelList(TreeSet<LabelBean> labelBeans) {
        List<ProviderBean> providerBeans = new ArrayList<>();
        for (LabelBean labelBean:labelBeans){
            ProviderBean bean = new ProviderBean();
            bean.setProvider(labelBean.getName());
            bean.setId(labelBean.getId());
            providerBeans.add(bean);
        }
        mAppFilterDialog.show();
        mAppFilterDialog.setData(providerBeans);
    }

    @Override
    public void labelAddResult(LabelBean bean) {

    }
}
