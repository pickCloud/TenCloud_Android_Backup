package com.ten.tencloud.module.user.adapter;

import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;

/**
 * Created by lxq on 2017/12/26.
 */

public class FirstLevelNodeViewBinder extends BaseNodeViewBinder {

    TextView mTvName;

    public FirstLevelNodeViewBinder(View view) {
        super(view);
        mTvName = view.findViewById(R.id.tv_name);
    }

    @Override
    public int getLayoutId() {
        return R.layout.tree_permission_level_first;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        PermissionTreeNodeBean nodeBean = (PermissionTreeNodeBean) treeNode.getValue();
        mTvName.setText(nodeBean.getName());
    }

    @Override
    public int getToggleTriggerViewId() {
        return -1;//不许点击
    }
}
