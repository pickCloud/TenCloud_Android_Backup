package com.ten.tencloud.module.user.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.module.user.adapter.RvTreeFilterItemOtherAdapter;
import com.ten.tencloud.module.user.adapter.RvTreeFilterItemServerAdapter;
import com.ten.tencloud.widget.dialog.PermissionFilterDialog;

import java.util.ArrayList;
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
    private RvTreeFilterItemServerAdapter mPermissionServerAdapter;
    private List<PermissionTreeNodeBean> mData;
    private String mType; //区分是文件 项目 服务器
    private RvTreeFilterItemOtherAdapter mPermissionOtherAdapter;

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
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mTvFilter.setVisibility("文件仓库".equals(mType) ? GONE : VISIBLE);
        handData();
    }

    private void handData() {

        if ("文件仓库".equals(mType) || "项目管理".equals(mType)) {
            mPermissionOtherAdapter = new RvTreeFilterItemOtherAdapter(mContext);
            mRvData.setAdapter(mPermissionOtherAdapter);
            List<PermissionTreeNodeBean> datas = new ArrayList<>();
            for (PermissionTreeNodeBean tags : mData) {
                datas.addAll(tags.getData());
            }
            mPermissionOtherAdapter.setDatas(datas);
        } else if ("云服务器".equals(mType)) {
            mPermissionServerAdapter = new RvTreeFilterItemServerAdapter(mContext);
            mRvData.setAdapter(mPermissionServerAdapter);
            List<PermissionTreeNodeBean> servers = new ArrayList<>();
            for (PermissionTreeNodeBean providers : mData) {
                for (PermissionTreeNodeBean areas : providers.getData()) {
                    servers.addAll(areas.getData());
                }
            }
            mPermissionServerAdapter.setDatas(servers);
        }
    }

    @OnClick({R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                if ("项目管理".equals(mType)) {
                    PermissionFilterDialog permissionFilterDialog = new PermissionFilterDialog(mContext);
                    permissionFilterDialog.show();
                    permissionFilterDialog.setData(mType, mData);
                } else if ("云服务器".equals(mType)) {
                    PermissionFilterDialog permissionFilterDialog = new PermissionFilterDialog(mContext);
                    permissionFilterDialog.show();
                    permissionFilterDialog.setData(mType, mData);
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

//        map.put("access_filehub", StringUtils.join(selectsFile, ","));
//        map.put("access_projects", StringUtils.join(selectsProject, ","));
//        map.put("access_servers", StringUtils.join(selectsServer, ","));
        return map;
    }
}
