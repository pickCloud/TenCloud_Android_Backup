package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        //查看状态下不显示
        mTvFilter.setVisibility(!"文件仓库".equals(mType) && !isView ? VISIBLE : GONE);
        handData();
    }

    private void handData() {
        List<PermissionTreeNodeBean> datas = new ArrayList<>();
        if (mData == null || mData.size() == 0
                || mData.get(0).getData() == null
                || mData.get(0).getData().size() == 0) {
            showEmptyView(true);
            return;
        }
        for (PermissionTreeNodeBean tags : mData) {
            if ("云服务器".equals(mType)) {
                //两级
                for (PermissionTreeNodeBean areas : tags.getData()) {
                    datas.addAll(areas.getData());
                }
            } else {
                datas.addAll(tags.getData());
            }
        }
        //过滤数据
        if (isView && mSelectData != null && mSelectData.getId() != 0) {
            datas = filterDataForSelect(mType, datas);
        }
        if ("文件仓库".equals(mType) || "项目管理".equals(mType)) {
            mPermissionOtherAdapter = new RvTreeFilterItemOtherAdapter(mContext, isView);
            mRvData.setAdapter(mPermissionOtherAdapter);
            if (!isView && mSelectData != null) {
                mPermissionOtherAdapter.setSelectPos(setSelectData(mType, datas));
            }
            mPermissionOtherAdapter.setDatas(datas);
        } else if (("云服务器".equals(mType))) {
            mPermissionServerAdapter = new RvTreeFilterItemServerAdapter(mContext, isView);
            mRvData.setAdapter(mPermissionServerAdapter);
            if (!isView && mSelectData != null) {
                mPermissionServerAdapter.setSelectPos(setSelectData(mType, datas));
            }
            mPermissionServerAdapter.setDatas(datas);
        }
    }

    private void showEmptyView(boolean isShow) {
        mEmptyView.setVisibility(isShow ? VISIBLE : INVISIBLE);
        mRvData.setVisibility(isShow ? INVISIBLE : VISIBLE);
    }

    private List<Integer> setSelectData(String type, List<PermissionTreeNodeBean> originalData) {
        List<Integer> newData = new ArrayList<>();
        String selectStr = "";
        if ("文件仓库".equals(type)) {
            selectStr = mSelectData.getAccess_filehub();
        } else if ("项目管理".equals(type)) {
            selectStr = mSelectData.getAccess_projects();
        } else if ("云服务器".equals(type)) {
            selectStr = mSelectData.getAccess_servers();
        }
        if (TextUtils.isEmpty(selectStr)) {
            return newData;
        }
        String[] split = selectStr.split(",");
        List<String> selects = Arrays.asList(split);
        for (PermissionTreeNodeBean originalDatum : originalData) {
            int id = ("云服务器".equals(type) ? originalDatum.getSid() : originalDatum.getId());
            if (selects.contains(id + "")) {
                newData.add(id);
            }
        }
        return newData;
    }

    /**
     * 根据查看时过滤数据
     *
     * @return
     */
    private List<PermissionTreeNodeBean> filterDataForSelect(String type, List<PermissionTreeNodeBean> originalData) {
        List<PermissionTreeNodeBean> newData = new ArrayList<>();
        String selectStr = "";
        if ("文件仓库".equals(type)) {
            selectStr = mSelectData.getAccess_filehub();
        } else if ("项目管理".equals(type)) {
            selectStr = mSelectData.getAccess_projects();
        } else if ("云服务器".equals(type)) {
            selectStr = mSelectData.getAccess_servers();
        }
        if (TextUtils.isEmpty(selectStr)) {
            return newData;
        }
        String[] split = selectStr.split(",");
        List<String> selects = Arrays.asList(split);
        for (PermissionTreeNodeBean originalDatum : originalData) {
            int id = ("云服务器".equals(type) ? originalDatum.getSid() : originalDatum.getId());
            if (selects.contains(id + "")) {
                newData.add(originalDatum);
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
                                if (!isView && mSelectData != null) {
                                    mPermissionOtherAdapter.setSelectPos(setSelectData(mType, select));
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
                                if (!isView && mSelectData != null) {
                                    mPermissionServerAdapter.setSelectPos(setSelectData(mType, select));
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
        List<Integer> selectPos;
        if ("文件仓库".equals(mType)) {
            if (mPermissionOtherAdapter == null) {
                map.put("access_filehub", "");
            } else {
                selectPos = mPermissionOtherAdapter.getSelectPos();
                String[] selects = new String[selectPos.size()];
                for (int i = 0; i < selectPos.size(); i++) {
                    selects[i] = selectPos.get(i) + "";
                }
                map.put("access_filehub", StringUtils.join(selects, ","));
            }

        } else if ("项目管理".equals(mType)) {
            if (mPermissionOtherAdapter == null) {
                map.put("access_projects", "");
            } else {
                selectPos = mPermissionOtherAdapter.getSelectPos();
                String[] selects = new String[selectPos.size()];
                for (int i = 0; i < selectPos.size(); i++) {
                    selects[i] = selectPos.get(i) + "";
                }
                map.put("access_projects", StringUtils.join(selects, ","));
            }
        } else if ("云服务器".equals(mType)) {
            if (mPermissionServerAdapter == null) {
                map.put("access_servers", "");
            } else {
                selectPos = mPermissionServerAdapter.getSelectPos();
                String[] selects = new String[selectPos.size()];
                for (int i = 0; i < selectPos.size(); i++) {
                    selects[i] = selectPos.get(i) + "";
                }
                map.put("access_servers", StringUtils.join(selects, ","));
            }
        }
        return map;
    }
}
