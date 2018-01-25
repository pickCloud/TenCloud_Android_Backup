package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qiniu.android.utils.StringUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.module.user.adapter.RvTreeFilterItemOtherAdapter;
import com.ten.tencloud.module.user.adapter.RvTreeFilterItemServerAdapter;
import com.ten.tencloud.widget.dialog.PermissionFilterDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/12/26.
 */

public class PermissionTreeFilterItemPager extends BasePager {

    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.empty_view)
    View mEmptyView;
    private RvTreeFilterItemServerAdapter mPermissionServerAdapter;
    private List<PermissionTreeNodeBean> mData;
    private String mType; //区分是文件 项目 服务器
    private RvTreeFilterItemOtherAdapter mPermissionOtherAdapter;
    private PermissionFilterDialog mOtherFilterDialog;
    private PermissionFilterDialog mServerFilterDialog;
    private Boolean isView;
    private PermissionTemplateBean mSelectData;

    public PermissionTreeFilterItemPager(Context context) {
        super(context);
        init();
    }

    @Override
    public void init() {
        createView(R.layout.pager_permission_tree_filter_item);
    }

    public void initView() {
        mData = getArgument("data");
        mType = getArgument("type");
        isView = getArgument("isView", false);
        mSelectData = getArgument("select");
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mTvFilter.setVisibility("文件仓库".equals(mType) ? GONE : VISIBLE);
        handData();
    }

    private void handData() {

        if ("文件仓库".equals(mType) || "项目管理".equals(mType)) {
            mPermissionOtherAdapter = new RvTreeFilterItemOtherAdapter(mContext, isView);
            mRvData.setAdapter(mPermissionOtherAdapter);
            List<PermissionTreeNodeBean> datas = new ArrayList<>();
            if (mData == null || mData.size() == 0
                    || mData.get(0).getData() == null
                    || mData.get(0).getData().size() == 0) {
                showEmptyView(true);
                return;
            }
            for (PermissionTreeNodeBean tags : mData) {
                datas.addAll(tags.getData());
            }

            if (isView && mSelectData != null && mSelectData.getId() != 0) {
                datas = filterDataForSelect(mType, datas);
            }

            mPermissionOtherAdapter.setDatas(datas);
        } else if ("云服务器".equals(mType)) {
            mPermissionServerAdapter = new RvTreeFilterItemServerAdapter(mContext, isView);
            mRvData.setAdapter(mPermissionServerAdapter);
            List<PermissionTreeNodeBean> servers = new ArrayList<>();
            if (mData == null || mData.size() == 0
                    || mData.get(0).getData() == null
                    || mData.get(0).getData().size() == 0) {
                showEmptyView(true);
                return;
            }
            for (PermissionTreeNodeBean providers : mData) {
                for (PermissionTreeNodeBean areas : providers.getData()) {
                    servers.addAll(areas.getData());
                }
            }
            if (isView && mSelectData != null && mSelectData.getId() != 0) {
                servers = filterDataForSelect(mType, servers);
            }
            mPermissionServerAdapter.setDatas(servers);
        }
    }

    private void showEmptyView(boolean isShow) {
        mEmptyView.setVisibility(isShow ? VISIBLE : INVISIBLE);
        mRvData.setVisibility(isShow ? INVISIBLE : VISIBLE);
    }


    /**
     * 根据查看时过滤数据
     *
     * @return
     */
    private List<PermissionTreeNodeBean> filterDataForSelect(String type, List<PermissionTreeNodeBean> originalData) {
        List<PermissionTreeNodeBean> newData = new ArrayList<>();
        if ("文件仓库".equals(type)) {
            String accessFilehub = mSelectData.getAccess_filehub();
            String[] split = accessFilehub.split(",");
            List<String> selects = Arrays.asList(split);
            for (PermissionTreeNodeBean originalDatum : originalData) {
                if (selects.contains(originalDatum.getId() + "")) {
                    newData.add(originalDatum);
                }
            }
        } else if ("项目管理".equals(type)) {
            String accessFilehub = mSelectData.getAccess_projects();
            String[] split = accessFilehub.split(",");
            List<String> selects = Arrays.asList(split);
            for (PermissionTreeNodeBean originalDatum : originalData) {
                if (selects.contains(originalDatum.getId() + "")) {
                    newData.add(originalDatum);
                }
            }
        } else if ("云服务器".equals(type)) {
            String accessFilehub = mSelectData.getAccess_servers();
            String[] split = accessFilehub.split(",");
            List<String> selects = Arrays.asList(split);
            for (PermissionTreeNodeBean originalDatum : originalData) {
                if (selects.contains(originalDatum.getSid() + "")) {
                    newData.add(originalDatum);
                }
            }
        }
        return newData;
    }

    @OnClick({R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                if ("项目管理".equals(mType)) {
                    if (mOtherFilterDialog == null) {
                        mOtherFilterDialog = new PermissionFilterDialog(mContext);
                        mOtherFilterDialog.setFilterListener(new PermissionFilterDialog.FilterListener() {

                            @Override
                            public void setFilterData() {
                                mOtherFilterDialog.setData(mType, mData);
                            }

                            @Override
                            public void onOkClick(List<PermissionTreeNodeBean> select) {
                                if (isView && mSelectData != null && mSelectData.getId() != 0) {
                                    select = filterDataForSelect(mType, select);
                                }
                                if (select == null || select.size() == 0) {
                                    showEmptyView(true);
                                    return;
                                }
                                showEmptyView(false);
                                mPermissionOtherAdapter.setDatas(select);
                            }
                        });
                    }
                    mOtherFilterDialog.show();

                } else if ("云服务器".equals(mType)) {
                    if (mServerFilterDialog == null) {
                        mServerFilterDialog = new PermissionFilterDialog(mContext);
                        mServerFilterDialog.setFilterListener(new PermissionFilterDialog.FilterListener() {
                            @Override
                            public void setFilterData() {
                                mServerFilterDialog.setData(mType, mData);
                            }

                            @Override
                            public void onOkClick(List<PermissionTreeNodeBean> select) {
                                if (isView && mSelectData != null && mSelectData.getId() != 0) {
                                    select = filterDataForSelect(mType, select);
                                }
                                if (select == null || select.size() == 0) {
                                    showEmptyView(true);
                                    return;
                                }
                                showEmptyView(false);
                                mPermissionServerAdapter.setDatas(select);
                            }
                        });
                    }
                    mServerFilterDialog.show();
                }
                break;
        }
    }

    /**
     * 获取选择的节点
     *
     * @return
     */
    public Map<String, String> getSelectNode() {
        Map<String, String> map = new HashMap<>();
        if ("文件仓库".equals(mType)) {
            List<Integer> selectPos = mPermissionOtherAdapter.getSelectPos();
            String[] selects = new String[selectPos.size()];
            for (int i = 0; i < selectPos.size(); i++) {
                selects[i] = selectPos.get(i) + "";
            }
            map.put("access_filehub", StringUtils.join(selects, ","));
        } else if ("项目管理".equals(mType)) {
            List<Integer> selectPos = mPermissionOtherAdapter.getSelectPos();
            String[] selects = new String[selectPos.size()];
            for (int i = 0; i < selectPos.size(); i++) {
                selects[i] = selectPos.get(i) + "";
            }
            map.put("access_projects", StringUtils.join(selects, ","));
        } else if ("云服务器".equals(mType)) {
            List<Integer> selectPos = mPermissionServerAdapter.getSelectPos();
            String[] selects = new String[selectPos.size()];
            for (int i = 0; i < selectPos.size(); i++) {
                selects[i] = selectPos.get(i) + "";
            }
            map.put("access_servers", StringUtils.join(selects, ","));
        }
        return map;
    }
}
