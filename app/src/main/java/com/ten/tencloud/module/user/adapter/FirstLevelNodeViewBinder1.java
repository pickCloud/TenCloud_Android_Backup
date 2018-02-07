package com.ten.tencloud.module.user.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by lxq on 2017/12/26.
 */

public class FirstLevelNodeViewBinder1 extends CheckableNodeViewBinder {

    private CheckBox mCheckBox;
    private LinearLayout mLlContent;
    private ImageView mIvArrow;
    private boolean isView;

    public FirstLevelNodeViewBinder1(View view, boolean isView) {
        super(view);
        this.isView = isView;
        mCheckBox = view.findViewById(R.id.checkbox);
        mLlContent = view.findViewById(R.id.ll_content);
        mIvArrow = view.findViewById(R.id.iv_arrow);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkbox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.tree_permission_level_first1;
    }

    @Override
    public void bindView(TreeNode treeNode) {
        PermissionTreeNodeBean nodeBean = (PermissionTreeNodeBean) treeNode.getValue();
        String name = nodeBean.getName();
        if (isView) {
            mCheckBox.setCompoundDrawables(null, null, null, null);
            mCheckBox.setEnabled(false);
        }
        if (TextUtils.isEmpty(name)) {//文件的情况
            name = nodeBean.getFilename();
        }
        mCheckBox.setText(name);
        if (treeNode.getChildren() == null || treeNode.getChildren().size() == 0) {
            mIvArrow.setVisibility(View.GONE);
        } else {
            mIvArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (expand) {
            mIvArrow.animate().rotation(90);
        } else {
            mIvArrow.animate().rotation(270);
        }
    }
}
